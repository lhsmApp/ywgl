package com.fh.controller.sysconfig;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;

/**
 * 系统配置
 * 
 * @ClassName: SysConfigController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2017年6月29日
 *
 */
@Controller
@RequestMapping(value = "/sysconfig")
public class SysConfigController extends BaseController {

	String menuUrl = "sysconfig/list.do"; // 菜单地址(权限用)

	@Resource(name = "sysconfigService")
	private SysConfigManager sysConfigManager;
	
	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public ModelAndView list(Page page) throws Exception {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("sysconfig/sysconfig/sysconfig_list");
		// 此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		
		return mv;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	public @ResponseBody PageResult<PageData> getPageList() throws Exception {
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varList = sysConfigManager.listAll(pd);
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		return result;
	}

	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public @ResponseBody CommonBase edit() throws Exception{
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		logBefore(logger, Jurisdiction.getUsername()+"修改SysConfig");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		if(pd.getString("oper").equals("edit")){
			sysConfigManager.edit(pd);
			commonBase.setCode(0);
		}
		else if(pd.getString("oper").equals("add")){
			sysConfigManager.save(pd);
			commonBase.setCode(0);
		}
		else if(pd.getString("oper").equals("del")){
			String [] ids=pd.getString("id").split(",");
			if(ids.length==1)
				sysConfigManager.delete(pd);
			else
				sysConfigManager.deleteAll(ids);
			commonBase.setCode(0);
		}
		
		/**此处为业务错误返回值，例如返回当前删除的信息含有业务关联字段，不能删除，自行设定setCode(返回码，客户端按码抓取并返回提示信息)和setMessage("自定义提示信息，提示给用户的")信息，并由界面进行展示。
		 * 此处不是异常返回的错误信息，异常返回错误信息统一由框架抓取异常。
		 */
		//commonBase.setCode(-1);
		//commonBase.setMessage("当前删除的信息含有业务关联字段，不能删除");
		return commonBase;
	}
}

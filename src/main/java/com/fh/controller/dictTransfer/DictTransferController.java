package com.fh.controller.dictTransfer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.DictsUtil;
import com.fh.controller.common.GenerateTransferData;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.PageResult;
import com.fh.entity.TableColumns;
import com.fh.entity.system.Dictionaries;
import com.fh.exception.CustomException;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.util.Const;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.SqlTools;
import com.fh.util.Tools;
import com.fh.util.collectionSql.GroupUtils;
import com.fh.util.collectionSql.GroupUtils.GroupBy;
import com.fh.util.enums.TransferOperType;

import net.sf.json.JSONArray;

/**
 * 字典数据传输
 * 
 * @ClassName: VoucherController
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2017年6月29日
 *
 */
@Controller
@RequestMapping(value = "/dictTransfer")
public class DictTransferController extends BaseController {

	String menuUrl = "voucher/list.do"; // 菜单地址(权限用)
	@Resource(name = "dictionariesService")
	private DictionariesManager dictionariesService;

	@Resource(name = "tmplconfigService")
	private TmplConfigService tmplconfigService;

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
		mv.setViewName("dictTransfer/dictTransfer_list");
		// 此处放当前页面初始化时用到的一些数据，例如搜索的下拉列表数据，所需的字典数据、权限数据等等。
		List<Dictionaries> listDict=dictionariesService.getDictTypes();
		Dictionaries dictDepartment=new Dictionaries();
		dictDepartment.setDICT_CODE("oa_department");
		dictDepartment.setNAME("组织机构字典");
		listDict.add(dictDepartment);
		
		/*Dictionaries dictSysUser=new Dictionaries();
		dictSysUser.setDICT_CODE("sys_user");
		dictSysUser.setNAME("系统用户字典");
		listDict.add(dictSysUser);*/
		mv.addObject("dicTypeList", listDict);
		
		mv.addObject("fmisacc", DictsUtil.getDictsByParentBianma(dictionariesService, "FMISACC"));
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
		logBefore(logger, Jurisdiction.getUsername() + "字典传输列表");
		PageData pd = new PageData();
		pd = this.getPageData();

		String keywords = pd.getString("keywords"); // 关键词检索条件
		if (null != keywords && !"".equals(keywords)) {
			pd.put("keywords", keywords.trim());
		}
		String filters = pd.getString("filters"); // 多条件过滤条件
		if (null != filters && !"".equals(filters)) {
			pd.put("filterWhereResult", SqlTools.constructWhere(filters, null));
		}
		// page.setPd(pd);
		List<PageData> varList = dictionariesService.listAll(pd);
		// int records = dictionariesService.count(pd);
		PageResult<PageData> result = new PageResult<PageData>();
		result.setRows(varList);
		// result.setRowNum(page.getRowNum());
		// result.setRecords(records);
		// result.setPage(page.getPage());
		return result;
	}

	/**
	 * 批量修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/dictTransfer")
	public @ResponseBody CommonBase dictTransfer() throws Exception {
		logBefore(logger, Jurisdiction.getUsername() + "字典传输");
		//String orgCode = Tools.readTxtFile(Const.ORG_CODE); // 读取总部组织机构编码
		CommonBase commonBase = new CommonBase();
		commonBase.setCode(-1);
		PageData pd = this.getPageData();
		String billOff=pd.getString("BILL_OFF");
		String strDataRows = pd.getString("DATA_ROWS");
		JSONArray array = JSONArray.fromObject(strDataRows);
		@SuppressWarnings("unchecked")
		List<PageData> listTransferData = (List<PageData>) JSONArray.toCollection(array, PageData.class);// 过时方法
		if (null != listTransferData && listTransferData.size() > 0) {
			/*
			 * for(PageData item:listTransferData){ item.put("UNITID", orgCode);
			 * }
			 */
			/********************** 生成字典传输数据 ************************/
			List<TableColumns> tableColumns = new ArrayList<TableColumns>();
			/*
			 * TableColumns tableColumn2=new TableColumns();
			 * tableColumn2.setColumn_key("PRI");
			 * tableColumn2.setColumn_name("UNITID");
			 * tableColumn2.setColumn_type("VARCHAR");
			 */
			TableColumns tableColumn = new TableColumns();
			tableColumn.setColumn_key("PRI");
			tableColumn.setColumn_name("BIANMA");
			tableColumn.setColumn_type("VARCHAR");
			TableColumns tableColumn1 = new TableColumns();
			tableColumn1.setColumn_name("NAME");
			tableColumn1.setColumn_type("VARCHAR");
			// tableColumns.add(tableColumn2);
			tableColumns.add(tableColumn);
			tableColumns.add(tableColumn1);
			for(PageData pdItem:listTransferData){
				pdItem.put("BIANMA", pdItem.getString("DICT_CODE"));
			}
			
			//****************临时删除字典信息************************
			/*List<PageData> testList=new ArrayList<PageData>();
			PageData pd2=new PageData();
			pd2.put("BIANMA", "01010");
			pd2.put("NAME", "盛大基地公司");
			pd2.put("PARENT_CODE", "oa_department");
			testList.add(pd2);*/
			//***************************************************

			// 将获取的字典数据进行分组
			Map<String, List<PageData>> mapTransferData = GroupUtils.group(listTransferData, new GroupBy<String>() {
				@Override
				public String groupby(Object obj) {
					PageData d = (PageData) obj;
					return d.getString("PARENT_CODE"); // 分组依据为PARENT_ID
				}
			});
			// 获取上传XML数据
			GenerateTransferData generateTransferData = new GenerateTransferData();
			// 3630100020
			String transferData = generateTransferData.generateTransferData(tableColumns, mapTransferData, billOff,
					TransferOperType.DELETE);
			// 执行上传FIMS
			Service service = new Service();
			Call call = (Call) service.createCall();
			pd.put("KEY_CODE", "JSynFactTableData");
			String strUrl = sysConfigManager.getSysConfigByKey(pd);
			URL url = new URL(strUrl);
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName("http://JSynFactTableData.j2ee", "synFactData"));
			call.setUseSOAPAction(true);
			String message = (String) call.invoke(new Object[] { transferData });
			System.out.println(message);
			if (message.equals("TRUE")) {//删除成功
				String transferDataInsert = generateTransferData.generateTransferData(tableColumns, mapTransferData, billOff,
						TransferOperType.INSERT);
				String messageInsert = (String) call.invoke(new Object[] { transferDataInsert });
				/******************************************************/
				if(messageInsert.equals("TRUE")){//插入成功
					commonBase.setCode(0);
				}else{
					commonBase.setCode(-1);
					commonBase.setMessage(messageInsert);
				}
			} else {
				commonBase.setCode(-1);
				commonBase.setMessage(message);
			}
		}
		return commonBase;
	}
}

package com.fh.service.socialOrganize.pesoinfo.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.service.socialOrganize.pesoinfo.PesoInfoManager;

/**
 * 社会组织
* @ClassName: PesoInfoService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoliu
* @date 2017年6月30日
*
 */
@Service("pesoinfoService")
public class PesoInfoService implements PesoInfoManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PesoInfoMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PesoInfoMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PesoInfoMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		List<PageData> listPageData=(List<PageData>)dao.findForList("PesoInfoMapper.datalistPage", page);
		for(PageData pageData:listPageData){
			pageData.put("OFFICE_ADDR_CUT", StringUtil.subString(pageData.getString("OFFICE_ADDR"), Const.CUT_STRING_NUM));
			pageData.put("PESO_INTR_CUT", StringUtil.subString(pageData.getString("PESO_INTR"), Const.CUT_STRING_NUM));
			pageData.put("REMARK_CUT", StringUtil.subString(pageData.getString("REMARK"), Const.CUT_STRING_NUM));
		}
		return listPageData;
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PesoInfoMapper.listAll", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> listDic(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PesoInfoMapper.listDic", pd);
	}
	
	/**列表(全部)根据条件搜索
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryListByCondition(PageData pd)throws Exception{
		List<PageData> listPageData=(List<PageData>)dao.findForList("PesoInfoMapper.queryListByCondition", pd);
		for(PageData pageData:listPageData){
			pageData.put("OFFICE_ADDR_CUT", StringUtil.subString(pageData.getString("OFFICE_ADDR"), Const.CUT_STRING_NUM));
			pageData.put("PESO_INTR_CUT", StringUtil.subString(pageData.getString("PESO_INTR"), Const.CUT_STRING_NUM));
			pageData.put("REMARK_CUT", StringUtil.subString(pageData.getString("REMARK"), Const.CUT_STRING_NUM));
		}
		return listPageData;
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PesoInfoMapper.findById", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> hasDuplicateRecord(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PesoInfoMapper.hasDuplicateRecord", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PesoInfoMapper.deleteAll", ArrayDATA_IDS);
	}
	
}


package com.fh.service.socialOrganize.pesoorginfo.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.Const;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.service.socialOrganize.pesoorginfo.PesoorgInfoManager;

/**
 * 体育社会组织领导机构信息
* @ClassName: PesoorgInfoService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoliu
* @date 2017年6月30日
*
 */
@Service("pesoorginfoService")
public class PesoorgInfoService implements PesoorgInfoManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PesoorgInfoMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("PesoorgInfoMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PesoorgInfoMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		List<PageData> listPageData=(List<PageData>)dao.findForList("PesoorgInfoMapper.datalistPage", page);
		for (PageData pageData : listPageData) {
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
		return (List<PageData>)dao.findForList("PesoorgInfoMapper.listAll", pd);
	}
	
	/**列表
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> pesoNameList()throws Exception{
		return (List<PageData>)dao.findForList("PesoactInfoMapper.pesoNameList", null);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PesoorgInfoMapper.findById", pd);
	}
	@SuppressWarnings("unchecked")
	public List<PageData> hasDuplicateRecord(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("PesoorgInfoMapper.hasDuplicateRecord", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("PesoorgInfoMapper.deleteAll", ArrayDATA_IDS);
	}
	
}


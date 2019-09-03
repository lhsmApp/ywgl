package com.fh.service.system.dictionaries.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.util.PageData;
import com.fh.service.system.dictionaries.DictionariesManager;

/**
 * 数据字典
* @ClassName: DictionariesService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
@Service("dictionariesService")
public class DictionariesService implements DictionariesManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("DictionariesMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("DictionariesMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("DictionariesMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DictionariesMapper.datalistPage", page);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DictionariesMapper.findById", pd);
	}
	
	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByBianma(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DictionariesMapper.findByBianma", pd);
	}
	
	/**通过条件获取数据
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionaries> findByCondition(String strCondition)throws Exception{
		return (List<Dictionaries>)dao.findForList("DictionariesMapper.findByCondition", strCondition);
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionaries> listSubDictByParentId(String parentId) throws Exception {
		return (List<Dictionaries>) dao.findForList("DictionariesMapper.listSubDictByParentId", parentId);
	}
	
	/**
	 * 获取字典类型
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Dictionaries> getDictTypes() throws Exception{
		return (List<Dictionaries>) dao.findForList("DictionariesMapper.getDictTypes",null);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Dictionaries> listAllDict(String parentId) throws Exception {
		List<Dictionaries> dictList = this.listSubDictByParentId(parentId);
		for(Dictionaries dict : dictList){
			dict.setTreeurl("dictionaries/list.do?DICT_CODE="+dict.getDICT_CODE());
			dict.setSubDict(this.listAllDict(dict.getDICT_CODE()));
			dict.setTarget("treeFrame");
		}
		return dictList;
	}
	
	/**排查表检查是否被占用
	 * @param pd
	 * @throws Exception
	 */
	public PageData findFromTbs(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DictionariesMapper.findFromTbs", pd);
	}
	
	/**获取SysDictionaries字典
	 * 张晓柳
	 * @param 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Dictionaries> getSysDictionaries(String dicName)throws Exception{
		return (List<Dictionaries>)dao.findForList("DictionariesMapper.getSysDictionaries", dicName);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listJq(JqPage page)throws Exception{
		return (List<PageData>)dao.findForList("DictionariesMapper.datalistJqPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DictionariesMapper.listAll", pd);
	}
	
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int count(PageData pd)throws Exception{
		return (int)dao.findForObject("DictionariesMapper.count", pd);
	}
	
	/**是否有相同编码 
	 * @param pd
	 * @throws Exception
	 */
	public PageData hasSameDictCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DictionariesMapper.hasSameDictCode", pd);
	}
}


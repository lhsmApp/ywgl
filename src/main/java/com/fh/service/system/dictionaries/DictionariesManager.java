package com.fh.service.system.dictionaries;

import java.util.List;

import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.util.PageData;

/**
 * 数据字典接口类
* @ClassName: DictionariesManager
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
public interface DictionariesManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	/**通过编码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByBianma(PageData pd)throws Exception;

	/**通过条件获取数据
	 * @param pd
	 * @throws Exception
	 */
	public List<Dictionaries> findByCondition(String strCondition)throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Dictionaries> listSubDictByParentId(String parentId) throws Exception;
	
	/**
	 * 获取字典类型
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Dictionaries> getDictTypes() throws Exception;
	
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Dictionaries> listAllDict(String parentId) throws Exception;
	
	/**排查表检查是否被占用
	 * @param pd
	 * @throws Exception
	 */
	public PageData findFromTbs(PageData pd)throws Exception;
	
	/**获取SysDictionaries字典
	 * 张晓柳
	 * @param 
	 * @throws Exception
	 */
	public List<Dictionaries> getSysDictionaries(String dicName)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listJq(JqPage page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int count(PageData pd)throws Exception;
	
	/**是否有相同编码 
	 * @param pd
	 * @throws Exception
	 */
	public PageData hasSameDictCode(PageData pd)throws Exception;
}


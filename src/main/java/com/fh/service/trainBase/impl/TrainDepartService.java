package com.fh.service.trainBase.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.ProblemType;
import com.fh.entity.TrainDepart;
import com.fh.util.PageData;
import com.fh.service.trainBase.TrainDepartManager;

/** 
 * 说明： 培训基础
 * 创建人：jiachao
 * 创建时间：2019-10-23
 * @version
 */
@Service("traindepartService")
public class TrainDepartService implements TrainDepartManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TrainDepartMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TrainDepartMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TrainDepartMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TrainDepartMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TrainDepartMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TrainDepartMapper.findById", pd);
	}
	
	/**通过Code获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TrainDepartMapper.findByCode", pd);
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainDepart> listByParentId(String parentId) throws Exception {
		return (List<TrainDepart>) dao.findForList("TrainDepartMapper.listByParentId", parentId);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<TrainDepart> listTree(String parentId) throws Exception {
		List<TrainDepart> valueList = this.listByParentId(parentId);
		for(TrainDepart fhentity : valueList){
			fhentity.setTreeurl("traindepart/list.do?TRAINDEPART_ID="+fhentity.getTRAINDEPART_ID());
			fhentity.setSubTrainDepart(this.listTree(fhentity.getTRAINDEPART_ID()));
			fhentity.setTarget("treeFrame");
		}
		return valueList;
	}
		
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)下拉ztree用
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllTrainDepartToSelect(String parentId,List<PageData> ztrainDepartPdList) throws Exception {
		List<PageData>[] arrayTrainDepart = this.listAllbyPd(parentId,ztrainDepartPdList);
		List<PageData> trainDepartPdList = arrayTrainDepart[1];
		for(PageData pd : trainDepartPdList){
			this.listAllTrainDepartToSelect(pd.getString("id"),arrayTrainDepart[0]);
		}
		return arrayTrainDepart[0];
	}
		
	/**下拉ztree用
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData>[] listAllbyPd(String parentId,List<PageData> ztrainDepartPdList) throws Exception {
		List<TrainDepart> trainDepartList = this.listSubTrainDepartByParentId(parentId);
		List<PageData> trainDepartPdList = new ArrayList<PageData>();
		for(TrainDepart trainDepart : trainDepartList){
			PageData pd = new PageData();
			pd.put("id", trainDepart.getTRAINDEPART_ID());
			pd.put("parentId", trainDepart.getPARENT_ID());
			pd.put("name", trainDepart.getNAME());
			pd.put("icon", "static/images/user.gif");
			trainDepartPdList.add(pd);
			ztrainDepartPdList.add(pd);
		}
		List<PageData>[] arrayDep = new List[2];
		arrayDep[0] = ztrainDepartPdList;
		arrayDep[1] = trainDepartPdList;
		return arrayDep;
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<TrainDepart> listSubTrainDepartByParentId(String parentId) throws Exception {
		return (List<TrainDepart>) dao.findForList("TrainDepartMapper.listSubTrainDepartByParentId", parentId);
	}
}


package com.fh.service.mbp.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.JqPage;
import com.fh.entity.Page;
import com.fh.service.mbp.MBPManager;
import com.fh.util.PageData;
import com.fh.util.enums.ProPriority;
import com.fh.util.enums.ProState;

/**
 * 问题管理
* @ClassName: MBPService
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2019年9月9日
*
 */
@Service("mbpService")
public class MBPService implements MBPManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("MBPMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("MBPMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("MBPMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		List<PageData> list=(List<PageData>)dao.findForList("MBPMapper.datalistPage", page);
		for(PageData pd:list){
			pd.put("PRO_STATE_NAME", ProState.getValueByKey(pd.getString("PRO_STATE")));
		}
		return list;
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MBPMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		PageData pdResult=(PageData)dao.findForObject("MBPMapper.findById", pd);
		pdResult.put("PRO_STATE_NAME", ProState.getValueByKey(pdResult.getString("PRO_STATE")));
		pdResult.put("PRO_PRIORITY_NAME", ProPriority.getValueByKey(pdResult.getString("PRO_PRIORITY")));
		return pdResult;
	}
	
	/**获取记录数量
	 * @param pd
	 * @throws Exception
	 */
	public int count(PageData pd)throws Exception{
		return (int)dao.findForObject("MBPMapper.count", pd);
	}

	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("MBPMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public void commit(PageData pd) throws Exception {
		dao.update("MBPMapper.commit", pd);
	}

	@Override
	public void cancel(PageData pd) throws Exception {
		dao.update("MBPMapper.cancel", pd);
	}
	
	@Override
	public void proGet(PageData pd) throws Exception {
		dao.update("MBPMapper.proGet", pd);
	}

	@Override
	public void addAssign(PageData pd) throws Exception {
		dao.save("MBPMapper.addAssign", pd);
	}

	@Override
	public void deleteAssign(PageData pd) throws Exception {
		dao.delete("MBPMapper.deleteAssign", pd);
	}

	@Override
	public void addAnswer(PageData pd) throws Exception {
		dao.save("MBPMapper.addAnswer", pd);
	}

	@Override
	public void deleteAnswer(PageData pd) throws Exception {
		dao.delete("MBPMapper.deleteAnswer", pd);
	}

	@Override
	public void addClose(PageData pd) throws Exception {
		dao.save("MBPMapper.addClose", pd);
	}

	@Override
	public void deleteClose(PageData pd) throws Exception {
		dao.update("MBPMapper.deleteClose", pd);
	}

	/**获取问题回复信息列表
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getProAnswers(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MBPMapper.getProAnswers", pd);
	}
	
	/**获取问题回复信息内容
	 * @param pd
	 * @throws Exception
	 */
	public PageData getAnswerContent(PageData pd)throws Exception{
		return (PageData)dao.findForObject("MBPMapper.getAnswerContent", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void updateAnswer(PageData pd)throws Exception{
		dao.update("MBPMapper.updateAnswer", pd);
	}
	
	/**获取问题回复信息列表
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getProLog(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("MBPMapper.getProLog", pd);
	}
	
	/**
	 * 增加问题日志
	 * @param pd
	 * @throws Exception
	 */
	@Override
	public void addLog(PageData pd) throws Exception {
		dao.save("MBPMapper.addLog", pd);
	}
}


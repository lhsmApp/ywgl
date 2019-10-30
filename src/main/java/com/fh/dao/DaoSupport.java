package com.fh.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import com.fh.util.PageData;
/**
 *  
* @ClassName: DaoSupport
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lhsmplus
* @date 2017年6月30日
*
 */
@Repository("daoSupport")
public class DaoSupport implements DAO {

	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionTemplate;

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForObject(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectOne(str, obj);
	}

	/**
	 * 查找对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object findForList(String str, Object obj) throws Exception {
		return sqlSessionTemplate.selectList(str, obj);
	}
	
	public Object findForMap(String str, Object obj, String key, String value) throws Exception {
		return sqlSessionTemplate.selectMap(str, obj, key);
	}

	/**
	 * 保存对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object save(String str, Object obj) throws Exception {
		return sqlSessionTemplate.insert(str, obj);
	}
	
	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object batchSave(String str, List objs )throws Exception{
		return sqlSessionTemplate.insert(str, objs);
	}
	
	/**
	 * 修改对象
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object update(String str, Object obj) throws Exception {
		return sqlSessionTemplate.update(str, obj);
	}

	/**
	 * 删除对象 
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object delete(String str, Object obj) throws Exception {
		return sqlSessionTemplate.delete(str, obj);
	}

	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public Object batchDelete(String str, List objs )throws Exception{
		return sqlSessionTemplate.delete(str, objs);
	}
	
	/**
	 * 批量更新
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void batchUpdate(String str, List<?> objs )throws Exception{
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		try{
			if(objs!=null){
				for(int i=0,size=objs.size();i<size;i++){
					sqlSession.update(str, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		}finally{
			sqlSession.close();
		}
	}


	/**
	 * 获取计算数据
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findDataCalculation(String tableNameBackup, 
			String sqlBatchDelAndIns, 
			String sqlRetSelect, List<PageData> listAdd)throws Exception{
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		List<PageData> returnList = new ArrayList<PageData>();
		try{
			if(listAdd!=null && !listAdd.isEmpty()){
				//删除TB_   _DETAIL_backup表中所有数据
				sqlSession.delete("DataCalculation.deleteTableData", tableNameBackup);
				//获取本次操作的数据流水号，文档中有的、后加生成的（先取最大值，添加时流水号自增，再取大于最大值的）
				Integer strMaxNum = sqlSession.selectOne("DataCalculation.getMaxSerialNo", tableNameBackup);
				String SqlInBillCode = "";
				for(PageData eachPd : listAdd){
					String SERIAL_NO = eachPd.getString("SERIAL_NO");
					if(SERIAL_NO!=null && !SERIAL_NO.trim().equals("")){
						if(SqlInBillCode!=null && !SqlInBillCode.trim().equals("")){
							SqlInBillCode += ",";
						}
						SqlInBillCode += SERIAL_NO;
					}
				}
				//先删后插，
				sqlSession.update(sqlBatchDelAndIns, listAdd);
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
				PageData getAddSerialNo = new PageData();
				getAddSerialNo.put("tableName", tableNameBackup);
				getAddSerialNo.put("strMaxNum", strMaxNum);
				List<Integer> getInsertBillCodeList =  sqlSession.selectList("DataCalculation.getAddSerialNo",  getAddSerialNo);
				if(getInsertBillCodeList!=null){
					for(Integer billCode : getInsertBillCodeList){
						if(SqlInBillCode!=null && !SqlInBillCode.trim().equals("")){
							SqlInBillCode += ",";
						}
						SqlInBillCode += billCode;
					}
				}
				//tb_social_inc_detail_backup（导入时用来计算的明细）先删后插数据后，带公式查询出记录
				PageData getListBySerialNo = new PageData();
				getListBySerialNo.put("sqlRetSelect", sqlRetSelect);
				getListBySerialNo.put("SqlInBillCode", SqlInBillCode);
				returnList = sqlSession.selectList("DataCalculation.getListBySerialNo",  getListBySerialNo);
			}
		} finally{
			sqlSession.rollback(); 
			sqlSession.close();
		}
		return returnList;
	}
	
	/**
	 * 上报
	 * @param str
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public void saveReport(String reportDelete, String reportInsert, List<?> objs)throws Exception{
		SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
		//批量执行器
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		try{
			if(objs!=null&&objs.size()>0){
				for(int i=0,size=objs.size();i<size;i++){
					sqlSession.delete(reportDelete, objs.get(i));
					sqlSession.update(reportInsert, objs.get(i));
				}
				sqlSession.flushStatements();
				sqlSession.commit();
				sqlSession.clearCache();
			}
		}finally{
			sqlSession.close();
		}
	}

}



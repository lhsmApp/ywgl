package com.fh.util.collectionSql;

import java.util.List;

import org.apache.log4j.Logger;
import org.josql.Query;
import org.josql.QueryExecutionException;
import org.josql.QueryParseException;
import org.josql.QueryResults;

import com.fh.util.StringUtil;

/**
 * JoSql工具类
 * 
 * @Description: 用于从集合中查询需要的记录
 */
public class JoSqlUtil {
	private static Logger log = Logger.getLogger(JoSqlUtil.class);

	/**
	 * 从列表获取指定起始行的指定条记录
	 * 
	 * @param listObject
	 *            对象列表
	 * @param startLine
	 *            开始行
	 * @param num
	 *            返回条数
	 * @return 查询到的记录列表
	 */
	public static <T> List<T> getObjectsFromObjectListByStartAndEndLine(
			List<T> listObject, int startLine, int num) {
		return getObjectsFromObjectListByStartAndEndLineByWhereConditions(
				listObject, startLine, num, null);
	}

	/**
	 * 从列表获取指定起始行的指定条记录
	 * 
	 * @param listObject
	 *            对象列表
	 * @param startLine
	 *            开始行
	 * @param num
	 *            返回条数
	 * @param whereConditions
	 *            where条件，不能包含limit,形如where a=a and b=b order by ……
	 * @return 查询到的记录列表
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getObjectsFromObjectListByStartAndEndLineByWhereConditions(
			List<T> listObject, int startLine, int num, String whereConditions) {
		if (listObject == null || listObject.size() < 1) {
			return listObject;
		}
		try {
			Query query = new Query();
			String sql = "select * from "
					+ listObject.get(0).getClass().getName();
			if (!StringUtil.isEmpty(whereConditions)) {
				sql = sql + " " + whereConditions;
			}
			sql = sql + " limit " + startLine + "," + num;
			query.parse(sql);

			QueryResults results;
			results = query.execute(listObject);
			return results.getResults();
		} catch (Exception e) {
			log.error("从列表获取指定起始行的指定条记录失败" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 按条件从列表获取记录
	 * 
	 * @param listObject
	 *            对象列表
	 * @param whereConditions
	 *            where条件，形如where a=a and b=b order by ……
	 * @return 查询到的记录列表
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getObjectsFromObjectListByWhereConditions(
			List<T> listObject,String whereConditions) {
		if (listObject == null || listObject.size() < 1) {
			return listObject;
		}
		try {
			Query query = new Query();
			String sql = "select * from "
					+ listObject.get(0).getClass().getName();
			if (!StringUtil.isEmpty(whereConditions)) {
				sql = sql + " " + whereConditions;
			}
			query.parse(sql);

			QueryResults results;
			results = query.execute(listObject);
			return results.getResults();
		} catch (Exception e) {
			log.error("按条件从列表获取记录失败" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 根据指定列值，查询对应的记录
	 * 
	 * @param listObject
	 *            待查询列表
	 * @param param
	 *            参数值
	 * @param paramName
	 *            不可为空 要查询的参数名
	 * @return
	 * @throws QueryExecutionException
	 * @throws QueryParseException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getObjectFromObjectListByParam(List<T> listObject,
			String param, String paramName) {
		if (listObject == null || listObject.size() < 1
				|| StringUtil.isEmpty(paramName)) {
			return null;
		}
		try {
			Query query = new Query();
			String sql = "select * from "
					+ listObject.get(0).getClass().getName() + " where "
					+ paramName + "=:" + paramName;
			query.setVariable(paramName, param);
			query.parse(sql);
			QueryResults results;
			results = query.execute(listObject);

			List<T> returnlist = results.getResults();
			if (null != returnlist && 0 != returnlist.size()) {
				return returnlist.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("根据指定列值，查询对应的记录失败" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
}

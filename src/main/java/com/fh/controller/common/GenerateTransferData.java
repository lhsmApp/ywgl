package com.fh.controller.common;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.fh.entity.TableColumns;
import com.fh.util.PageData;
import com.fh.util.StringUtil;

/**
 * 生成用于传输的Xml格式数据
 * 
 * @ClassName: GenerateTransferData
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author jiachao
 * @date 2017年6月28日
 *
 */
public class GenerateTransferData {

	/**
	 * 传输凭证数据
	 * 
	 * @param tableColumns
	 * @param listTransferData
	 * @param deptCode
	 * @param transferOperType
	 * @param transferOperType
	 */
	public String generateTransferData(List<TableColumns> tableColumns, Map<String, List<PageData>> mapTransferData,
			String deptCode, String transferOperType) {
		// 建立document对象
		Document doc = DocumentHelper.createDocument();
		// 建立XML文档的根data
		Element root = doc.addElement("data");
		root.addAttribute("oper", transferOperType.toString());// 加入oper属性内容
		root.addAttribute("org", deptCode);// 加入org属性内容,org属性代表FMIS的组织结构编码
		//root.addComment("根元素data");// 加入一行注释
		for (Map.Entry<String, List<PageData>> entry : mapTransferData.entrySet()) {
			Element table = root.addElement("table");// 加入第一个table节点
			table.addAttribute("id", "T_" + entry.getKey());// 加入id属性，值为表名,在FMIS中为了区别FMIS表与其他系统表，其他系统表名要以"T_"开头，所以此处值为T_+AMIS表名
			table.addAttribute("colNumbers", StringUtil.toString(tableColumns.size(), ""));// 加入colNumbers属性，值代表该表中列的个数
			/*
			 * 在<colsAll> 标签中定义该表所有列，<Keys> 中定义主键列，<ColS> 中定义普通列，colType
			 * 属性代表该列的类型，C代表字符类型，N代表数值类型。
			 */
			Element colsAll = table.addElement("colsAll");// 加入colsAll节点,标签中定义该表所有列

			// 主键列集合
			Element keys = colsAll.addElement("Keys");// 加入Keys节点,定义所有主键列集合
			// 普通列集合
			Element colS = colsAll.addElement("ColS");// 加入Keys节点,<ColS>
														// 中定义普通列集合
			for (TableColumns tableColumn : tableColumns) {
				if (tableColumn.getColumn_key() != null && tableColumn.getColumn_key().trim().equals("PRI")) {
					Element key = keys.addElement("key");// 加入Key节点,定义主键列
					key.addAttribute("id", "F_" + tableColumn.getColumn_name());// 加入id属性，值为列名
					if (tableColumn.getData_type() != null && (tableColumn.getData_type().trim().equals("DECIMAL")
							|| tableColumn.getData_type().trim().equals("DOUBLE")
							|| tableColumn.getData_type().trim().equals("INT")
							|| tableColumn.getData_type().trim().equals("FLOAT"))) {
						key.addAttribute("colType", "N");// 加入colType属性，属性代表该列的类型，C代表字符类型，N代表数值类型
					} else {
						key.addAttribute("colType", "C");// 加入colType属性，属性代表该列的类型，C代表字符类型，N代表数值类型
					}
				} else {
					Element col = colS.addElement("col");// 加入col节点,定义普通列
					col.addAttribute("id", "F_" + tableColumn.getColumn_name());// 加入id属性，值为列名
					if (tableColumn.getData_type() != null && (tableColumn.getData_type().trim().equals("DECIMAL")
							|| tableColumn.getData_type().trim().equals("DOUBLE")
							|| tableColumn.getData_type().trim().equals("INT")
							|| tableColumn.getData_type().trim().equals("FLOAT"))) {
						col.addAttribute("colType", "N");// 加入colType属性，属性代表该列的类型，C代表字符类型，N代表数值类型
					} else {
						col.addAttribute("colType", "C");// 加入colType属性，属性代表该列的类型，C代表字符类型，N代表数值类型
					}
				}
			}

			// <rows> 标签中可存放多行数据，每行数据存放到<row> 标签中，<KeyValue> 中存放主键值，<ColValue>
			// 存放普通列值。
			Element rows = table.addElement("rows");// 加入rows节点,<rows>
													// 标签中可存放多行数据
			for (PageData transferData : entry.getValue()) {
				Element row = rows.addElement("row");// 加入rows节点,每行数据存放到<row>
														// 标签中
				// 主键列值集合
				Element keyValue = row.addElement("KeyValue");// 加入KeyValue节点,定义主键列值集合
				// 普通列值集合
				Element colValue = row.addElement("ColValue");// 加入ColValue节点,定义普通列值集合
				for (TableColumns tableColumn : tableColumns) {
					if (tableColumn.getColumn_key() != null && tableColumn.getColumn_key().trim().equals("PRI")) {
						Element value = keyValue.addElement("value");// 加入value节点
						value.setText(StringUtil.toString(transferData.get(tableColumn.getColumn_name()), ""));// 为value设置内容//定义主键列值
					} else {
						Element value = colValue.addElement("value");// 加入value节点
						value.setText(StringUtil.toString(transferData.get(tableColumn.getColumn_name()), ""));// 为value设置内容//定义普通列值
					}
				}
			}
		}
		// document转换为String字符串
		String documentStr = doc.asXML();
		return documentStr;
	}
}

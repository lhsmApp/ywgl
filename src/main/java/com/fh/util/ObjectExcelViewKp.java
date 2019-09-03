package com.fh.util;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.Region;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.fh.util.PageData;
import com.fh.util.Tools;

/**
 * 导入到EXCEL
* @ClassName: ObjectExcelView
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lhsmplus
* @date 2017年6月30日
*
 */
public class ObjectExcelViewKp extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String getfilename = (String) model.get("filename");
		String busiDate=(String)model.get("BUSI_DATE");
		busiDate=busiDate.substring(0,4)+"年"+busiDate.substring(4,6)+"月";
		String showfilename = new String(getfilename.getBytes("gb2312"), "ISO-8859-1");
		
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+showfilename+".xls");
		sheet = workbook.createSheet("sheet1");
		
		
		int colNum = 4;
		short width = 30,height=25*20,heightHeader=30*20,heightMemo=20*20;
		sheet.setDefaultColumnWidth(width);
		//制定某一列的宽度：需要是256的倍数
        /*sheet.setColumnWidth(0,1024);
        sheet.setColumnWidth(1,1024);
        sheet.setColumnWidth(2,1024);*/

		//中国石油天然气股份有限公司西气东输管道分公司
		cell = getCell(sheet, 0, 0);
		setText(cell,"中国石油天然气股份有限公司西气东输管道分公司");
		sheet.getRow(0).setHeight(heightHeader);    
        //合并表头单元格
        setRegionStyle(sheet, new Region(0,(short)0,0,(short)(colNum-1)),getHeaderStyle(workbook));
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)(colNum-1)));

        //开票申请单
        cell = getCell(sheet, 1, 0);
		setText(cell,"开票申请单");
		sheet.getRow(1).setHeight(heightHeader);    
        //合并表头单元格
        setRegionStyle(sheet, new Region(1,(short)0,1,(short)(colNum-1)),getHeaderStyle(workbook));
        sheet.addMergedRegion(new Region(1,(short)0,1,(short)(colNum-1)));
        
        cell = getCell(sheet, 2, 0);
		setText(cell,"编号JSDF-12");
		cell.setCellStyle(getUnitStyle(workbook)); 
		
		cell = getCell(sheet, 2, 1);
		setText(cell,busiDate);
		cell.setCellStyle(getUnitStyle(workbook));
        
		cell = getCell(sheet, 2, 3);
		setText(cell,"单位：元（不含税）");
		cell.setCellStyle(getUnitStyle(workbook)); 
		sheet.getRow(2).setHeight(heightMemo);  
		
		
		List<String> titles = (List<String>) model.get("titles");
		int len = titles.size();
		HSSFCellStyle headerStyle = workbook.createCellStyle(); //标题样式
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFFont headerFont = workbook.createFont();	//标题字体
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short)11);
		headerStyle.setFont(headerFont);
		//short width = 20,height=25*20;
		//sheet.setDefaultColumnWidth(width);
		for(int i=0; i<len; i++){ //设置标题
			String title = titles.get(i);
			cell = getCell(sheet, 3, i);
			cell.setCellStyle(headerStyle);
			setText(cell,title);
		}
		sheet.getRow(3).setHeight(height);
		
		HSSFCellStyle contentStyle = workbook.createCellStyle(); //内容样式
		contentStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		List<PageData> varList = (List<PageData>) model.get("varList");
		int varCount = varList.size();
		for(int i=0; i<varCount; i++){
			PageData vpd = varList.get(i);
			for(int j=0;j<len;j++){
				String varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
				cell = getCell(sheet, i+colNum, j);
				cell.setCellStyle(contentStyle);
				setText(cell,varstr);
			}
		}
		
		cell = getCell(sheet, varCount+colNum, 0);
		setText(cell,"主管领导：");
		cell.setCellStyle(getUnitStyle(workbook)); 
		
		cell = getCell(sheet, varCount+colNum, 1);
		setText(cell,"财务审核：");
		cell.setCellStyle(getUnitStyle(workbook));
        
		cell = getCell(sheet, varCount+colNum, 2);
		setText(cell,"经办人：");
		cell.setCellStyle(getUnitStyle(workbook)); 
		sheet.getRow(varCount+colNum).setHeight(heightMemo);  
	}

	private HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook){
        //设置样式
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 样式对象    
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平    
        //cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        //cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        //cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        //cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 18);//设置字体大小  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗 
        cellStyle.setFont(font);
        return cellStyle;
	}
	private HSSFCellStyle getUnitStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyle = workbook.createCellStyle(); //标题样式
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平    
		HSSFFont font = workbook.createFont();	
		font.setFontHeightInPoints((short)9);
		cellStyle.setFont(font);
        return cellStyle;
	}
	
	/**
	* 设置单元格边框（解决合并单元格显示部分边框问题）
	* @param sheet 
	* @param region
	* @param cs
	*/
	@SuppressWarnings("deprecation")
	public static void setRegionStyle(HSSFSheet sheet, Region region, HSSFCellStyle cs) {
	    for (int i = region.getRowFrom(); i <= region.getRowTo(); i++) {
	        HSSFRow row = HSSFCellUtil.getRow(i, sheet);
	        for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
	            HSSFCell cell = HSSFCellUtil.getCell(row, (short) j);
	            cell.setCellStyle(cs);
	        }
	    }
	}
}

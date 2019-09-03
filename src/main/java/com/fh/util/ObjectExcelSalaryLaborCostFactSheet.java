package com.fh.util;

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


/**
 * 导入到EXCEL
* @ClassName: ObjectExcelSalaryLaborCostFactSheet
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lhsmplus
* @date 2017年6月30日
*
 */
public class ObjectExcelSalaryLaborCostFactSheet extends AbstractExcelView{

	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String getfilename = (String) model.get("filename");
		String showfilename = new String(getfilename.getBytes("gb2312"), "ISO-8859-1");
		
		HSSFSheet sheet;
		HSSFCell cell;
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename="+showfilename+".xls");
		sheet = workbook.createSheet("sheet1");

		int colNum = 19;
		short width = 20,heightHeader=30*20;
		sheet.setDefaultColumnWidth(width);
		//制定某一列的宽度：需要是256的倍数
        sheet.setColumnWidth(0,1024);
        sheet.setColumnWidth(1,1024);
        sheet.setColumnWidth(2,1024);

		cell = getCell(sheet, 0, 0);
		setText(cell,getfilename);
		sheet.getRow(0).setHeight(heightHeader);    

        //合并表头单元格
        setRegionStyle(sheet, new Region(0,(short)0,0,(short)(colNum-1)),getHeaderStyle(workbook));
        sheet.addMergedRegion(new Region(0,(short)0,0,(short)(colNum-1)));

		cell = getCell(sheet, 1, colNum-3);
		setText(cell,"单位：元");
		cell.setCellStyle(getUnitStyle(workbook));   
		
		int rowHeaderNum = 2;
		List<PageData> varList = (List<PageData>) model.get("setList");
		int varCount = varList.size();
		for(int i=0; i<varCount; i++){
			PageData vpd = varList.get(i);
			for(int j=0;j<colNum;j++){
				int col = j+1;
				String getValue = "";
				if(col>=10){
					getValue = vpd.getString("name"+col);
				} else {
					getValue = vpd.getString("name0"+col);
				}
				String varstr = (getValue != null && !getValue.trim().equals("")) ? getValue : "";
				cell = getCell(sheet, i+rowHeaderNum, j);
				cell.setCellStyle(getContentNormalStyle(workbook));
				setText(cell,varstr);
			}
		}
		
        //两行标题行
		cell = getCell(sheet, 2, 0);
        setRegionStyle(sheet, new Region(2,(short)0,3,(short)(3)),getTittleStyle(workbook));
        sheet.addMergedRegion(new Region(2,(short)0,3,(short)(3)));

		cell = getCell(sheet, 2, 4);
        setRegionStyle(sheet, new Region(2,(short)(4),3,(short)(4)),getTittleStyle(workbook));
        sheet.addMergedRegion(new Region(2,(short)(4),3,(short)(4)));
        
		cell = getCell(sheet, 2, 5);
        setRegionStyle(sheet, new Region(2,(short)(5),2,(short)(12)),getTittleStyle(workbook));
        sheet.addMergedRegion(new Region(2,(short)(5),2,(short)(12)));

        for(int i=5; i<=12;i++){
    		cell = getCell(sheet, 3, i);
			cell.setCellStyle(getTittleStyle(workbook));
        }
        for(int i=13; i<=colNum-1;i++){
    		cell = getCell(sheet, 2, i);
            setRegionStyle(sheet, new Region(2,(short)(i),3,(short)(i)),getTittleStyle(workbook));
            sheet.addMergedRegion(new Region(2,(short)(i),3,(short)(i)));
        }

        //工资总额合计
		cell = getCell(sheet, 4, 0);
        setRegionStyle(sheet, new Region(4,(short)(0),4,(short)(3)),getContentBoldAlignStyle(workbook));
        sheet.addMergedRegion(new Region(4,(short)(0),4,(short)(3)));
        for(int i=4; i<=colNum-1;i++){
    		cell = getCell(sheet, 4, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
        }
        
        //合同化
		cell = getCell(sheet, 5, 0);
        setRegionStyle(sheet, new Region(5,(short)(0),14,(short)(1)),getContentBoldVerticalStyle(workbook));
        sheet.addMergedRegion(new Region(5,(short)(0),14,(short)(1)));

		cell = getCell(sheet, 5, 2);
        setRegionStyle(sheet, new Region(5,(short)(2),5,(short)(3)),getContentBoldAlignStyle(workbook));
        sheet.addMergedRegion(new Region(5,(short)(2),5,(short)(3)));

		cell = getCell(sheet, 6, 2);
        setRegionStyle(sheet, new Region(6,(short)(2),9,(short)(2)),getContentBoldVerticalStyle(workbook));
        sheet.addMergedRegion(new Region(6,(short)(2),9,(short)(2)));

		cell = getCell(sheet, 10, 2);
        setRegionStyle(sheet, new Region(10,(short)(2),14,(short)(2)),getContentBoldVerticalStyle(workbook));
        sheet.addMergedRegion(new Region(10,(short)(2),14,(short)(2)));

		cell = getCell(sheet, 6, 3);
		cell.setCellStyle(getContentBoldAlignStyle(workbook));

		cell = getCell(sheet, 10, 3);
		cell.setCellStyle(getContentBoldAlignStyle(workbook));
		
        for(int i=4; i<=colNum-1;i++){
    		cell = getCell(sheet, 5, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
    		cell = getCell(sheet, 6, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
    		cell = getCell(sheet, 10, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
        }
        
        //市场化
		cell = getCell(sheet, 15, 0);
        setRegionStyle(sheet, new Region(15,(short)(0),24,(short)(1)),getContentBoldVerticalStyle(workbook));
        sheet.addMergedRegion(new Region(15,(short)(0),24,(short)(1)));

		cell = getCell(sheet, 15, 2);
        setRegionStyle(sheet, new Region(15,(short)(2),15,(short)(3)),getContentBoldAlignStyle(workbook));
        sheet.addMergedRegion(new Region(15,(short)(2),15,(short)(3)));

		cell = getCell(sheet, 16, 2);
        setRegionStyle(sheet, new Region(16,(short)(2),19,(short)(2)),getContentBoldVerticalStyle(workbook));
        sheet.addMergedRegion(new Region(16,(short)(2),19,(short)(2)));

		cell = getCell(sheet, 20, 2);
        setRegionStyle(sheet, new Region(20,(short)(2),24,(short)(2)),getContentBoldVerticalStyle(workbook));
        sheet.addMergedRegion(new Region(20,(short)(2),24,(short)(2)));

		cell = getCell(sheet, 16, 3);
		cell.setCellStyle(getContentBoldAlignStyle(workbook));

		cell = getCell(sheet, 20, 3);
		cell.setCellStyle(getContentBoldAlignStyle(workbook));
		
        for(int i=4; i<=colNum-1;i++){
    		cell = getCell(sheet, 15, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
    		cell = getCell(sheet, 16, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
    		cell = getCell(sheet, 20, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
        }

        //中间一行标题行
		cell = getCell(sheet, 25, 0);
        setRegionStyle(sheet, new Region(25,(short)0,25,(short)(3)),getTittleStyle(workbook));
        sheet.addMergedRegion(new Region(25,(short)0,25,(short)(3)));

		cell = getCell(sheet, 25, 4);
		cell.setCellStyle(getTittleStyle(workbook));
        
		cell = getCell(sheet, 25, 5);
        setRegionStyle(sheet, new Region(25,(short)(5),25,(short)(colNum-2)),getTittleStyle(workbook));
        sheet.addMergedRegion(new Region(25,(short)(5),25,(short)(colNum-2)));

    	cell = getCell(sheet, 25, colNum-1);
		cell.setCellStyle(getTittleStyle(workbook));
        
        //劳务费
		cell = getCell(sheet, 26, 0);
        setRegionStyle(sheet, new Region(26,(short)0,40,(short)(0)),getContentBoldVerticalStyle(workbook));
        sheet.addMergedRegion(new Region(26,(short)0,40,(short)(0)));

		cell = getCell(sheet, 26, 1);
        setRegionStyle(sheet, new Region(26,(short)1,26,(short)(3)),getContentBoldAlignStyle(workbook));
        sheet.addMergedRegion(new Region(26,(short)1,26,(short)(3)));
		
        for(int i=4; i<=colNum-1;i++){
    		cell = getCell(sheet, 26, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
        }

		cell = getCell(sheet, 27, 1);
        setRegionStyle(sheet, new Region(27,(short)1,27,(short)(3)),getContentBoldAlignStyle(workbook));
        sheet.addMergedRegion(new Region(27,(short)1,27,(short)(3)));

		cell = getCell(sheet, 27, 4);
		cell.setCellStyle(getContentBoldAlignStyle(workbook));
		
        for(int i=4; i<=colNum-1;i++){
    		cell = getCell(sheet, 27, i);
			cell.setCellStyle(getTittleStyle(workbook));
        }
		
        //系统内劳务
		cell = getCell(sheet, 28, 1);
        setRegionStyle(sheet, new Region(28,(short)1,33,(short)(1)),getContentBoldVerticalStyle(workbook));
        sheet.addMergedRegion(new Region(28,(short)1,33,(short)(1)));

		cell = getCell(sheet, 28, 2);
        setRegionStyle(sheet, new Region(28,(short)2,28,(short)(3)),getContentBoldAlignStyle(workbook));
        sheet.addMergedRegion(new Region(28,(short)2,28,(short)(3)));

		cell = getCell(sheet, 29, 2);
        setRegionStyle(sheet, new Region(29,(short)2,32,(short)(2)),getContentBoldVerticalStyle(workbook));
        sheet.addMergedRegion(new Region(29,(short)2,32,(short)(2)));

		cell = getCell(sheet, 29, 3);
		cell.setCellStyle(getContentBoldAlignStyle(workbook));

		cell = getCell(sheet, 33, 2);
        setRegionStyle(sheet, new Region(33,(short)2,33,(short)(3)),getContentBoldAlignStyle(workbook));
        sheet.addMergedRegion(new Region(33,(short)2,33,(short)(3)));

        for(int i=4; i<=colNum-1;i++){
    		cell = getCell(sheet, 28, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
    		cell = getCell(sheet, 29, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
    		cell = getCell(sheet, 33, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
        }
		
        //劳务派遣
		cell = getCell(sheet, 34, 1);
        setRegionStyle(sheet, new Region(34,(short)1,40,(short)(1)),getContentBoldVerticalStyle(workbook));
        sheet.addMergedRegion(new Region(34,(short)1,40,(short)(1)));

		cell = getCell(sheet, 34, 2);
        setRegionStyle(sheet, new Region(34,(short)2,34,(short)(3)),getContentBoldAlignStyle(workbook));
        sheet.addMergedRegion(new Region(34,(short)2,34,(short)(3)));

		cell = getCell(sheet, 34, 4);
		cell.setCellStyle(getContentBoldAlignStyle(workbook));

        for(int i=4; i<=colNum-1;i++){
    		cell = getCell(sheet, 34, i);
			cell.setCellStyle(getTittleStyle(workbook));
        } 

		cell = getCell(sheet, 35, 2);
        setRegionStyle(sheet, new Region(35,(short)2,35,(short)(3)),getContentBoldAlignStyle(workbook));
        sheet.addMergedRegion(new Region(35,(short)2,35,(short)(3)));

		cell = getCell(sheet, 36, 2);
        setRegionStyle(sheet, new Region(36,(short)2,39,(short)(2)),getContentBoldVerticalStyle(workbook));
        sheet.addMergedRegion(new Region(36,(short)2,39,(short)(2)));
        
		cell = getCell(sheet, 36, 3);
		cell.setCellStyle(getContentBoldAlignStyle(workbook));

		cell = getCell(sheet, 40, 2);
        setRegionStyle(sheet, new Region(40,(short)2,40,(short)(3)),getContentBoldAlignStyle(workbook));
        sheet.addMergedRegion(new Region(40,(short)2,40,(short)(3)));

        for(int i=4; i<=colNum-1;i++){
    		cell = getCell(sheet, 36, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
    		cell = getCell(sheet, 37, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
    		cell = getCell(sheet, 40, i);
			cell.setCellStyle(getContentBoldAlignStyle(workbook));
        }
        
        //备注行
		cell = getCell(sheet, 41, 0);
        setRegionStyle(sheet, new Region(41,(short)0,41,(short)(colNum-1 - 2)),getBzStyle(workbook));
        sheet.addMergedRegion(new Region(41,(short)0,41,(short)(colNum-1 - 2)));
        
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
        font.setFontHeightInPoints((short) 20);//设置字体大小  
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
	private HSSFCellStyle getTittleStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyle = workbook.createCellStyle(); //标题样式
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平    
		HSSFFont font = workbook.createFont();	
		font.setFontHeightInPoints((short)11);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗 
		cellStyle.setFont(font);
        return cellStyle;
	}
	private HSSFCellStyle getContentBoldAlignStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyle = workbook.createCellStyle(); //标题样式
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平    
		HSSFFont font = workbook.createFont();	
		font.setFontHeightInPoints((short)10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗 
		cellStyle.setFont(font);
        return cellStyle;
	}
	private HSSFCellStyle getContentBoldVerticalStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyle = workbook.createCellStyle(); //标题样式
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平    
        cellStyle.setRotation((short)255);
		HSSFFont font = workbook.createFont();	
		font.setFontHeightInPoints((short)10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗 
		cellStyle.setFont(font);
        return cellStyle;
	}
	private HSSFCellStyle getContentNormalStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyle = workbook.createCellStyle(); //标题样式
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平    
		HSSFFont font = workbook.createFont();	
		font.setFontHeightInPoints((short)10);
		cellStyle.setFont(font);
        return cellStyle;
	}
	private HSSFCellStyle getBzStyle(HSSFWorkbook workbook){
        //设置样式
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 样式对象    
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 水平    
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);//设置字体大小  
        cellStyle.setFont(font);
        return cellStyle;
	}
	
	/**
	* 导出Excel
	* @param sstjid
	* @return boolean
	*/
	public synchronized String exportExcel(String sstjid){
		String path = "";

	    /*Zshgadj gadj = ZshgadjService.service.findById_edit(sstjid);
	    short rowHeight_x = 700;//小单元格行高

		word_index++;
		ExcelDic += (new Date().getYear()+1900)+"/";
		String path = ExcelDic + "report_"+ word_index + ".xls";

		String rootPath = PathKit.getWebRootPath().replace("\\", "/");
		rootPath = rootPath.substring(0, rootPath.lastIndexOf("/"));

		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sheet1");//打开Excel表格在底端可以看到
        sheet.setColumnWidth(0, 7800);//设置列宽
        sheet.setColumnWidth(1, 9600);
        sheet.setColumnWidth(2, 7800);
        sheet.setColumnWidth(3, 9600);
        
        
        //设置样式
        HSSFCellStyle cellStyle = workbook.createCellStyle(); // 样式对象    
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平    
        cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框    
        HSSFFont font = workbook.createFont();//设置字体
        font.setFontName("Arial");    
        font.setFontHeightInPoints((short) 20);//设置字体大小  
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗 
        cellStyle.setFont(font);
        
        //设置样式
        HSSFCellStyle cellStyle_T = workbook.createCellStyle(); // 样式对象    
        cellStyle_T.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle_T.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平    
        cellStyle_T.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        cellStyle_T.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        cellStyle_T.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        cellStyle_T.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        cellStyle_T.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex()); //设置颜色
        HSSFFont font_t = workbook.createFont();//设置字体
        font_t.setFontName("Arial");    
        font_t.setFontHeightInPoints((short) 11);//设置字体大小  
        font_t.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//加粗 
        cellStyle_T.setFont(font_t);
        
        //设置样式
        HSSFCellStyle cellStyle_C = workbook.createCellStyle(); // 样式对象    
        cellStyle_C.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直    
        cellStyle_C.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框    
        cellStyle_C.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框    
        cellStyle_C.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框    
        cellStyle_C.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        HSSFFont font_c = workbook.createFont();//设置字体
        font_c.setFontName("Arial");    
        font_c.setFontHeightInPoints((short) 11);//设置字体大小  
        cellStyle_C.setFont(font_c);
        cellStyle_C.setWrapText(true);// 自动换行 
        
        int indexRow = 0;
        // 创建第一行   
        HSSFRow row0 = sheet.createRow(indexRow);  
        row0.setHeight((short) 1100);// 设置行高      
        // 创建第一列   
        HSSFCell cell0 = row0.createCell(indexRow++);   
        cell0.setCellValue(new HSSFRichTextString("表"));   
        cell0.setCellStyle(cellStyle);      
        //合并表头单元格
        setRegionStyle(sheet, new Region(0,(short)0,0,(short)3),cellStyle);
        sheet.addMergedRegion(new Region(
        0 //first row (0-based) from 行  
        ,(short)0 //first column (0-based) from 列     
        ,0//last row  (0-based)  to 行
        ,(short)3//last column  (0-based)  to 列     
        ));
		   
        
        // 创建第二行   
        HSSFRow row1 = sheet.createRow(indexRow++);   
        row1.setHeight(rowHeight_x);
        HSSFCell cell1_1 = row1.createCell(0);   
        cell1_1.setCellValue(new HSSFRichTextString("姓名"));   
        cell1_1.setCellStyle(cellStyle_T); 
        HSSFCell cell2_1 = row1.createCell(1);   
        cell2_1.setCellValue(new HSSFRichTextString(gadj.getStr(Zshgadj.column_xahzxm)));   
        cell2_1.setCellStyle(cellStyle_C);
        HSSFCell cell3_1 = row1.createCell(2);   
        cell3_1.setCellValue(new HSSFRichTextString("审核人"));   
        cell3_1.setCellStyle(cellStyle_T);
        HSSFCell cell4_1 = row1.createCell(3);   
        cell4_1.setCellValue(new HSSFRichTextString(gadj.getStr(Zshgadj.column_shr)));   
        cell4_1.setCellStyle(cellStyle_C);
        
        
        // 创建第三行  （合并单元格）
        HSSFRow row9 = sheet.createRow(indexRow++);
        row9.setHeight((short)1500);
        HSSFCell cell1_9 = row9.createCell(0);   
        cell1_9.setCellValue(new HSSFRichTextString("简要情况"));   
        cell1_9.setCellStyle(cellStyle_T); 
        HSSFCell cell2_9 = row9.createCell(1);   
        cell2_9.setCellValue(new HSSFRichTextString(gadj.getStr(Zshgadj.column_jyaq)));
        //合并表头单元格
        setRegionStyle(sheet, new Region(indexRow-1,(short)1,indexRow-1,(short)3),cellStyle_C);
        sheet.addMergedRegion(new Region(indexRow-1,(short)1,indexRow-1,(short)3));
        
        
        FileOutputStream outputStream = null;
		try {
    		outputStream = new FileOutputStream(rootPath + path);
    		workbook.write(outputStream); 
    		outputStream.flush();
    		outputStream.close();
    	} catch (FileNotFoundException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}finally {
    		try {
    		    if(workbook != null){
    		        workbook.close();
    		    }
    		    if(outputStream != null){
    		        outputStream.close();
    		    }
    		} catch (IOException e) {
    		    e.printStackTrace();
    		}
    	}*/
		return path;
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

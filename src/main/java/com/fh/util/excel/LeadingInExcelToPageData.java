package com.fh.util.excel;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.TmplInputTips;
import com.fh.util.PageData;
import com.fh.util.base.StochasticUtil;
import com.fh.util.enums.EmplGroupType;


/**
 * SpringMVC 读取Excle工具类
* @ClassName: LeadingInExcelToPageData
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 张晓柳
* @date 2017年2月13日
*
 */
public class LeadingInExcelToPageData<T> {
    
    //log4j输出
    private Logger logger = Logger.getLogger(this.getClass());
    // 时间的格式
    private String format="yyyy-MM-dd";

    /**
     * 无参构造
     */
    public LeadingInExcelToPageData() {
        super();
    }
    
    /**
     * 构造设置显示时间的格式
     * @param format 例："yyyy-MM-dd"
     */
    public LeadingInExcelToPageData(String format) {
        super();
        this.format = format;
    }
    
    /**
     * 设置显示时间的格式
     * @param format 例："yyyy-MM-dd"
     */
    public void setFormat(String format) {
        this.format = format;
    }
    
    /**
     * 上传Excle文件、并读取其中数据、返回list数据集合
     * @param multipart
     * @param propertiesFileName    properties文件名称
     * @param kyeName                properties文件中上传存储文件的路径
     * @param sheetIndex            读取Excle中的第几页中的数据
     * @param titleAndAttribute        标题名与实体类属性名对应的Map集合
     * @param clazz                    实体类.class
     * @return                        返回读取出的List集合
     * @throws Exception
     */
    public Map<Integer, Object> uploadAndRead(MultipartFile multipart,String propertiesFileName, String kyeName,int sheetIndex,
            Map<String, String> titleAndAttribute,
            Map<String, TableColumns> map_HaveColumnsList,
    		Map<String, TmplConfigDetail> map_SetColumnsList, Map<String, Object> DicList,
			Boolean bolIsDicSetSAL_RANGE, Boolean bolIsDicSetUSER_CATG, 
			Map<String, TmplInputTips> TmplInputTipsList, List<String> ImportNotHaveTransferList) throws Exception{
        
            String originalFilename=null;
            int i = 0;
            boolean isExcel2003 = false;
            
            //取出文件名称
            originalFilename = multipart.getOriginalFilename();
            
            //判断Excel是什么版本
            i = isExcleVersion(originalFilename);
            if(i==0)return null; else if(i==1)isExcel2003=true;
            
            String filePath = readPropertiesFilePathMethod( propertiesFileName, kyeName);
            File filePathname = this.upload(multipart, filePath, isExcel2003);
            Map<Integer, Object> judgementVersion = judgementVersion(filePathname, sheetIndex, titleAndAttribute, map_HaveColumnsList, 
            		isExcel2003, map_SetColumnsList, DicList, bolIsDicSetSAL_RANGE, bolIsDicSetUSER_CATG, TmplInputTipsList, ImportNotHaveTransferList);
        
        return judgementVersion;
    }
    
    /**
     * @描述：判断Excel是什么版本
     * @param originalFilename
     * @return
     *         1 ：2003
     *         2 ：2007
     *         0 ：不是Excle版本
     */
    public int isExcleVersion(String originalFilename){
        int i = 0;
        
        if(originalFilename.matches("^.+\\.(?i)(xls)$"))i = 1; 
        else
        if(originalFilename.matches("^.+\\.(?i)(xlsx)$"))i = 2;
        
        return i;
    }

    /**
     * 读取properties文件中对应键的值
     * @param propertiesFileName
     * @param kyeName
     * @return value值
     */
    public String readPropertiesFilePathMethod(String propertiesFileName, String kyeName){
        
        //读取properties文件
        InputStream inputStream=null;
        Properties properties=null;
        String filePath=null;//读取出的文件路径
        try {
        	Class<?> ss=this.getClass();
        	ClassLoader dd = ss.getClassLoader();
        	URL ff = dd.getResource("/"+propertiesFileName+".properties");
          String propertiesPath = ff .getPath();
          inputStream= new FileInputStream(propertiesPath);
          properties=new Properties();
          properties.load(inputStream);
          filePath = properties.getProperty(kyeName);
        } catch (FileNotFoundException e1) {
            logger.error("未找到properties文件！", e1);
        } catch (IOException e1) {
            logger.error("打开文件流异常！", e1);
        } finally{
            //关闭流
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("关闭文件流异常！", e);
                }
            }
        }
        return filePath;
    }
    
    /**
     * SpringMVC 上传Excle文件至本地
     * @param multipart
     * @param filePath        上传至本地的文件路径    例：D:\\fileupload
     * @param isExcel2003    是否是2003版本的Excle文件
     * @return                返回上传文件的全路径
     * @throws Exception
     */
    public File upload(MultipartFile multipart,String filePath,boolean isExcel2003) throws Exception{
        //文件后缀
        String extension=".xlsx";
        if(isExcel2003)extension=".xls";
        //指定上传文件的存储路径
        File file=new File(filePath);
        
        //接口强转实现类
        CommonsMultipartFile commons=(CommonsMultipartFile) multipart;
        
        //判断所属路径是否存在、不存在新建
        if(!file.exists())file.mkdirs();
        
        /*
         * 新建一个文件
         * LongIdWorker longID工具类
         */
        File filePathname=new File(file+File.separator+StochasticUtil.getUUID()+extension);
        
        //将上传的Excel写入新建的文件中
        try {
            commons.getFileItem().write(filePathname);
        } catch (Exception e) {
            logger.error("写入文件异常", e);
        }
        
        return filePathname;
    }
    
    /**
     * 读取本地Excel文件返回List集合
     * @param filePathname
     * @param sheetIndex
     * @param titleAndAttribute
     * @param clazz
     * @param isExcel2003
     * @return
     * @throws Exception
     */
    public Map<Integer, Object> judgementVersion(File filePathname,int sheetIndex,Map<String, String> titleAndAttribute,
    		Map<String, TableColumns> map_HaveColumnsList,boolean isExcel2003,
    		Map<String, TmplConfigDetail> map_SetColumnsList, Map<String, Object> DicList,
			Boolean bolIsDicSetSAL_RANGE, Boolean bolIsDicSetUSER_CATG, 
			Map<String, TmplInputTips> TmplInputTipsList, List<String> ImportNotHaveTransferList) throws Exception{
        
        FileInputStream is=null;
        POIFSFileSystem fs=null;
        Workbook workbook=null;
            try {
                //打开流
                is=new FileInputStream(filePathname);
                if(isExcel2003){
                    //把excel文件作为数据流来进行传入传出
                    fs=new POIFSFileSystem(is);
                    //解析Excel 2003版
                    workbook = new HSSFWorkbook(fs);
                }else{
                    //解析Excel 2007版
                    workbook=new XSSFWorkbook(is);
                }
                
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        
        return readExcelTitle(workbook,sheetIndex,titleAndAttribute,map_HaveColumnsList, map_SetColumnsList, DicList, bolIsDicSetSAL_RANGE, 
        		bolIsDicSetUSER_CATG, TmplInputTipsList, ImportNotHaveTransferList);
    }

    /**
     * 判断接收的Map集合中的标题是否于Excle中标题对应
     * @param workbook
     * @param sheetIndex
     * @param titleAndAttribute
     * @param clazz
     * @return
     * @throws Exception
     */
    private Map<Integer, Object> readExcelTitle(Workbook workbook,int sheetIndex,Map<String, String> titleAndAttribute,
    		Map<String, TableColumns> map_HaveColumnsList,
    		Map<String, TmplConfigDetail> map_SetColumnsList, Map<String, Object> DicList,
			Boolean bolIsDicSetSAL_RANGE, Boolean bolIsDicSetUSER_CATG, 
			Map<String, TmplInputTips> TmplInputTipsList, List<String> ImportNotHaveTransferList) throws Exception{

        //得到第一个shell  
        Sheet sheet = workbook.getSheetAt(sheetIndex);
        
        // 获取标题
        Row titelRow = sheet.getRow(0);
        Map<Integer, String> attribute = new HashMap<Integer, String>();
        if (titleAndAttribute != null) {
            for (int columnIndex = 0; columnIndex < titelRow.getLastCellNum(); columnIndex++) {
                Cell cell = titelRow.getCell(columnIndex);
                if (cell != null) {
                    String key = TransferSbcDbc.ToDBC(cell.getStringCellValue().trim());
                    String value = titleAndAttribute.get(key);
                    if (value == null) {
                        value = key;
                    }
                    attribute.put(Integer.valueOf(columnIndex), value);
                }
            }
        } else {
            for (int columnIndex = 0; columnIndex < titelRow.getLastCellNum(); columnIndex++) {
                Cell cell = titelRow.getCell(columnIndex);
                if (cell != null) {
                    String key = cell.getStringCellValue().trim();
                    attribute.put(Integer.valueOf(columnIndex), key);
                }
            }
        }

        return readExcelValue(workbook,sheet,attribute,map_HaveColumnsList, map_SetColumnsList, DicList, 
        		bolIsDicSetSAL_RANGE, bolIsDicSetUSER_CATG, TmplInputTipsList, ImportNotHaveTransferList);
        
    }
    
    /**
     * 获取Excle中的值
     * @param workbook
     * @param sheet
     * @param attribute
     * @param clazz
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	private Map<Integer, Object> readExcelValue(Workbook workbook,Sheet sheet,Map<Integer, String> attribute,
			Map<String, TableColumns> map_HaveColumnsList,
			Map<String, TmplConfigDetail> map_SetColumnsList, Map<String, Object> DicList,
			Boolean bolIsDicSetSAL_RANGE, Boolean bolIsDicSetUSER_CATG, 
			Map<String, TmplInputTips> TmplInputTipsList, List<String> ImportNotHaveTransferList) throws Exception{
    	if(!(map_HaveColumnsList!=null && map_HaveColumnsList.size()>0)){
    		map_HaveColumnsList = new HashMap<String, TableColumns>();
    	}
    	if(!(map_SetColumnsList!=null && map_SetColumnsList.size()>0)){
    		map_SetColumnsList = new HashMap<String, TmplConfigDetail>();
    	}
    	if(!(DicList!=null && DicList.size()>0)){
    		DicList = new HashMap<String, Object>();
    	}

    	Map<Integer, Object> returnMap = new HashMap<Integer, Object>();
    	//验证数据，returnErrorCustomn有数据，可以导入，但界面有提示，
    	Map<String, String> returnErrorCustomn = new HashMap<String, String>();
    	//验证数据，returnErrorMust有数据，不能导入，界面有提示，
    	Map<String, String> returnErrorMust = new HashMap<String, String>();
    	Boolean bolError = false;
    	Boolean bolCust = false;
        List<PageData> info=new ArrayList<PageData>();
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator(); 
        //获取标题行列数
        //int titleCellNum = sheet.getRow(0).getLastCellNum();
        // 获取值
        int LastRowNum = sheet.getLastRowNum();
        for (int rowIndex = 1; rowIndex <= LastRowNum; rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            if(row == null) continue;  
//            logger.debug("第--" + rowIndex);
            
            ////    1.若当前行的列数不等于标题行列数就放弃整行数据(若想放弃此功能注释4个步骤即可)
            //int lastCellNum = row.getLastCellNum();
            //if(titleCellNum !=  lastCellNum){
            //    continue;
            //}
            
            // 2.标记
            boolean judge = true;
            PageData obj = new PageData();
            String returnErrorUserCode = "";
            String returnErrorCustomnMessage = "";
            String returnErrorMustMessage = "";
            for (int columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {//这里小于等于变成小于
                Cell cell = row.getCell(columnIndex);
                if(cell == null) continue;
                
                //处理单元格中值得类型
                CellValue cellValue = evaluator.evaluate(cell);   
                if(cellValue == null) continue;  

                String COL_CODE = attribute.get(Integer.valueOf(columnIndex));

                String value = ""; //cellValue == null ? "" : cellValue.formatAsString()
                switch (cellValue.getCellType()) {  
                    case Cell.CELL_TYPE_BOOLEAN:  
                    	Boolean bolValue = cellValue.getBooleanValue();  
                    	value = bolValue.toString().trim();
                        break;  
                    case Cell.CELL_TYPE_NUMERIC:  
                    	if(COL_CODE.equals("USER_CODE") || COL_CODE.equals("BUSI_DATE") || COL_CODE.equals("STAFF_IDENT")){
                    		cell.setCellType(Cell.CELL_TYPE_STRING);
    						String temp = cell.getStringCellValue();
    						value = temp.trim();
                    	} else {
                        	Number numValue = cellValue.getNumberValue();  
                        	value = numValue.toString().trim();
                    	}
                        break;  
                    case Cell.CELL_TYPE_STRING:  
                    	value = cellValue.getStringValue().trim();  
                        break;  
                    case Cell.CELL_TYPE_BLANK:  
                        break;  
                    case Cell.CELL_TYPE_ERROR:  
                        break;  
                    case Cell.CELL_TYPE_FORMULA:   
                        break;  
                }
                
                // 3.单元格中的值等于null或等于"" 就放弃整行数据
                if(!(value != null && !value.trim().equals(""))){
                	continue;
                }
                
    			if(map_SetColumnsList != null && map_SetColumnsList.size() > 0){
    				TmplConfigDetail itemCol = map_SetColumnsList.get(COL_CODE);
    				if(itemCol != null){
            			String trans = itemCol.getDICT_TRANS();
            			if(trans != null && !trans.trim().equals("")){
            				Map<String, String> dicAdd = (Map<String, String>) DicList.getOrDefault(trans, new HashMap<String, String>());
                            String getKey = "";
							String transValue = value.replace(" ", "").replace("(", "").replace(")", "").replace("（", "").replace("）", "");
            				for (Map.Entry<String, String> dic :dicAdd.entrySet())  {
            					if(value.equals(dic.getValue().toString())){
            						getKey = dic.getKey();
                                }
            			    }

            				//现在没用上，特殊导入规则及字段验证
            				if(!(getKey != null && !getKey.trim().equals(""))){
            					if(TmplInputTipsList!=null && TmplInputTipsList.size()>0){
                					TmplInputTips inputTips = TmplInputTipsList.get(COL_CODE);
                					if(inputTips!=null){
                    					String inputTipsDICT_TRANS = "";
                    					if(inputTips!=null && inputTips.getDICT_TRANS()!=null && !inputTips.getDICT_TRANS().trim().equals("")){
                    						inputTipsDICT_TRANS = inputTips.getDICT_TRANS().trim();
                    					}
                    					if(String.valueOf(1).equals(inputTipsDICT_TRANS)){
                            				returnErrorMustMessage += inputTips.getDIC_PREFIX() + itemCol.getCOL_NAME() + " : " + value + inputTips.getDIC_SUFFIX() + " ";
                            				bolError = true;
                    					} else {
                        					returnErrorCustomnMessage += inputTips.getDIC_PREFIX() + itemCol.getCOL_NAME() + " : " + value + inputTips.getDIC_SUFFIX() + " ";
                        					bolCust = true;
                    					}
                					} else {
                    					returnErrorCustomnMessage += itemCol.getCOL_NAME() + " : " + value + " ";
                    					bolCust = true;
                					}
            					} else {
                					returnErrorCustomnMessage += itemCol.getCOL_NAME() + " : " + value + " ";
                					bolCust = true;
                					if(ImportNotHaveTransferList!=null && ImportNotHaveTransferList.contains(COL_CODE)){
                    					returnErrorMustMessage += itemCol.getCOL_NAME() + " : " + value + " ";
                    					bolError = true;
                					}
            					}
            				}
            				value = getKey;
            			}
    				}
    			}
    			TableColumns fieldTable = (TableColumns) map_HaveColumnsList.get(COL_CODE);
    			String fieldType = "";
    			if(fieldTable!=null && fieldTable.getData_type()!=null){
    				fieldType = fieldTable.getData_type();
    			}
				
                Object agge = value;
                if (fieldType.toUpperCase().equals(Date.class.getName().toUpperCase())) {
                    agge = new SimpleDateFormat(format).parse(value);
                } else if (fieldType.toUpperCase().equals(Boolean.class.getName().toUpperCase())) {
                    agge = "Y".equals(value) || "1".equals(value);
                }
                obj.put(COL_CODE, agge);

            	if(COL_CODE.equals("USER_CODE")){
                	returnErrorUserCode = value;
                	String haveMustMessage = returnErrorMust.get(returnErrorUserCode);
                	if(haveMustMessage==null) haveMustMessage = "";
                	returnErrorMustMessage = haveMustMessage + returnErrorMustMessage;
                	String haveCustomnMessage = returnErrorCustomn.get(value);
                	if(haveCustomnMessage==null) haveCustomnMessage = "";
                	returnErrorCustomnMessage = haveCustomnMessage + returnErrorCustomnMessage;
            	}
            }
            
            if(!obj.isEmpty()){
            	if(judge)info.add(obj);
            }
            returnErrorCustomn.remove(returnErrorUserCode);
            returnErrorCustomn.put(returnErrorUserCode, returnErrorCustomnMessage);
            returnErrorMust.remove(returnErrorUserCode);
            returnErrorMust.put(returnErrorUserCode, returnErrorMustMessage);
        }
        returnMap.put(1, info);
        if(bolCust){
            returnMap.put(2, returnErrorCustomn);
        }
        if(bolError){
            returnMap.put(3, returnErrorMust);
        }
        return returnMap;
    }
}

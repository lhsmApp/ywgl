package com.fh.controller.dataReporting.erpdelacctapplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.CommonBase;
import com.fh.entity.Page;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.User;
import com.fh.exception.CustomException;
import com.fh.service.dataReporting.erpdelacctapplication.ERPDelAcctApplicationManager;
import com.fh.service.dataReporting.grcperson.GRCPersonManager;
import com.fh.service.sysConfig.sysconfig.SysConfigManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.StringUtil;
import com.fh.util.date.DateFormatUtils;
import com.fh.util.date.DateUtils;
import com.fh.util.enums.SysDeptTime;
import com.fh.util.excel.LeadingInExcelToPageData;
import com.fh.util.excel.TransferSbcDbc;

import net.sf.json.JSONArray;

/**
 * 说明：ERP删除账号申请处理模块 创建人：xinyuLo 创建时间：2019-10-11
 */
@Controller
@RequestMapping(value = "/erpdelacctapplication")
public class ERPDelAcctApplicationController extends BaseController {

    String menuUrl = "erpdelacctapplication/list.do"; // 菜单地址(权限用)
    @Resource(name = "erpdelacctapplicationService")
    private ERPDelAcctApplicationManager erpdelacctapplicationService;
    @Resource(name = "tmplconfigService")
    private TmplConfigService tmplconfigService;
    @Resource(name = "sysconfigService")
    private SysConfigManager sysconfigService;
    @Resource(name = "grcpersonService")
    private GRCPersonManager grcpersonService;

    Map<String, TableColumns> Map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
    Map<String, TmplConfigDetail> Map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();

    /**
     * 保存与修改合并类
     * 
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/saveAndEdit")
    @ResponseBody
    public CommonBase saveAndEdit() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "保存与修改ERPDelAcctApplication");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        // //校验权限
        // 初始化变量
        String listData = null;
        String staffId = null;
        PageData pd = new PageData();
        CommonBase commonBase = new CommonBase();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        commonBase.setCode(-1);
        pd = this.getPageData();
        listData = pd.getString("listData");
        JSONArray array = JSONArray.fromObject(listData);
        @SuppressWarnings("unchecked")
        List<String> listTransferData = (List<String>) JSONArray.toCollection(array, PageData.class);// 过时方法
        for (int i = 0; i < listTransferData.size(); i++) {
            staffId = listTransferData.get(i).trim();
            PageData pageData = new PageData();
            pageData.put("CONFIRM_STATE", "1"); // 1未上报 2已上报 3撤销上报 4已驳回
            pageData.put("USER_DEPART", user.getUNIT_CODE());
            pageData.put("BUSI_DATE", DateUtils.getCurrentDateMonth()); // 业务期间
            pageData.put("STATE", "1");
            pageData.put("BILL_USER", user.getUSER_ID());
            pageData.put("BILL_DATE", DateUtils.getCurrentTime(DateFormatUtils.TIME_NOFUll_FORMAT));
            pageData.put("ID", listTransferData.get(i++));
            pageData.put("STAFF_CODE", listTransferData.get(i++).trim());
            pageData.put("STAFF_NAME", listTransferData.get(i++).trim());
            pageData.put("DEPART_CODE", listTransferData.get(i++).trim());
            pageData.put("UNITS_DEPART", listTransferData.get(i++).trim());
            pageData.put("STAFF_POSITION", listTransferData.get(i++).trim());
            pageData.put("STAFF_JOB", listTransferData.get(i++).trim());
            pageData.put("STAFF_MODULE", listTransferData.get(i++).trim());
            pageData.put("PHONE", listTransferData.get(i++).trim());
            pageData.put("MAIL", listTransferData.get(i++).trim());
            pageData.put("PERMISSION_CHANGE", listTransferData.get(i++).trim());
            pageData.put("APPLY_DATE", listTransferData.get(i++).trim());
            pageData.put("ACCOUNT_DELETE_REASON", listTransferData.get(i++).trim());
            pageData.put("NOTE", listTransferData.get(i).trim());
            if (null != staffId && !"".equals(staffId)) {// 如果有ID则进行修改
                erpdelacctapplicationService.edit(pageData);
            } else {// 如果无ID则进行新增
                erpdelacctapplicationService.save(pageData);
            }
        }
        commonBase.setCode(0);
        return commonBase;
    }

    /**
     * 列表
     * 
     * @param page
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "列表ERPDelAcctApplication");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        // //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        PageData data = new PageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        pd = this.getPageData();
        String confirmState = pd.getString("confirmState");
        String busiDate = pd.getString("busiDate");
        pd.put("KEY_CODE", "erpdelMustKey");
        data.put("BUSI_TYPE", SysDeptTime.ERP_DAA.getNameKey());
        data.put("DEPT_CODE", user.getUNIT_CODE());
        //String date = sysconfigService.currentSection(pd);// 获取当期时间
        Date dNow = new Date( );
	    SimpleDateFormat ft = new SimpleDateFormat ("yyyyMM");
		String date = ft.format(dNow);
        // 需要获取必填的内容，然后输出到页面上
        String mandatory = sysconfigService.getSysConfigByKey(pd);

        if (null == confirmState || StringUtil.isEmpty(confirmState)) {
            pd.put("confirmState", "1"); // 1未上报 2已上报 3撤销上报 4已驳回
        }
        if (null == busiDate || StringUtil.isEmpty(busiDate)) {
            //pd.put("busiDate", date);
        }
        pd.put("month", date);
        pd.put("DEPART_CODE", user.getUNIT_NAME());
        pd.put("USER_DEPART", user.getUNIT_CODE());
        pd.put("mandatory", mandatory);
        page.setPd(pd);
        PageData pageData = grcpersonService.findSysDeptTime(data);
        if (null != pageData && pageData.size() > 0) {
            pd.putAll(pageData);
        }
        // 获取业务期间
        List<PageData> listBusiDate = DateUtil.getMonthList("BUSI_DATE", date);
        List<PageData> varList = erpdelacctapplicationService.list(page); // 列出ERPDelAcctApplication列表
        mv.setViewName("dataReporting/erpdelacctapplication/erpdelacctapplication_list");
        mv.addObject("varList", varList);
        mv.addObject("listBusiDate", listBusiDate);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
//        mv.addObject("mandatory", mandatory);
        Map_SetColumnsList.put("BUSI_DATE", new TmplConfigDetail("BUSI_DATE", "录入时间", "1", false));
        Map_SetColumnsList.put("STAFF_CODE", new TmplConfigDetail("STAFF_CODE", "员工编号", "1", false));
        Map_SetColumnsList.put("STAFF_NAME", new TmplConfigDetail("STAFF_NAME", "员工姓名", "1", false));
        Map_SetColumnsList.put("DEPART_CODE", new TmplConfigDetail("DEPART_CODE", "二级单位", "1", false));
        Map_SetColumnsList.put("UNITS_DEPART", new TmplConfigDetail("UNITS_DEPART", "三级单位", "1", false));
        Map_SetColumnsList.put("STAFF_POSITION", new TmplConfigDetail("STAFF_POSITION", "职务", "1", false));
        Map_SetColumnsList.put("STAFF_JOB", new TmplConfigDetail("STAFF_JOB", "岗位", "1", false));
        Map_SetColumnsList.put("STAFF_MODULE", new TmplConfigDetail("STAFF_MODULE", "模块", "1", false));
        Map_SetColumnsList.put("PHONE", new TmplConfigDetail("PHONE", "联络电话", "1", false));
        Map_SetColumnsList.put("MAIL", new TmplConfigDetail("MAIL", "电子邮件", "1", false));
        Map_SetColumnsList.put("PERMISSION_CHANGE", new TmplConfigDetail("PERMISSION_CHANGE", "权限变更", "1", false));
        Map_SetColumnsList.put("APPLY_DATE", new TmplConfigDetail("APPLY_DATE", "申请日期", "1", false));
        Map_SetColumnsList.put("ACCOUNT_DELETE_REASON",
                new TmplConfigDetail("ACCOUNT_DELETE_REASON", "账号删除原因", "1", false));
        Map_SetColumnsList.put("NOTE", new TmplConfigDetail("NOTE", "备注", "1", false));

        return mv;
    }

    /**
     * 批量删除
     * 
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/deleteAll")
    @ResponseBody
    public CommonBase deleteAll() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "批量删除ERPDelAcctApplication");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
        // //校验权限
        PageData pd = new PageData();
        CommonBase commonBase = new CommonBase();
        commonBase.setCode(-1);
        pd = this.getPageData();
        String DATA_IDS = pd.getString("DATA_IDS");
        if (null != DATA_IDS && !"".equals(DATA_IDS)) {
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            erpdelacctapplicationService.deleteAll(ArrayDATA_IDS);
            commonBase.setCode(0);
        } else {
            commonBase.setCode(-1);
        }
        return commonBase;
    }

    /**
     * 批量上报/撤销上报
     * 
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/report")
    @ResponseBody
    public CommonBase editReport() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "批量上报/撤销上报ERPDelAcctApplication");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;}
        // //校验权限
        PageData pd = new PageData();
        CommonBase commonBase = new CommonBase();
        commonBase.setCode(-1);
        pd = this.getPageData();
        String DATA_IDS = pd.getString("DATA_IDS");
        if (null != DATA_IDS && !"".equals(DATA_IDS)) {
            String arrayDATA_IDS[] = DATA_IDS.split(",");
            pd.put("arrayDATA_IDS", arrayDATA_IDS);
            erpdelacctapplicationService.editReportState(pd);
            commonBase.setCode(0);
        } else {
            commonBase.setCode(-1);
        }
        return commonBase;
    }

    /**
     * 打开上传EXCEL页面
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/goUploadExcel")
    public ModelAndView goUploadExcel() throws Exception {
        CommonBase commonBase = new CommonBase();
        commonBase.setCode(-1);
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("common/uploadExcel");
        mv.addObject("local", "erpdelacctapplication");
        mv.addObject("commonBaseCode", commonBase.getCode());
        mv.addObject("commonMessage", commonBase.getMessage());
        return mv;
    }

    /**
     * 导出到excel
     * 
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel(Page page) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "导出ERPOfficialAcctApplication到excel");
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("confirmState", pd.get("confirmState")); //1未上报 2已上报 3撤销上报 4已驳回
        pd.put("BUSI_DATE", pd.get("BUSI_DATE")); //月份
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
		pd.put("USER_DEPART", user.getUNIT_CODE()); //单位
        page.setPd(pd);
        List<PageData> varOList = erpdelacctapplicationService.exportList(page);
        return export(varOList, "", Map_SetColumnsList);
    }

    /**
     * 下载模版
     * 
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downExcel")
    public ModelAndView downExcel(Page page) throws Exception {

        PageData getPd = this.getPageData();
        List<PageData> varOList = erpdelacctapplicationService.exportModel(getPd);
        return export(varOList, "ERPOfficialAcctApplication", Map_SetColumnsList);
    }

    private ModelAndView export(List<PageData> varOList, String ExcelName,
            Map<String, TmplConfigDetail> map_SetColumnsList) throws Exception {
        ModelAndView mv = new ModelAndView();
        Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
        dataMap.put("filename", ExcelName);
        List<String> titles = new ArrayList<String>();
        List<PageData> varList = new ArrayList<PageData>();
        if (map_SetColumnsList != null && map_SetColumnsList.size() > 0) {
            for (TmplConfigDetail col : map_SetColumnsList.values()) {
                if (col.getCOL_HIDE().equals("1")) {
                    titles.add(col.getCOL_NAME());
                }
            }
            if (varOList != null && varOList.size() > 0) {
                for (int i = 0; i < varOList.size(); i++) {
                    PageData vpd = new PageData();
                    int j = 1;
                    for (TmplConfigDetail col : map_SetColumnsList.values()) {
                        if (col.getCOL_HIDE().equals("1")) {
                            Object getCellValue = varOList.get(i).get(col.getCOL_CODE().toUpperCase());
                            if (null == getCellValue) {
                                getCellValue = "";
                            }
                            vpd.put("var" + j, getCellValue.toString());
                            j++;
                        }
                    }
                    varList.add(vpd);
                }
            }
        }
        dataMap.put("titles", titles);
        dataMap.put("varList", varList);
        ObjectExcelView erv = new ObjectExcelView();
        mv = new ModelAndView(erv, dataMap);
        return mv;
    }

    /**
     * 从EXCEL导入到数据库
     * 
     * @param file
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked" })
    @RequestMapping(value = "/readExcel")
    public ModelAndView readExcel(@RequestParam(value = "excel", required = false) MultipartFile file)
            throws Exception {
        CommonBase commonBase = new CommonBase();
        commonBase.setCode(-1);

        // String strErrorMessage = "";
        // PageData getPd = this.getPageData();
        Map<String, Object> DicList = new HashMap<String, Object>();

        // 局部变量
        LeadingInExcelToPageData<PageData> testExcel = null;
        Map<Integer, Object> uploadAndReadMap = null;
        try {
            // 定义需要读取的数据
            String formart = "yyyy-MM-dd";
            String propertiesFileName = "config";
            String kyeName = "file_path";
            int sheetIndex = 0;
            Map<String, String> titleAndAttribute = null;
            // 定义对应的标题名与对应属性名
            titleAndAttribute = new LinkedHashMap<String, String>();

            // 配置表设置列
            if (Map_SetColumnsList != null && Map_SetColumnsList.size() > 0) {
                for (TmplConfigDetail col : Map_SetColumnsList.values()) {
                    titleAndAttribute.put(TransferSbcDbc.ToDBC(col.getCOL_NAME()), col.getCOL_CODE());
                }
            }

            // 调用解析工具包
            testExcel = new LeadingInExcelToPageData<PageData>(formart);
            // 解析excel，获取客户信息集合

            uploadAndReadMap = testExcel.uploadAndRead(file, propertiesFileName, kyeName, sheetIndex, titleAndAttribute,
                    Map_HaveColumnsList, Map_SetColumnsList, DicList, false, false, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("读取Excel文件错误", e);
            throw new CustomException("读取Excel文件错误:" + e.getMessage(), false);
        }
        boolean judgement = false;

        List<PageData> listUploadAndRead = (List<PageData>) uploadAndReadMap.get(1);
        if (listUploadAndRead != null && !"[]".equals(listUploadAndRead.toString()) && listUploadAndRead.size() >= 1) {
            judgement = true;
        }
        if (judgement) {
            User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
            for (PageData pageData : listUploadAndRead) {
                // 将每条数据插入新内容
                pageData.put("CONFIRM_STATE", "1"); // 1未上报 2已上报 3撤销上报 4已驳回
                pageData.put("USER_DEPART", StringUtil.toString(user.getUNIT_CODE(), ""));
                pageData.put("BUSI_DATE", DateUtils.getCurrentDateMonth()); // 业务期间
                pageData.put("STATE", "1");
                pageData.put("BILL_USER", user.getUSER_ID());
                pageData.put("BILL_DATE", DateUtils.getCurrentTime(DateFormatUtils.TIME_NOFUll_FORMAT));
            }
            erpdelacctapplicationService.grcUpdateDatabase(listUploadAndRead);
            commonBase.setCode(0);
        } else {
            commonBase.setCode(-1);
            commonBase.setMessage("TranslateUtil");
        }
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("common/uploadExcel");
        mv.addObject("local", "erpdelacctapplication");
        mv.addObject("commonBaseCode", commonBase.getCode());
        mv.addObject("commonMessage", commonBase.getMessage());
        return mv;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}

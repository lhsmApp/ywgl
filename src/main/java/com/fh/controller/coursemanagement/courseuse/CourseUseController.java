package com.fh.controller.coursemanagement.courseuse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.controller.common.Common;
import com.fh.entity.Page;
import com.fh.entity.TableColumns;
import com.fh.entity.TmplConfigDetail;
import com.fh.entity.system.User;
import com.fh.service.coursemanagement.coursedetail.CourseDetailManager;
import com.fh.service.coursemanagement.courseuse.CourseUseManager;
import com.fh.service.tmplconfig.tmplconfig.impl.TmplConfigService;
import com.fh.service.trainBase.CourseTypeManager;
import com.fh.service.trainplan.trainplan.TrainPlanManager;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.date.DateFormatUtils;

import net.sf.json.JSONArray;

/**
 * 说明：观看信息处理类 创建人：xinyuLo 创建时间：2019-11-13
 */
@Controller
@RequestMapping(value = "/courseuse")
public class CourseUseController extends BaseController {

    String menuUrl = "courseuse/list.do"; // 菜单地址(权限用)
    @Resource(name = "courseuseService")
    private CourseUseManager courseuseService;
    @Resource(name = "trainplanService")
    private TrainPlanManager trainplanService;
    @Resource(name = "coursedetailService")
    private CourseDetailManager coursedetailService;
    @Resource(name = "coursetypeService")
    private CourseTypeManager coursetypeService;
    @Resource(name = "tmplconfigService")
    private TmplConfigService tmplconfigService;

    String TableNameDetail = "TB_DI_GRC_PERSON"; // 表名
    Map<String, TableColumns> Map_HaveColumnsList = new LinkedHashMap<String, TableColumns>();
    Map<String, TmplConfigDetail> Map_SetColumnsList = new LinkedHashMap<String, TmplConfigDetail>();

    /**
     * 保存
     * 
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public ModelAndView save() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "新增CourseUse");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "add")) {
            return null;
        } // 校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("COURSEUSE_ID", this.get32UUID()); // 主键
        courseuseService.save(pd);
        mv.addObject("msg", "success");
        mv.setViewName("save_result");
        return mv;
    }

    /**
     * 列表
     * 
     * @param page
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView list(Page page) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "观看课程视频列表CourseUse");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        // //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        List<PageData> coursePage = new ArrayList<PageData>();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        pd = this.getPageData();
        String keywords = pd.getString("keywords"); // 关键词检索条件
        String nowDate = DateUtil.getDays();
        String userName = user.getUSERNAME();// 用户/学员ID
        if (null != keywords && !"".equals(keywords)) {
            pd.put("keywords", keywords.trim());
        }
        pd.put("STUDENT_ID", userName);
        pd.put("PLAN_TYPE", "2");
        page.setPd(pd);
        List<PageData> varList = trainplanService.courseList(page); // 列出CourseUse列表
        // 处理显示信息
        for (PageData pageData : varList) {
            // 先判断时间
            String endDate = pageData.getString("END_DATE");
            String startDate = pageData.getString("START_DATE");
            if (DateUtil.compareDates(endDate, nowDate) && DateUtil.compareDates(nowDate, startDate)) {
                coursePage.add(pageData);
            }
        }
        mv.setViewName("coursemanagement/courseuse/courseuse_list");
        mv.addObject("varList", coursePage);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
        return mv;
    }

    /**
     * 列表
     * 
     * @param page
     * @throws Exception
     */
    @RequestMapping(value = "/listDetail")
    public ModelAndView listDetail(Page page) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "列表CourseUseDetail");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        // //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd.put("CHAPTER_PARENT_ID", "0");
        List<PageData> varList = coursedetailService.listAll(pd); // 列出CourseDetail列表
        for (PageData pageData : varList) {
            // 通过父ID查询子id获取小节数
            Integer parentId = (int) pageData.get("CHAPTER_ID");
            Integer count = coursedetailService.countByParentId(parentId);
            pageData.put("count", count);
        }
        // 查询所有小节
        page.setPd(pd);
        List<PageData> listSection = coursedetailService.list(page);
        mv.addObject("COURSE_ID", pd.getString("COURSE_ID"));
        mv.addObject("varList", varList);
        mv.addObject("listSection", listSection);
        mv.setViewName("coursemanagement/courseuse/courseuse_detail");
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
        return mv;
    }

    /**
     * 列表
     * 
     * @param page
     * @throws Exception
     */
    @RequestMapping(value = "/goVideo")
    public ModelAndView goVideo() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "列表CourseUseDetail");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        // //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        PageData data = coursedetailService.findById(pd);
        pd.put("CHAPTER_PARENT_ID", data.get("CHAPTER_PARENT_ID"));
        List<PageData> varList = coursedetailService.listAll(pd); // 列出CourseDetail列表
        mv.addObject("pd", data);
        mv.addObject("COURSE_ID", pd.get("COURSE_ID"));
        mv.addObject("varList", varList);
        mv.setViewName("coursemanagement/courseuse/courseuse_video");
        mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
        return mv;
    }

    /**
     * 列表
     * 
     * @param page
     * @throws Exception
     */
    @RequestMapping(value = "/queryList")
    public ModelAndView queryList(Page page) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "统计列表CourseUse");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        // //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String keywords = pd.getString("keywords"); // 关键词检索条件
        if (null != keywords && !"".equals(keywords)) {
            pd.put("keywords", keywords.trim());
        }
        page.setPd(pd);
        List<PageData> varList = courseuseService.list(page); // 列出CourseUse列表
        List<PageData> courseTypePdList = new ArrayList<PageData>();
        JSONArray arr = JSONArray.fromObject(coursetypeService.listAllCourseTypeToSelect("0", courseTypePdList));
        for (PageData i : varList) {
            i.put("RPT_TIME", DateFormatUtils.formatString((String) i.get("RPT_TIME")));//格式化时间格式
        }
        Map_HaveColumnsList = Common.GetHaveColumnsMapByTableName(TableNameDetail, tmplconfigService);
        Map_SetColumnsList.put("COURSE_NAME", new TmplConfigDetail("COURSE_NAME", "课程名称", "1", false));
        Map_SetColumnsList.put("COURSE_TYPE_NAME", new TmplConfigDetail("COURSE_TYPE_NAME", "课程分类", "1", false));
        Map_SetColumnsList.put("CHAPTER_NAME", new TmplConfigDetail("CHAPTER_NAME", "小节名称", "1", false));
        Map_SetColumnsList.put("PLAY_TIME", new TmplConfigDetail("PLAY_TIME", "观看时长", "1", false));
        Map_SetColumnsList.put("STUDENT_NAME", new TmplConfigDetail("STUDENT_NAME", "观看人", "1", false));
        Map_SetColumnsList.put("RPT_TIME", new TmplConfigDetail("RPT_TIME", "观看时间", "1", false));

        mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));
        mv.setViewName("coursemanagement/courseuse/courseuse_query_list");
        mv.addObject("varList", varList);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
        return mv;
    }

    /**
     * 收集学习时长等数据
     * 
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveTime")
    @ResponseBody
    public void saveTime() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "保存学习时长CourseUseDetail");
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USER);
        PageData pd = new PageData();
        pd = this.getPageData();
        String rptTime = DateUtil.getTime().replace("-", "").replace(" ", "").replace(":", "");
        PageData pageData = coursedetailService.findSection(pd);
        pd.putAll(pageData);
        pd.put("VIEW_USER", user.getUSER_ID());
        pd.put("RPT_TIME", rptTime);
        courseuseService.save(pd);
    }

    /**
     * 导出到excel
     * 
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/excel")
    public ModelAndView exportExcel(Page page) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "导出CourseUse到excel");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
        PageData getPd = this.getPageData();
        // 页面显示数据的年月
        // getPd.put("SystemDateTime", SystemDateTime);
        page.setPd(getPd);
        List<PageData> varOList = courseuseService.list(page);
        return export(varOList, "", Map_SetColumnsList);
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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}

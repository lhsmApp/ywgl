package com.fh.controller.notice.notice;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.service.notice.notice.NoticeDetailManager;
import com.fh.service.notice.notice.NoticeItemManager;
import com.fh.service.notice.notice.NoticeManager;
import com.fh.service.trainBase.CourseTypeManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

import net.sf.json.JSONArray;

/**
 * 说明：信息通知处理类 创建人：jiachao 创建时间：2019-12-02
 */
@Controller
@RequestMapping(value = "/notice")
public class NoticeController extends BaseController {

    String menuUrl = "notice/list.do"; // 菜单地址(权限用)
    @Resource(name = "noticeService")
    private NoticeManager noticeService;

    @Resource(name = "coursetypeService")
    private CourseTypeManager coursetypeService;
    
    @Resource(name = "noticeDetailService")
    private NoticeDetailManager noticeDetailService;
    
    @Resource(name = "noticeItemService")
    private NoticeItemManager noticeItemService;

    /**
     * 保存
     * 
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/save")
    public void save(PrintWriter out) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "新增Notice");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        // //校验权限
        PageData pd = new PageData();
        pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        pd.put("NOTICE_USER", user.getUSER_ID());
        
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        pd.put("CREATE_DATE", format.format(new Date()));
        pd.put("USER_DEPART", user.getUNIT_CODE());
        pd.put("CREATE_USER", user.getUSER_ID());
        pd.put("STATE", 1);
        noticeService.save(pd);//向tb_notice表中插入一条数据
        
        Object scope_Arr = pd.get("scope_Arr");
        JSONArray json = JSONArray.fromObject(scope_Arr);
        
        
        List<PageData> detailArray = new ArrayList<PageData>();
        List<PageData> itemArray = new ArrayList<PageData>();
        for(int i=0;i<json.size();i++){
            net.sf.json.JSONObject job = json.getJSONObject(i);
            PageData p=new PageData();
            p.put("NOTICE_ID", pd.get("id"));
            p.put("USER_ID", job.get("id"));
            p.put("STATE", 0);
            detailArray.add(p);
        }
        for(int i=0;i<json.size();i++){
            net.sf.json.JSONObject job = json.getJSONObject(i);
            PageData p=new PageData();
            p.put("NOTICE_ID", pd.get("id"));
            p.put("ITEM_ID", i);
            p.put("BUSINESS_CODE", job.get("id"));
            p.put("BUSINESS_NAME", job.get("name"));
            itemArray.add(p);
        }
        pd.put("varList", detailArray);
        pd.put("varList2", itemArray);
        noticeDetailService.saveAll(pd);
        noticeItemService.saveAll(pd);
        
        out.write("{'sMsg':'success'}");
        out.close();
    }

    /**
     * 删除
     * 
     * @param out
     * @throws Exception
     */
    @RequestMapping(value = "/delete")
    public void delete(PrintWriter out) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "删除Notice");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return;
        } // 校验权限
        PageData pd = new PageData();
        pd = this.getPageData();
        noticeService.delete(pd);
        out.write("success");
        out.close();
    }

    /**
     * 修改
     * 
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/edit")
    public ModelAndView edit() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "修改Notice");
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;}
        // //校验权限
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        noticeService.edit(pd);
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
        logBefore(logger, Jurisdiction.getUsername() + "列表Notice");
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
        List<PageData> varList = noticeService.list(page); // 列出Notice列表
        mv.setViewName("notice/notice/notice_list");
        mv.addObject("varList", varList);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
        return mv;
    }

    /**
     * 去新增页面
     * 
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goAdd")
    public ModelAndView goAdd() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        mv.setViewName("notice/notice/notice_edit");
        mv.addObject("msg", "save");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 去修改页面
     * 
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        pd = noticeService.findById(pd); // 根据ID读取
        mv.setViewName("notice/notice/notice_edit");
        mv.addObject("msg", "edit");
        mv.addObject("pd", pd);
        return mv;
    }

    /**
     * 去选择发布范围
     * 
     * @return
     */
    @RequestMapping(value = "/goSelectScope")
    public ModelAndView goSelectScope(Page page) throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        String keywords = pd.getString("keywords"); // 关键词检索条件
        if (null != keywords && !"".equals(keywords)) {
            pd.put("keywords", keywords.trim());
        }
        page.setPd(pd);
        List<PageData> varList = noticeService.showSysUserList(page); // 列出角色列表
        //List<PageData> varList = noticeService.showSysRoleList(page); // 列出角色列表
        mv.setViewName("notice/notice/select_scope/user");
        //mv.setViewName("notice/notice/select_scope/role");
        List<PageData> courseTypePdList = new ArrayList<PageData>();
        JSONArray arr = JSONArray.fromObject(coursetypeService.listAllCourseTypeToSelect("0", courseTypePdList));
        mv.addObject("zTreeNodes", (null == arr ? "" : arr.toString()));
        mv.addObject("varList", varList);
        mv.addObject("pd", pd);
        mv.addObject("QX", Jurisdiction.getHC()); // 按钮权限
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
    public Object deleteAll() throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "批量删除Notice");
        if (!Jurisdiction.buttonJurisdiction(menuUrl, "del")) {
            return null;
        } // 校验权限
        PageData pd = new PageData();
        Map<String, Object> map = new HashMap<String, Object>();
        pd = this.getPageData();
        List<PageData> pdList = new ArrayList<PageData>();
        String DATA_IDS = pd.getString("DATA_IDS");
        if (null != DATA_IDS && !"".equals(DATA_IDS)) {
            String ArrayDATA_IDS[] = DATA_IDS.split(",");
            noticeService.deleteAll(ArrayDATA_IDS);
            pd.put("msg", "ok");
        } else {
            pd.put("msg", "no");
        }
        pdList.add(pd);
        map.put("list", pdList);
        return AppUtil.returnObject(pd, map);
    }

    /**
     * 查询是否有我的消息
     * 
     * @param
     * @throws Exception
     */
    @RequestMapping(value = "/getMyNotice")
    public void getMyNotice(PrintWriter out) throws Exception {
        // if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
        // //校验权限
        PageData pd = new PageData();
        pd = this.getPageData();
        User user = (User) Jurisdiction.getSession().getAttribute(Const.SESSION_USERROL);
        pd.put("USER_ID", user.getUSER_ID());
        List<PageData> retData = noticeService.getMyNotice(pd);
        String json = JSON.toJSONString(retData);
        out.write("{\"sMsg\":\"success\",\"retData\":"+URLEncoder.encode(json, "UTF-8")+"}");
        out.close();
    }
    
    
    /**
     * 下载
     * 
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/download")
    public void downExcel(HttpServletResponse response) throws Exception {
        PageData pd = new PageData();
        pd = this.getPageData();
        String fileName = pd.getString("address").trim();
        FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILEOA + fileName,
                pd.getString("address"));
    }

    /**
     * 附件
     * 
     * @param pic
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/uploadFile")
    public void uploadPic(MultipartFile file, HttpServletResponse response) throws Exception {
        logBefore(logger, Jurisdiction.getUsername() + "视频上传CourseBase");
        String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;
        String fileName = FileUpload.fileUp(file, filePath, this.get32UUID());
        System.out.println(filePath + fileName);
        JSONObject json = new JSONObject();
        // 回调文件路径
        json.put("path", Const.FILEPATHFILE + fileName);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json.toString());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(format, true));
    }
}

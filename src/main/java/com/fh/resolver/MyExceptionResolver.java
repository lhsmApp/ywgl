package com.fh.resolver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.fh.entity.CommonBase;
import com.fh.exception.CustomException;



/**
 * MyExceptionResolver.java
* @ClassName: MyExceptionResolver
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月30日
*
 */
public class MyExceptionResolver implements HandlerExceptionResolver{

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		System.out.println("==============异常开始=============");
		ex.printStackTrace();
		System.out.println("==============异常结束=============");
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json;charset=utf-8");
		CustomException customException = null;
		CommonBase commonBase=new CommonBase();
		if(ex instanceof CustomException){
			customException = (CustomException)ex;
			if(customException.isAjaxExp()){
				//错误信息
				String message = customException.getMessage();
				commonBase.setCode(-1);
				commonBase.setMessage(message);
				try {
					String json=new JSONObject(commonBase).toString();
					PrintWriter writer =response.getWriter();
					writer.write(json);
					writer.flush();  
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}else{
				customException = new CustomException("未知错误:"+ex.toString(),false);
				String message = customException.getMessage();
				ModelAndView mv = new ModelAndView("error");
				mv.addObject("exception", message.replaceAll("\n", "<br/>"));
				return mv;
			}
		}else{
			customException = new CustomException("未知错误:"+ex.toString(),false);
			if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request  
                    .getHeader("X-Requested-With")!= null && request  
                    .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
				//错误信息
				String message = customException.getMessage();
				ModelAndView mv = new ModelAndView("error");
				mv.addObject("exception", message.replaceAll("\n", "<br/>"));
				return mv;
			}else{
				commonBase.setCode(-1);
				commonBase.setMessage(customException.getMessage());
				String json=new JSONObject(commonBase).toString();
				PrintWriter writer;
				try {
					writer = response.getWriter();
					writer.write(json);
					writer.flush();  
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}

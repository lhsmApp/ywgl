package com.fh.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateWeek {
	//获取某月最后一天
		public static String getLastDayOfMonth(int year,int month) {  
			Calendar cal = Calendar.getInstance();  
			//设置年份  
			cal.set(Calendar.YEAR,year);  
			//设置月份  
			cal.set(Calendar.MONTH, month-1);  
			//获取某月最大天数  
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
			//设置日历中月份的最大天数  
			cal.set(Calendar.DAY_OF_MONTH, lastDay);  
			//格式化日期  
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			String lastDayOfMonth = sdf.format(cal.getTime());  
			return lastDayOfMonth;  
			}  
		  //获取某月第一天是星期几
		  public static int getTheFirstDayWeekOfMonth(int year,int month,int day){
		        int week = 0 ;
		        Calendar cal = Calendar.getInstance();
		        cal.set(Calendar.YEAR, year);
		        cal.set(Calendar.MONTH, month-1);
		        cal.set(Calendar.DATE, day);
		        week = cal.get(Calendar.DAY_OF_WEEK)-1;	        
		        return week;
		    }
		  //在当前日期上增加天数
		  public static String addDays(String weekStart,int days)throws Exception{
			  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				 Calendar c = Calendar.getInstance();
				 c.setTime(sdf.parse(weekStart));
				 c.add(Calendar.DATE, days);  
				 weekStart = sdf.format(c.getTime()); 			
				return sdf.format(c.getTime());
		    }
}

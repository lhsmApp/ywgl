package com.fh.util;

import java.text.DecimalFormat;

/**
 * 
* @ClassName: DecimalUtil
* @Description: TODO(这里用一句话描述这个类的作用)
* @author zhangxiaoliu
* @date 
*
 */
public class DecimalUtil {
	
	private final static DecimalFormat df = new DecimalFormat("#.##");   

	/**
	 * 
	 * @return
	 */
	//public static String fomatDate(double dou) {
    //    return df.format(dou);
	//}
	
    //截取两位小数位数
    public static double InterceptTwoDecimalDigits(double dou)
    {
    	double ret = 0;
            String[] strtax = String.valueOf(dou).split("\\.");
            if (strtax.length >= 2) {
                if (strtax[1].length() > 2) {
                	Integer substring3 = Integer.valueOf(strtax[1].substring(0, 3));
                    if (substring3 >= 995) {
                    	ret = Double.valueOf(strtax[0]) + 1;
                    } else {
                    	Integer substring2 = Integer.valueOf(strtax[1].substring(2, 3));
                    	Integer substring01 = Integer.valueOf(strtax[1].substring(0, 2));
                    	Integer dd = substring2 >= 5 ? (substring01 + 1) : substring01;
                    	String str = strtax[0] + "." + (dd >= 10 ? dd.toString() : ('0' + dd.toString()));
                    	ret = Double.valueOf(str);
                    }
                } else {
                	ret = dou;
                }
            } else {
            	ret = dou;
            }
        return ret;
    }
    
  
}

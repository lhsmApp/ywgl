package com.fh.util.excel;

/**
 * 半角全角互换
* @ClassName: TransferSbcDbc
* @Description: TODO(这里用一句话描述这个类的作用)
* @author 张晓柳
* @date 2017年11月6日
*
 */
public class TransferSbcDbc {  
	/**
     * 半角转全角
     * @param input String.
     * @return 全角字符串.
     */
    public static String ToSBC(String input) {
        String returnString = "";
        if(input!=null){
            char c[] = input.toCharArray();
            for (int i = 0; i < c.length; i++) {
              if (c[i] == ' ') {
                c[i] = '\u3000';
              } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
              }
            }
            returnString = new String(c);
        }
        return returnString;
    }

    /**
     * 全角转半角
     * @param input String.
     * @return 半角字符串
     */
    public static String ToDBC(String input) {
        String returnString = "";
        if(input!=null){
            char c[] = input.toCharArray();
            for (int i = 0; i < c.length; i++) {
              if (c[i] == '\u3000') {
                c[i] = ' ';
              } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);
              }
            }
            returnString = new String(c);
        }
        return returnString;
    }
}
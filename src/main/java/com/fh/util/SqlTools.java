package com.fh.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * JqGrid multipleSearch后台根据Request的Filters字段自动组装Where条件
* @ClassName: SqlTools
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年6月14日
*
 */
public class SqlTools {
	public interface CallBack {//回调函数接口  
        //public String executeData(String f,String o,String d);  
        public String executeField(String f);  
    }  
  
    private static Map<String, String> Q2Oper;  
  
    private SqlTools() {  
        
    }  
    
    private static void initMap(){
    	Q2Oper = new HashMap();  
        //['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']  
        Q2Oper.put("eq", " = ");  
        Q2Oper.put("ne", " <> ");  
        Q2Oper.put("lt", " < ");  
        Q2Oper.put("le", " <= ");  
        Q2Oper.put("gt", " > ");  
        Q2Oper.put("ge", " >= ");  
        Q2Oper.put("bw", " LIKE ");  
        Q2Oper.put("bn", " NOT LIKE ");  
        Q2Oper.put("in", " IN ");  
        Q2Oper.put("ni", " NOT IN ");  
        Q2Oper.put("ew", " LIKE ");  
        Q2Oper.put("en", " NOT LIKE ");  
        Q2Oper.put("cn", " LIKE ");  
        Q2Oper.put("nc", " NOT LIKE ");  
    }
  
    /**
     * 拼接生成Where条件
     * @param filter
     * @param cb
     * @return
     */
    public static String constructWhere(String filter,CallBack cb) {  
    	initMap();
        String query="";  
        if (!filter.isEmpty()) {  
            JsonObject jsono = new JsonParser().parse(filter).getAsJsonObject();  
            if (jsono.isJsonObject()) {  
                String group = jsono.get("groupOp").getAsString();  
                JsonElement rules = jsono.get("rules");  
                if(rules==null) return "";
                int i = 0;  
                for (JsonElement o : rules.getAsJsonArray()) {  
                    String field = o.getAsJsonObject().get("field").getAsString();  
                    String op = o.getAsJsonObject().get("op").getAsString();  
                    String data = o.getAsJsonObject().get("data").getAsString();  
                    if (!op.isEmpty() && !data.isEmpty()) {  
                        i++; 
                        if(cb!=null){
                        	field = cb.executeField(field);  
                        	//data = cb.executeData(field, op, data); 
                        	data = executeData(field, op, data);
                        }else{
                        	data = executeData(field, op, data);    
                        }
                        if(i==1)  
                            query=" AND ";  
                        else  
                            query +=" "+group+" ";  
                        if(op.equals("in")||op.equals("ni")){  
                            query+= field+Q2Oper.get(op)+" ("+data+")";  
                        } else {  
                            query+= field+Q2Oper.get(op)+data;  
                        }  
                    }  
                }  
            }  
        }  
        return query;  
    }  

    private static String executeData(String f, String o, String d) {  
        if(o.equals("bw") || o.equals("bn")) return "'" + d + "%'";  
        else if (o.equals("ew") || o.equals("en")) return "'%"+d+"'";  
        else if (o.equals("cn") || o.equals("nc")) return "'%" +d+ "%'";  
        else return "'" +d+ "'";  
    }  
}

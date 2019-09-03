package com.fh.util.collectionSql;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.fh.util.StringUtil;

/**
 * 集合分组
* @ClassName: GroupUtils
* @Description: TODO(这里用一句话描述这个类的作用)
* @author jiachao
* @date 2017年7月11日
*
 */
public class GroupUtils {

	/**
	 * 分组依据接口，用于集合分组时，获取分组依据
	* @ClassName: GroupBy
	* @Description: TODO(这里用一句话描述这个类的作用)
	* @author jiachao
	* @date 2017年7月11日
	*
	* @param <T>
	 */
    public interface GroupBy<T> {  
        T groupby(Object obj);  
    }  
  
    /** 
     *  
     * @param colls 
     * @param gb 
     * @return 
     */  
    public static final <T extends Comparable<T>, D> Map<T, List<D>> group(Collection<D> colls, GroupBy<T> gb) {  
        if (colls == null || colls.isEmpty()) {  
            System.out.println("分组集合不能为空!");  
            return null;  
        }  
        if (gb == null) {  
            System.out.println("分组依据接口不能为空!");  
            return null;  
        }  
        Iterator<D> iter = colls.iterator();  
        Map<T, List<D>> map = new HashMap<T, List<D>>();  
        while (iter.hasNext()) {  
            D d = iter.next();  
            T t = gb.groupby(d);  
            if (map.containsKey(t)) {  
                map.get(t).add(d);  
            } else {  
                List<D> list = new ArrayList<D>();  
                list.add(d);  
                map.put(t, list);  
            }  
        }  
        return map;  
    }  
    /** 
     * 将List<V>按照V的methodName方法返回值（返回值必须为K类型）分组，合入到Map<K, List<V>>中<br> 
     * 要保证入参的method必须为V的某一个有返回值的方法，并且该返回值必须为K类型 
     *  
     * @param list 
     *            待分组的列表 
     * @param map 
     *            存放分组后的map 
     * @param clazz 
     *            泛型V的类型 
     * @param methodName 
     *            方法名 
     */  
    public static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Class<V> clazz, String methodName) {  
        // 入参非法行校验  
        if (null == list || null == map || null == clazz || !StringUtil.isEmpty(methodName)) {  
            System.out.print("CommonUtils.listGroup2Map 入参错误，list：" + list + " ；map：" + map + " ；clazz：" + clazz + " ；methodName：" + methodName);  
            return;  
        }  
  
        // 获取方法  
        Method method = getMethodByName(clazz, methodName);  
        // 非空判断  
        if (null == method) {  
            return;  
        }  
  
        // 正式分组  
        listGroup2Map(list, map, method);  
    }  
    /** 
     * 根据类和方法名，获取方法对象 
     *  
     * @param clazz 
     * @param methodName 
     * @return 
     */  
    public static Method getMethodByName(Class<?> clazz, String methodName) {  
        Method method = null;  
        // 入参不能为空  
        if (null == clazz || !StringUtil.isEmpty(methodName)) {  
            System.out.print("CommonUtils.getMethodByName 入参错误，clazz：" + clazz + " ；methodName：" + methodName);  
            return method;  
        }  
  
        try {  
            method = clazz.getDeclaredMethod(methodName);  
        } catch (Exception e) {  
            System.out.print("类获取方法失败！");  
        }  
  
        return method;  
    }  
    /** 
     * 将List<V>按照V的某个方法返回值（返回值必须为K类型）分组，合入到Map<K, List<V>>中<br> 
     * 要保证入参的method必须为V的某一个有返回值的方法，并且该返回值必须为K类型 
     *  
     * @param list 
     *            待分组的列表 
     * @param map 
     *            存放分组后的map 
     * @param method 
     *            方法 
     */  
    @SuppressWarnings("unchecked")  
    public static <K, V> void listGroup2Map(List<V> list, Map<K, List<V>> map, Method method) {  
        // 入参非法行校验  
        if (null == list || null == map || null == method) {  
            System.out.print("CommonUtils.listGroup2Map 入参错误，list：" + list + " ；map：" + map + " ；method：" + method);  
            return;  
        }  
  
        try {  
            // 开始分组  
            Object key;  
            List<V> listTmp;  
            for (V val : list) {  
                key = method.invoke(val);  
                listTmp = map.get(key);  
                if (null == listTmp) {  
                    listTmp = new ArrayList<V>();  
                    map.put((K) key, listTmp);  
                }  
                listTmp.add(val);  
            }  
        } catch (Exception e) {  
            System.out.print("分组失败！");  
        }  
    } 
    
    
    /*public static void main(String[] args) {  
        // 准备一个集合  
  
        final int loop = 1000 * 1000;  
        List<Data> list = new ArrayList<Data>(); // size=8 * loop  
        for (int i = 0; i < loop; i++) {  
            list.add(new Data().setId(1L).setCourseId(200010L).setContent("AAA"));  
            list.add(new Data().setId(2L).setCourseId(200010L).setContent("BBB"));  
            list.add(new Data().setId(3L).setCourseId(200011L).setContent("CCC"));  
            list.add(new Data().setId(4L).setCourseId(200011L).setContent("DDD"));  
            list.add(new Data().setId(5L).setCourseId(200010L).setContent("EEE"));  
            list.add(new Data().setId(6L).setCourseId(200011L).setContent("FFF"));  
            list.add(new Data().setId(7L).setCourseId(200010L).setContent("GGG"));  
            list.add(new Data().setId(8L).setCourseId(200012L).setContent("HHH"));  
        }  
        // 进行分组 1  
        long time = System.currentTimeMillis();  
        Map<Long, List<Data>> map2 = new LinkedHashMap<Long, List<Data>>();  
        CommonUtils.listGroup2Map(list, map2, Data.class, "getId");// 输入方法名  
          
        long duration = System.currentTimeMillis() - time;  
  
        System.out.println("分组一执行：" + duration + "毫秒!");  
  
        // 分组二  
        time = System.currentTimeMillis();  
        Map<Long, List<Data>> map = CommonUtils.group(list, new GroupBy<Long>() {  
            @Override  
            public Long groupby(Object obj) {  
                Data d = (Data) obj;  
                return d.getCourseId(); // 分组依据为课程ID  
            }  
        });  
  
        duration = System.currentTimeMillis() - time;  
  
        System.out.println("分组二执行：" + duration + "毫秒!");  
  
    }*/   
}

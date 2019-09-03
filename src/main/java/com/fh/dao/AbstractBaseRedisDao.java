package com.fh.dao;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;  
import org.springframework.data.redis.serializer.RedisSerializer;  

/**
 * redis Dao    
* @ClassName: AbstractBaseRedisDao
* @Description: TODO(这里用一句话描述这个类的作用)
* @author lhsmplus
* @date 2017年6月30日
*
* @param <K>
* @param <V>
 */
public abstract class AbstractBaseRedisDao<K, V> {  
      
    @Resource(name="redisTemplate")
    protected RedisTemplate<K, V> redisTemplate;
  
    /** 
     * 设置redisTemplate 
     * @param redisTemplate the redisTemplate to set 
     */  
    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;  
    }  
      
    /** 
     * 获取 RedisSerializer 
     */  
    protected RedisSerializer<String> getRedisSerializer() {  
        return redisTemplate.getStringSerializer();  
    }  
}

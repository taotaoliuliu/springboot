package com.aebiz.common.zookeeper;

import java.util.concurrent.TimeUnit;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;

/**
 * redission 分布式锁
 * @author 55401
 *
 */
public class RedissonManager {

	
	    private static Config config = new Config();
	    //声明redisso对象
	    private static Redisson redisson = null;
	    
	    static{
	        config.useSingleServer().setAddress("127.0.0.1:6379");
	             //得到redisson对象
	        redisson = (Redisson) Redisson.create(config);

	   }
	    
	    
	  //获取redisson对象的方法
	    public static Redisson getRedisson(){
	        return redisson;
	    }
	    
	    
	    private static final String LOCK_TITLE = "redisLock_";

	    
	    //加锁
	    public static boolean acquire(String lockName){
	       //声明key对象
	        String key = LOCK_TITLE + lockName;
	       //获取锁对象
	        RLock mylock = redisson.getLock(key);
	       //加锁，并且设置锁过期时间，防止死锁的产生
	        mylock.lock(2, TimeUnit.MINUTES); 
	        System.err.println("======lock======"+Thread.currentThread().getName());
	       //加锁成功
	        return  true;
	    }
	    
	    
	  //锁的释放
	    public static void release(String lockName){
	       //必须是和加锁时的同一个key
	        String key = LOCK_TITLE + lockName;
	       //获取所对象
	        RLock mylock = redisson.getLock(key);
	      //释放锁（解锁）
	        mylock.unlock();
	        System.err.println("======unlock======"+Thread.currentThread().getName());
	    }
	    
	    
	    
}

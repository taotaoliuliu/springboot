package com.aebiz.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.JedisPool;

public class RedisHelper {
	
	
	private static JedisPool pool=null;
	
	public static JedisPool getPool(){
		
		if(pool==null){
			

			GenericObjectPoolConfig poolConfig=new GenericObjectPoolConfig();
			
			poolConfig.setMaxTotal(300);
			
			poolConfig.setMaxIdle(10);;
			pool =new JedisPool(poolConfig, "127.0.0.1", 6379);
		}
		
		return pool;
	}
	
	
	
	
	public static void main(String[] args) {
		JedisPool pool2 = RedisHelper.getPool();
		
		List<String> keys =new ArrayList<>();
		
		keys.add(REDIS_STOCK_NUM_PRE+"skuNo0000000000001");
		keys.add(REDIS_SOLD_NUM_PRE+"skuNo0000000000001");
		keys.add(REDIS_LOCK_NUM_PRE+"skuNo0000000000001");
		
		List<String> values =new ArrayList<>();

		values.add("200");
		values.add("200");
		values.add("0");
		
		
		pool2.getResource().eval(getEditRedisLua(), keys, values);
		
		
		pool2.getResource().set(REDIS_PROMOTION_LOCK_NUM_PRE+promotionUuid+"_"+skuNo, "200");
		
		
		//订单锁库 
		
		List<String> keys2 =new ArrayList<>();
		
		keys2.add(REDIS_STOCK_NUM_PRE+skuNo);
		keys2.add(REDIS_SOLD_NUM_PRE+skuNo);
		keys2.add(REDIS_LOCK_NUM_PRE+skuNo);
		keys2.add(REDIS_ORDER_DETAIL_LOCK_NUM_PRE+orderId+"_"+skuNo);
		keys2.add("5");
		
		List<String> values2 =new ArrayList<>();
		
		
		
		
		values2.add(REDIS_PROMOTION_LOCK_NUM_PRE+promotionUuid+"_"+skuNo);
		
		
		
		values2.add( REDIS_ORDER_GIFT_LOCK_NUM_PRE+orderId+"_"+promotionUuid+"_"+skuNo);
       
        
		values2.add("3");

		
		
		pool2.getResource().eval(getOrderLockLua(), keys2, values2);
		
		
		System.out.println("ddddddd");
		
	}
	
	
	
    public static String getEditRedisLua(){
        String lua=" local flag=true " +
        		"redis.log(redis.LOG_DEBUG,KEYS[1])" +
                " for key,value in ipairs(KEYS) "+
                " do "+
                " redis.log(redis.LOG_DEBUG,value) "+
                " local num=ARGV[key] "+
                " redis.call('set',value,num) "+
                " end "+
                " return flag";
        return lua;
    }
    
    
    /**
     * 
redis.log(redis.LOG_DEBUG,key1)
redis.log(redis.LOG_DEBUG,key2)
redis.log(redis.LOG_DEBUG, ARGV[1],#ARGV[1])
     */
    
    
    
    
    
    
    
    
    
    /**
     * 获取普通订单锁库lua  脚本
     * @return
     * 
     * 
     * 1 库存数
	2 售卖数
	3 锁库数
	4 订单锁库key REDIS_ORDER_DETAIL_LOCK_NUM_PRE+orderDetailUuid+"_"+skuNo;
	5 页面锁库数量
	
	
	 // * 赠品锁库： 0--促销锁库key 1--赠品锁库key  2---锁库数量
	  * 
	  * [ARGV[key-2]] 因为是批量所以这个必须这样写
     */
    
    

    public static String getOrderLockLua() {
        String lua="local flag = true\n" +
                "local lockGiftArr = {}\n" +
                "local stockOrderArr = {}\n" +
                "local stockArr= {}\n" +
                "local stockNum = 0\n" +
                "local soldNum = 0\n" +
                "local lockNum = 0\n" +
                "local buyNum = 0\n" +
                "local promotionLockNum = 0\n" +
                "\n" +
                "for key,value in ipairs(KEYS) do\n" +
                "\tif key % 5 ~=0 and key % 5 ~=4  then \n" +
                "\t\tstockArr[value] = redis.call('get',value)\n" +
                "\tend\n" +
                "end\n" +
                "\n" +
                "for key,value in pairs(stockArr) do\n" +
                "\tif (type(value) == 'nil' or (not value)) then  flag=false  break end\n" +
                "end\n" +
                "\n" +
                "-- 校验锁库信息\n" +
                "if flag then\n" +
                "\tfor key,value in ipairs(KEYS) do\n" +
                "\t\tif key % 5 ==1 then stockNum = tonumber(stockArr[value]) end\n" +
                "\t\tif key % 5 ==2 then soldNum = tonumber(stockArr[value]) end\n" +
                "\t\tif key % 5 ==0 then  \n" +
                "\t\t\tbuyNum =tonumber(value)\n" +
                "\t\t\tif buyNum > stockNum or buyNum > soldNum then flag=false  break end\n" +
                "\t\tend\n" +
                "\tend\n" +
                "end\n" +
                "\n" +
                "if flag then\n" +
                "\tfor key,value in ipairs(KEYS) do\n" +
                "\t\tif key % 5 ==2 then soldNum = tonumber(stockArr[value]) end\n" +
                "\t\tif key % 5 ==3 then lockNum = tonumber(stockArr[value]) end\n" +
                "\t\tif key % 5 ==0 then  \n" +
                "\t\t\tbuyNum = tonumber(value)\n" +
                "\t\t\tstockOrderArr[KEYS[key-3]] = (soldNum-buyNum)\n" +
                "\t\t\tstockOrderArr[KEYS[key-2]] = (lockNum+buyNum)\n" +
                "\t\t\tstockOrderArr[KEYS[key-1]] = buyNum\n" +
                "\t\tend\n" +
                "\tend\n" +
                "\n" +
                "    -- 赠品锁库\n" +
                "\tfor key,value in ipairs(ARGV) do\n" +
                "\t\tif key % 3 ==1 then\n" +
                "\t\t\tlocal temp = redis.call('get',value)\n" +
                "\t\t\tif (type(temp) == 'nil' or (not temp)) then \n" +
                "\t\t\t\tlockGiftArr[value]=-1\n" +
                "\t\t\telse\n" +
                "\t\t\t\tlockGiftArr[value]=temp\n" +
                "\t\t\tend\n" +
                "\t\tend\n" +
                "\tend\n" +
                "\t-- 校验锁库信息\n" +
                "\tfor key,value in ipairs(ARGV)\n" +
                "\t\tdo\n" +
                "\t\tif key % 3 ==1 then promotionLockNum=tonumber(lockGiftArr[value])   end\n" +
                "\t\tif key % 3 ==0 then\n" +
                "\t\t\tif promotionLockNum > 0 then \n" +
                "\t\t\t\tbuyNum=tonumber(value)\n" +
                "\t\t\t\tif promotionLockNum > buyNum then \n" +
                "\t\t\t\t\tstockOrderArr[ARGV[key-2]]=(promotionLockNum-buyNum)\n" +
                "\t\t\t\t\tstockOrderArr[ARGV[key-1]]=buyNum\n" +
                "\t\t\t\telse\n" +
                "\t\t\t\t\tstockOrderArr[ARGV[key-2]]=0\n" +
                "\t\t\t\t\tstockOrderArr[ARGV[key-1]]=promotionLockNum\n" +
                "\t\t\t\tend\n" +
                "\t\t\tend\n" +
                "\t\tend\n" +
                "\tend\n" +
                "\t-- 保存订单锁库\n" +
                "\tfor key,value in pairs(stockOrderArr) do\n" +
                "\t\tredis.call('set',key,value)\n" +
                "\tend\n" +
                "end\n" +
                "\n" +
                "return flag\n" ;
        return lua;
    }
    
    
    
    
    
    

	/**
	 * 总库存redis前缀
	 * 存储格式 key：stock_skuNo_stockNum_skuNo  value：总库存数
	 */
	private static final String REDIS_STOCK_NUM_PRE = "stock_skuNo_stockNum_";
	/**
	 * 可售库存库存redis前缀 、
	 * 存储格式 key：stock_skuNo_soldNum_skuNo    value：可售库存数
	 */
	private static final String REDIS_SOLD_NUM_PRE = "stock_skuNo_soldNum_";
	/**
	 * 锁定库存redis前缀
	 * 存储格式  key：stock_skuNo_lockNum_skuNo  vlaue：锁定库存数
	 */
	private static final String REDIS_LOCK_NUM_PRE = "stock_skuNo_lockNum_";
	
	
	
	
	/**
	 * 订单明细锁库redis前缀
	 * 存储格式 key：lockStock_orderDetail_orderDetailUuid_skuNo  value：锁定数
	 */
	private static final String REDIS_ORDER_DETAIL_LOCK_NUM_PRE = "lockStock_orderDetail_";
	
	
	
	
	
	/**
	 * 这个是促销设置库存数量的时候有的
	 */
	private static final String REDIS_PROMOTION_LOCK_NUM_PRE = "lockStock_promotion_";
	
	
	/**
	 * 订单赠品锁库redis前缀
	 * 存储格式  key：lockStock_orderGift_orderUuid_promotionUUid_skuNo value：锁定数
	 */
	private static final String REDIS_ORDER_GIFT_LOCK_NUM_PRE = "lockStock_orderGift_";


	
	private static final String skuNo = "skuNo0000000000001";
	private static final String orderId = "orderId0000000001";
	private static final String promotionUuid = "promotion000000001";
}



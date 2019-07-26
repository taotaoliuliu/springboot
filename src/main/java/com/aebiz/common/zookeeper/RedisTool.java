package com.aebiz.common.zookeeper;

import java.util.Collections;

import redis.clients.jedis.Jedis;

/**
 * redis 分布式锁实现
 * 
 * @author 55401
 *
 */
public class RedisTool {

	private static final String LOCK_SUCCESS = "OK";
	private static final String SET_IF_NOT_EXIST = "NX";  //只在键不存在时，才对键进行设置操作
	private static final String SET_WITH_EXPIRE_TIME = "PX"; //设置键的过期时间为 millisecond 毫秒

	/**
	 * 尝试获取分布式锁
	 * 
	 * @param jedis
	 *            Redis客户端
	 * @param lockKey
	 *            锁
	 * @param requestId
	 *            请求标识
	 * @param expireTime
	 *            超期时间
	 * @return 是否获取成功
	 */
	
	/**
	 * set key value [ex 秒数] [px 毫秒数] [nx/xx]　　

　　　　　　如果ex和px同时写，则以后面的有效期为准

　　　　　　nx：如果key不存在则建立

　　　　　　xx：如果key存在则修改其值
	 * @param jedis
	 * @param lockKey
	 * @param requestId
	 * @param expireTime
	 * @return
	 */
	public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

		String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

		if (LOCK_SUCCESS.equals(result)) {
			return true;
		}
		return false;

	}

	private static final Long RELEASE_SUCCESS = 1L;

	/**
	 * 释放分布式锁
	 * 
	 * @param jedis
	 *            Redis客户端
	 * @param lockKey
	 *            锁
	 * @param requestId
	 *            请求标识
	 * @return 是否释放成功
	 */
	public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

		String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
		Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

		if (RELEASE_SUCCESS.equals(result)) {
			return true;
		}
		return false;

	}

}

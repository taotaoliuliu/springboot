package com.aebiz.common.seckill.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.aebiz.common.config.SystemConfig;
import com.aebiz.common.exception.BusinessException;
import com.aebiz.common.seckill.cache.UserBlackListCache;
import com.aebiz.common.seckill.model.UserRequestRecord;
import com.aebiz.common.utils.RedisUtil;

/**
 * 恶意用户检测拦截器
 */
@Component
public class UserInterceptor extends HandlerInterceptorAdapter
{
	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private UserBlackListCache userBlackListCache;

	@Autowired
	private SystemConfig systemConfig;

	private static final String USER_REQUEST_TIMES_PREFIX = "user_request_times_";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception
	{
		// 1.获取用户手机号
		String mobile = request.getParameter("mobile");

		// 匹配手机号是否是正常手机号
		// Matcher matcher = pattern.matcher(mobile);
		// // 2. 验证用户是否在黑名单中
		// if (!matcher.find() || userBlackListCache.isIn(mobile))
		// {
		// throw new BusinessException("抢购已经结束啦");
		// }

		// 2. 验证用户是否在黑名单中
		if (userBlackListCache.isIn(mobile))
		{
			throw new BusinessException("抢购已经结束啦");
		}

		// 查询该用户访问记录
		List<UserRequestRecord> requestRecords = redisUtil.lrange(USER_REQUEST_TIMES_PREFIX + mobile, 0,
				systemConfig.getUserBlackTimes() - 1, UserRequestRecord.class);

		// 超过限定时间内的访问频率
		if (requestRecords.size() + 1 >= systemConfig.getUserBlackTimes() && (System.currentTimeMillis()
				- requestRecords.get(requestRecords.size() - 1).getTimestamp() < systemConfig.getUserBlackSeconds() * 1000))
		{
			// 模拟加入IP黑名单，实际应用时这里要优化入库，下次重启服务时重新加载
			userBlackListCache.addInto(mobile);

			// 清空访问记录缓存
			redisUtil.delete(USER_REQUEST_TIMES_PREFIX + mobile);
			throw new BusinessException("抢购已经结束啦");
		}

		else
		{
			UserRequestRecord requestRecord = new UserRequestRecord();
			requestRecord.setMobile(mobile);
			requestRecord.setTimestamp(System.currentTimeMillis());
			// 如果第一次设定访问次数，则增加过期时间
			redisUtil.lpush(USER_REQUEST_TIMES_PREFIX + mobile, requestRecord);
		}
		return true;
	}


	
}

package com.aebiz.common.seckill;

/**
 * 常量
 * lsl
 */
public class CommonConstant
{
	public interface RedisKey
	{
		String MESSAGE_TYPE="MESSAGE_TYPE";
		
		String GOODS_STORE_BY_ID = "goods_store_by_goods_id_{0}";

		String GOODS_INFO_BY_ID = "goods_info_by_goods_id_{0}";

		// IP黑名单
		String IP_BLACK_LIST = "ip_black_list";

		// 用户黑名单
		String USER_BLACK_LIST = "user_black_list";

		// 秒杀处理列表
		String MIAOSHA_HANDLE_LIST = "miaosha_handle_list_goods_random_name:{0}";

		// redis库存
		String REDIS_GOODS_STORE = "redis_goods_store_goods_random_name:{0}";

		// redis占位成功下单token
		String MIAOSHA_SUCCESS_TOKEN = "miaosha_success_token_mobile:{0}_productname:{1}_";

		// redis占位成功下单token前缀
		String MIAOSHA_SUCCESS_TOKEN_PREFIX = "miaosha_success_token_mobile";

		// 秒杀结束标志
		String MIAOSHA_FINISH_FLAG = "miaosha_finish_flag_goods_random_name:{0}";

	}

	public interface RedisKeyExpireSeconds
	{
		int GOODS_STORE_BY_ID = 3 * 24 * 60 * 60;
	}

	/**
	 * 限流倍数
	 * 
	 * @category 限流倍数
	 * @author xiangyong.ding@weimob.com
	 * @since 2017年3月16日 下午2:08:54
	 */
	public interface CurrentLimitMultiple
	{
		// 商品购买限流倍数
		int GOODS_BUY = 1;
	}

	/**
	 * cookie id
	 */
	public static final String COOKIE_NAME = "MIAOSHA_ID";

	/**
	 * token有效期，单位：毫秒
	 */
	public static final long TOKEN_EFECTIVE_MILLISECONDS = 3 * 60 * 1000;

	public static final String MESSAGE_TYPE = "MESSAGE_TYPE";
}

package com.aebiz.common.pay.alipay.config;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alipay.api.DefaultAlipayClient;

import com.alipay.api.AlipayClient;

import scala.collection.mutable.HashMap;
/**
 * 配置公共参数
 * 创建者 科帮网
 * 创建时间	2017年7月27日
 */

@Component
public  class AliPayConfig {
	
	 /**
     * 私有的默认构造子，保证外界无法直接实例化
     */
    private AliPayConfig(){};
    
    
    
   
    public static String privateKey;
    
    
    public static String appid;
    
    
    public static String alipayPublicKey;
    
    
    public static String openApiDomain;
    
    
    /**
     * 签名方式
     */
 	public static String SIGN_TYPE = "RSA2";
	 /**
     * 参数类型
     */
    public static String PARAM_TYPE = "json";
    /**
     * 编码
     */
    public static String CHARSET = "utf-8";
    /**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例
     * 没有绑定关系，而且只有被调用到才会装载，从而实现了延迟加载
     */
    private static class SingletonHolder{
    	
    	
        /**
         * 静态初始化器，由JVM来保证线程安全
         */
		private  static AlipayClient alipayClient = new DefaultAlipayClient(openApiDomain, appid,
													privateKey, PARAM_TYPE, CHARSET,
													alipayPublicKey,"RSA2");
		
		//private  static AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }
    /**
     * 支付宝APP请求客户端实例
     * @Author  科帮网
     * @return  AlipayClient
     * @Date	2017年7月27日
     * 更新日志
     * 2017年7月27日  科帮网 首次创建
     *
     */
    public static AlipayClient getAlipayClient(){
        return SingletonHolder.alipayClient;
    }
    /**
     * 电脑端预下单
     * @Author  科帮网
     * @return  AlipayTradeService
     * @Date	2017年7月27日
     * 更新日志
     * 2017年7月27日  科帮网 首次创建
     *
     */
  //  public static AlipayTradeService getAlipayTradeService(){
      //  return SingletonHolder.tradeService;
   // }
	public static String getPrivateKey() {
		return privateKey;
	}
	
	 @Value("${alipay.privateKey}")
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public static String getAppid() {
		return appid;
	}
	
	@Value("${alipay.appid}")
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public static String getAlipayPublicKey() {
		return alipayPublicKey;
	}
	
	
	
	@Value("${alipay.alipayPublicKey}")
	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}
	public static String getOpenApiDomain() {
		return openApiDomain;
	}
	
	@Value("${alipay.openApiDomain}")
	public void setOpenApiDomain(String openApiDomain) {
		this.openApiDomain = openApiDomain;
	}
    
    
    
    
}

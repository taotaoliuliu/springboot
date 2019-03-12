package com.aebiz.common.pay.weixin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.aebiz.common.utils.HttpClientUtils;
import com.alibaba.fastjson.JSONObject;

@Controller
public class PayController {

	
	@RequestMapping("/auth")
	public void auth(@RequestParam("code") String code,String orderId){
		
		//https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx705762491229630b&redirect_uri=http%3A%2F%2Fhyribm.natappfree.cc%2Fauth%3Forderid%3D2&response_type=code&scope=snsapi_base#wechat_redirect
		
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx705762491229630b&secret=f984414914131383e75cd5741371618f&code=" + code + "&grant_type=authorization_code";
        
        
        String response = HttpClientUtils.get4Body(url);
        
        JSONObject object = JSONObject.parseObject(response);
        String openid = object.getString("openid");
        System.out.println(openid);

	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String encode = URLEncoder.encode("http://hyribm.natappfree.cc/auth?orderid=2", "utf-8");
		
		System.out.println(encode);
	}
}

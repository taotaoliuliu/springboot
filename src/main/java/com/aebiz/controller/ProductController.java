package com.aebiz.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aebiz.entity.Ad;
import com.aebiz.service.AdService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import redis.clients.jedis.JedisPool;

/**
 * @date 2016/8/9
 */
@Controller
@RequestMapping("/info")
public class ProductController {

	private Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private JedisPool jedisPool;

	@ResponseBody
	@RequestMapping(value = "")
	public String getAd(HttpServletRequest request)  {
		String type = request.getParameter("type");
		Map<String, Object> content = null;

		try {
			if ("basic".equals(type)) {
				content = getBasicInfo(request.getParameter("skuId"));
			} else if ("desc".equals(type)) {
				content = getDescInfo(request.getParameter("skuId"));

			} else if ("other".equals(type)) {
				content = getOtherInfo(request.getParameter("ps3Id"), request.getParameter("brandId"));

			}
		} catch (Exception e) {
			//content="{\"ret\":\"fail\"}";
				
			e.printStackTrace();
		}

		return JSON.toJSONString(content);
	}

	private Map<String, Object> getBasicInfo(String skuId) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		// 商品编号
		map.put("skuId", skuId);
		// 名称
		map.put("name", "苹果（Apple）iPhone 6 (A1586) 16GB 金色 移动联通电信4G手机");
		// 一级二级三级分类
		map.put("ps1Id", 9987);
		map.put("ps2Id", 653);
		map.put("ps3Id", 655);
		// 品牌ID
		map.put("brandId", 14026);
		// 图片列表
		map.put("imgs", getImgs(skuId));
		// 上架时间
		map.put("date", "2014-10-09 22:29:09");
		// 商品毛重
		map.put("weight", "400");
		// 颜色尺码
		map.put("colorSize", getColorSize(skuId));
		// 扩展属性
		map.put("expands", getExpands(skuId));
		// 规格参数
		map.put("propCodes", getPropCodes(skuId));
		map.put("date", System.currentTimeMillis());
		//String content = objectMapper.writeValueAsString(map);
		// 实际应用应该是发送MQ
		//asyncSetToRedis(basicInfoJedisPool, "p:" + skuId + ":", content);
		return map;
	}

	
	
	private List<String> getImgs(String skuId) {  
	    return Lists.newArrayList(  
	            "http://img12.360buyimg.com/n1/s180x180_jfs/t10315/227/1754541026/256693/980afae7/59e5bdf4Nb6b9904a.jpg",  
	            "http://img10.360buyimg.com/n1/s180x180_jfs/t8929/149/2405378839/254338/66dd143d/59cda962N323b5446.jpg",  
	            "http://img10.360buyimg.com/n1/s180x180_jfs/t14914/228/2523477059/220222/bb14ff04/5aa63873N519436a1.jpg"
	            );  
	} 
	
	private List<Map<String, Object>> getColorSize(String skuId) {  
	    return Lists.newArrayList(  
	        makeColorSize(1217499, "金色", "公开版（16GB ROM）"),  
	        makeColorSize(1217500, "深空灰", "公开版（16GB ROM）"),  
	        makeColorSize(1217501, "银色", "公开版（16GB ROM）"),  
	        makeColorSize(1217508, "金色", "公开版（64GB ROM）"),  
	        makeColorSize(1217509, "深空灰", "公开版（64GB ROM）"),  
	        makeColorSize(1217509, "银色", "公开版（64GB ROM）"),  
	        makeColorSize(1217493, "金色", "移动4G版 （16GB）"),  
	        makeColorSize(1217494, "深空灰", "移动4G版 （16GB）"),  
	        makeColorSize(1217495, "银色", "移动4G版 （16GB）"),  
	        makeColorSize(1217503, "金色", "移动4G版 （64GB）"),  
	        makeColorSize(1217503, "金色", "移动4G版 （64GB）"),  
	        makeColorSize(1217504, "深空灰", "移动4G版 （64GB）"),  
	        makeColorSize(1217505, "银色", "移动4G版 （64GB）")  
	    );  
	}  
	private Map<String, Object> makeColorSize(long skuId, String color, String size) {  
	    Map<String, Object> cs1 = Maps.newHashMap();  
	    cs1.put("SkuId", skuId);  
	    cs1.put("Color", color);  
	    cs1.put("Size", size);  
	    return cs1;  
	} 
	
	
	private List<List<?>> getExpands(String skuId) {  
	    return Lists.newArrayList(  
	            (List<?>)Lists.newArrayList("热点", Lists.newArrayList("超薄7mm以下", "支持NFC")),  
	            (List<?>)Lists.newArrayList("系统", "苹果（IOS）"),  
	            (List<?>)Lists.newArrayList("系统", "苹果（IOS）"),  
	            (List<?>)Lists.newArrayList("购买方式", "非合约机")  
	    );  
	}  
	
	
	private Map<String, List<List<String>>> getPropCodes(String skuId) {  
	    Map<String, List<List<String>>> map = Maps.newHashMap();  
	    map.put("主体", Lists.<List<String>>newArrayList(  
	            Lists.<String>newArrayList("品牌", "苹果（Apple）"),  
	            Lists.<String>newArrayList("型号", "iPhone 6 A1586"),  
	            Lists.<String>newArrayList("颜色", "金色"),  
	            Lists.<String>newArrayList("上市年份", "2014年")  
	    ));  
	    map.put("存储", Lists.<List<String>>newArrayList(  
	            Lists.<String>newArrayList("机身内存", "16GB ROM"),  
	            Lists.<String>newArrayList("储存卡类型", "不支持")  
	    ));  
	    map.put("显示", Lists.<List<String>>newArrayList(  
	            Lists.<String>newArrayList("屏幕尺寸", "4.7英寸"),  
	            Lists.<String>newArrayList("触摸屏", "Retina HD"),  
	            Lists.<String>newArrayList("分辨率", "1334 x 750")  
	    ));  
	    return map;  
	}  
	
	
	private Map<String, Object> getDescInfo(String skuId) throws Exception {  
	    Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("content", "<div><img data-lazyload='http://img30.360buyimg.com/jgsq-productsoa/jfs/t448/127/574781110/103911/b3c80634/5472ba22N45400f4e.jpg' alt='' /><img data-lazyload='http://img30.360buyimg.com/jgsq-productsoa/jfs/t802/133/19465528/162152/e463e43/54e2b34aN11bceb70.jpg' alt='' height='386' width='750' /></div>");  
	    map.put("date", System.currentTimeMillis());  
	    //String content = objectMapper.writeValueAsString(map);  
	    //实际应用应该是发送MQ  
	    //asyncSetToRedis(descInfoJedisPool, "d:" + skuId + ":", content);  
	    return map;
	}  
	
	
	private Map<String, Object> getOtherInfo(String ps3Id, String brandId) throws Exception {  
	    Map<String, Object> map = new HashMap<String, Object>();  
	    //面包屑  
	    List<List<?>> breadcrumb = Lists.newArrayList();  
	    breadcrumb.add(Lists.newArrayList(9987, "手机"));  
	    breadcrumb.add(Lists.newArrayList(653, "手机通讯"));  
	    breadcrumb.add(Lists.newArrayList(655, "手机"));  
	    //品牌  
	    Map<String, Object> brand = Maps.newHashMap();  
	    brand.put("name", "苹果（Apple）");  
	    brand.put("logo", "BrandLogo/g14/M09/09/10/rBEhVlK6vdkIAAAAAAAFLXzp-lIAAHWawP_QjwAAAVF472.png");  
	    map.put("breadcrumb", breadcrumb);  //list<list<>
	    map.put("brand", brand);  
	    //实际应用应该是发送MQ  
	   // asyncSetToRedis(otherInfoJedisPool, "s:" + ps3Id + ":", objectMapper.writeValueAsString(breadcrumb));  
	    //asyncSetToRedis(otherInfoJedisPool, "b:" + brandId + ":", objectMapper.writeValueAsString(brand));  
	    return map;
	}  
	
}

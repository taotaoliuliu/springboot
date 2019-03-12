package com.aebiz.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.aebiz.common.base.cotroller.BaseController;
import com.aebiz.common.im.constant.Constants;
import com.aebiz.common.im.user.model.ImFriendUserData;
import com.aebiz.common.im.user.model.ImFriendUserInfoData;
import com.aebiz.common.im.user.model.ImGroupUserData;
import com.aebiz.common.im.user.model.ImUserData;
import com.aebiz.common.im.user.model.UserAccountEntity;
import com.aebiz.common.im.user.model.UserInfoEntity;
import com.aebiz.common.im.user.service.UserAccountService;
import com.aebiz.common.im.user.service.UserDepartmentService;
import com.aebiz.common.im.user.service.UserMessageService;
import com.aebiz.entity.Menu;
import com.alibaba.fastjson.JSONArray;

/**
 * @date 2016/8/9
 */
@Controller
@RequestMapping("/im") 
public class ImController extends BaseController<Menu> {
	
	

	// \b 是单词边界(连着的两个(字母字符 与 非字母字符) 之间的逻辑上的间隔),    
    // 字符串在编译时会被转码一次,所以是 "\\b"    
    // \B 是单词内部逻辑间隔(连着的两个字母字符之间的逻辑上的间隔)    
    static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"    
            +"|windows (phone|ce)|blackberry"    
            +"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"    
            +"|laystation portable)|nokia|fennec|htc[-_]"    
            +"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";    
    static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"    
            +"|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";    
      
    //移动设备正则匹配：手机端、平板  
    static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);    
    static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);  

	@Autowired  
	public  HttpServletRequest request;
	
	@Autowired
	private UserDepartmentService userDepartmentServiceImpl;
	
	@Autowired
	private UserAccountService userAccountServiceImpl;
	
	@Autowired
	private UserMessageService userMessageServiceImpl;
	
	
	
	@RequestMapping("/toLogin")
	public String toLogin(Model model,String uuid){
		
		return "login";
	}
    
 
	
	@RequestMapping("/toMain")
	public String toMain(Model model,String uuid){
		
		model.addAttribute("uuid", uuid);
		return "im/layim";
	}
    

	/**
	 * 登录IM
	 */
	@RequestMapping("/login")
	public String login(@RequestParam Map<String, Object> params,HttpServletRequest request){
	//	Query query = new Query(params);
		UserAccountEntity userAccount = userAccountServiceImpl.validateUser(params);
		request.setAttribute("uuid", userAccount.getId());
		
		if(userAccount!=null){
			setLoginUser(userAccount);
			String template = check(request);
			if(template.equals(Constants.ViewTemplateConfig.mobiletemplate)){
				return "layimmobile";
			}
			return "im/layim";
		}
		return "redirect:login.jsp";
	}
	
	
	  /** 
     * 检测是否是移动设备访问 
     *  
     * @Title: check  
     * @param userAgent 浏览器标识 
     * @return true:移动设备接入，false:pc端接入 
     */  
    public static String check(HttpServletRequest request){    
    	 HttpSession session= request.getSession();  
    	 String temp =Constants.ViewTemplateConfig.template;//默认模板
        try{   
        	String sername = request.getServerName();
        	if(sername.indexOf("m.")>-1){
            	temp =Constants.ViewTemplateConfig.mobiletemplate;
		    }else{
		    	 //获取ua，用来判断是否为移动端访问  
                String userAgent = request.getHeader( "USER-AGENT" ).toLowerCase();    
                if(null == userAgent){    
                    userAgent = "";    
                }  
                // 匹配    
                Matcher matcherPhone = phonePat.matcher(userAgent);    
                Matcher matcherTable = tablePat.matcher(userAgent); 
                //判断是否为移动端访问  
                if(matcherPhone.find() || matcherTable.find()){    
                	temp =Constants.ViewTemplateConfig.mobiletemplate;
                }   
		    }  
            session.setAttribute("template",temp);  
        }catch(Exception e){}  
    	 return temp;
    }  
 

    /**
	 * 保存登录用户
	 * */
	protected void setLoginUser(UserAccountEntity u) {
		request.getSession().setAttribute("user",u);
	}
	
	
	/** 
	 *  取得所有聊天用户
	 * @param response
	 * @param request
	 * @param redirectAttributes
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getusers")
	@ResponseBody
	public Object getAllUser(HttpServletResponse response,HttpServletRequest request) throws Exception{
		    // 数据格式请参考文档  http://www.layui.com/doc/modules/layim.html  
			if(getLoginUser()!=null){
				
				UserInfoEntity  user = getLoginUser().getUserInfo();	
				ImFriendUserInfoData  my = new ImFriendUserInfoData();
				my.setId(user.getUid());
				my.setAvatar(user.getProfilephoto());
				my.setSign(user.getSignature());
				my.setUsername(user.getName());
				my.setStatus("online");
				
				//模拟群信息
				ImGroupUserData  group = new ImGroupUserData();
				group.setAvatar("http://tva2.sinaimg.cn/crop.0.0.199.199.180/005Zseqhjw1eplix1brxxj305k05kjrf.jpg");
				group.setId(1L);
				group.setGroupname("公司群");
				List<ImGroupUserData>  groups = new ArrayList<ImGroupUserData>();
				groups.add(group);
				
				Map map = new HashMap();
				ImUserData  us = new ImUserData();
				us.setCode("0");
				us.setMsg("");
				map.put("mine", my);
				map.put("group",groups);
				//获取用户分组 及用户
				List<ImFriendUserData> friends = userDepartmentServiceImpl.queryGroupAndUser();
				map.put("friend",friends);
				us.setData(map);
				return JSONArray.toJSON(us);
			}else{
				return JSONArray.toJSON("");
			}
	}
	
	
	/**
     * 取得登录用户
     * @return
     */
     public UserAccountEntity getLoginUser(){
    	 UserAccountEntity user=null;
    	 user = (UserAccountEntity)request.getSession().getAttribute("user");
    	 return user;
     } 
	
	
}

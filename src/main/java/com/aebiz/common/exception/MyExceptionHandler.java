package com.aebiz.common.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.aebiz.common.bean.Result;
import com.aebiz.common.utils.ResponseUtil;
import com.alibaba.fastjson.JSON;

/**
 * 统一异常处理，有效地针对异步和非异步请求 不同异常会到不同页面 throw new ParameterException("XXXX") －－－－ >
 * error-parameter.jsp throw new SystemException("XXXX") －－－－ > error-System.jsp
 * throw new Exception("XXXX") －－－－ > error.jsp Status value 1001 业务异常返回 1001
 * 1002 参数异常返回 1000 其他异常返回
 * 
 * @author lsl
 * @date 2014-12-10
 * @Email：mmm333zzz520@163.com
 */
public class MyExceptionHandler implements HandlerExceptionResolver {

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		System.out.println("aaa");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);

		Result returnObj = new Result();

		// 是否异步请求
		if (!(request.getHeader("accept").indexOf("application/json") > -1
				|| (request.getHeader("X-Requested-With") != null
						&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			// 根据不同错误转向不同页面
			if (ex instanceof BusinessException) {
				response.setStatus(1001);// 业务异常返回 1001
				return new ModelAndView("exception/error-system", model);
			} /*
				 * else if(ex instanceof ParameterException) {
				 * response.setStatus(1002);//参数异常返回 1002 return new
				 * ModelAndView("exception/error-parameter", model); }
				 * 
				 * else if(ex instanceof AuthorizationException) {
				 * response.setStatus(1002);//参数异常返回 1002 return new
				 * ModelAndView("exception/timeout", model); }
				 */
			else {
				response.setStatus(1000);// 其他异常返回 1000
				
				returnObj.setData("");
				//returnObj.setMessage(ex.getMessage());
				ResponseUtil.write(response, new String[]{JSON.toJSONString(returnObj)});
				
				 PrintWriter writer;
				try {
					writer = response.getWriter();
					 writer.write(ex.getMessage());
					 writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return new ModelAndView("/error");

			}
			
			
		} else {
			try {

				String resultCode = "";
				String appendMessage = "";
				if (ex instanceof BusinessException) {
				//	response.setStatus(1001);// 业务异常返回 1001
					
					BusinessException exceptionx = (BusinessException) ex;
					resultCode = exceptionx.getErrorCode();
					appendMessage = exceptionx.getErrorInfo();

				} /*
					 * else if(ex instanceof ParameterException) {
					 * response.setStatus(1002);//参数异常返回 1002 }
					 */
				else {
					response.setStatus(1000);// 其他异常返回 1000
					appendMessage=ex.getMessage();
				}

				returnObj.setData("");
				//returnObj.setMessage(appendMessage);
				
				

				ResponseUtil.write(response, new String[]{JSON.toJSONString(returnObj)});
			} catch (Exception e) {
				model.put("ex", e);
				return new ModelAndView("exception/error", model);
			}
			return null;
		}
	}
}

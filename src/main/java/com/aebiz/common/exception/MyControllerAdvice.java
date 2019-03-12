package com.aebiz.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aebiz.common.bean.Result;

@ControllerAdvice
public class MyControllerAdvice  {

	
	
	 
    @ExceptionHandler(Exception.class) 
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req,Exception e){ 
    	e.printStackTrace();
		Result result =new Result();
		if(e instanceof BusinessException) {
			BusinessException businessException = (BusinessException)e;
			//result.setMessage(businessException.getErrorInfo());
			
		}
		else if (e instanceof MalciousException){
			
		}
		else{
			result.setRetMessage(e.getMessage());
			result.setCode(Result.UNKNOWN_ERRORCODE);
		}		
        return result; 
    }
	
	
	
}

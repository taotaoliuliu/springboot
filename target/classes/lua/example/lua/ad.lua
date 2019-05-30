local redis = require "resty.redis"
local template = require("resty.template")
local dkjson = require("dkjson") 
local ngx_log = ngx.log
local ngx_ERR = ngx.ERR  

local ngx_var = ngx.var 





local function read_http()  
  

	local http = require "resty.http"
                local httpc = http.new()

 local resp = httpc:request_uri(
                    "http://192.168.137.1:8080/ad/getAd",
                        {
                        method = "GET",

                      }
                )


  
    if not resp then  
        ngx_log(ngx_ERR, "request error :", err)  
        return  
    end  
  
    if resp.status ~= 200 then  
        ngx_log(ngx_ERR, "request error, status :", resp.status)  
        return  
    end  
  
    return resp.body  
end  


--从redis获取  
local content = read_http() 


--如果redis没有，回源到tomcat  
if not content then  
   ngx_log(ngx_ERR, "redis not found content, back to http, id : ", "xxxxxx")
end  

 local data=dkjson.decode(content)
    ngx_log(ngx_ERR, "data:", content)
 

  template.render("ad.html", data)  





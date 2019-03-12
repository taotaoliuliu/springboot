local redis = require "resty.redis"
local template = require("resty.template")
local dkjson = require("dkjson")  
local ngx_log = ngx.log
local ngx_ERR = ngx.ERR  
ngx.say("xxxxxssss")

local ngx_var = ngx.var 
--获取id  
local id = ngx_var.id  

ngx.say(id)


local function close_redis(red)  
    if not red then  
        return  
    end  
    --释放连接(连接池实现)  
    local pool_max_idle_time = 10000 --毫秒  
    local pool_size = 100 --连接池大小  
    local ok, err = red:set_keepalive(pool_max_idle_time, pool_size)  
  
    if not ok then  
        ngx_log(ngx_ERR, "set redis keepalive error : ", err)  
    end  
end  

local function read_redis(id)  
    local red = redis:new()  
    red:set_timeout(1000)  
    local ip = "192.168.137.87"  
    local port =6379  
    local ok, err = red:connect(ip, port)  
    if not ok then  
        ngx_log(ngx_ERR, "connect to redis error : ", err)  
        return close_redis(red)  
    end  
  
    local resp, err = red:get(id)  
    if not resp then  
        ngx_log(ngx_ERR, "get redis content error : ", err)  
        return close_redis(red)  
    end  
        --得到的数据为空处理  
    if resp == ngx.null then  
        resp = nil  
    end  
    close_redis(red)  
  
    return resp  
end  

local function read_http(id)  
  

	local http = require "resty.http"
                local httpc = http.new()

 local resp = httpc:request_uri(
                    "http://192.168.137.1:8080/ad/"..id,
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
local content = read_redis(id) 


--如果redis没有，回源到tomcat  
if not content then  
   ngx_log(ngx_ERR, "redis not found content, back to http, id : ", id)
   content = read_http(id)
end  

 -- ngx_log(ngx_ERR, "content "+content, id)

 local data=dkjson.decode(content);
 
 ngx.say(type(data))
 

template.render("ad2.html", data)  





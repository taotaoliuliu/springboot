local common = require("item.common")  
local item = require("item.initutils")  
local read_redis = common.read_redis  
local read_http = common.read_http  
local dkjson = require("dkjson")  

local cjson_decode = dkjson.decode  
local ngx_log = ngx.log  
local ngx_ERR = ngx.ERR  
local ngx_exit = ngx.exit  
local ngx_print = ngx.print  
local ngx_var = ngx.var  
  
  
local skuId = ngx_var.skuId  
  
--ngx.say(skuId);

 local  basicInfoStr = read_http({type="basic", skuId = skuId})  
 
 
if not basicInfoStr then  
   ngx_log(ngx_ERR, "http not found basic info, skuId : ", skuId)  
   return ngx_exit(404)  
end 



local basicInfo = cjson_decode(basicInfoStr) 

local ps3Id = basicInfo["ps3Id"]  
local brandId = basicInfo["brandId"]   


local otherInfoStr = read_http({type="other", ps3Id = ps3Id, brandId = brandId})
ngx.say(otherInfoStr);
  
   if not otherInfoStr then  
       ngx_log(ngx_ERR, "http not found other info, skuId : ", skuId)  
   else  
     local otherInfo = cjson_decode(otherInfoStr)  
     basicInfo["breadcrumb"] = otherInfo["breadcrumb"]  
     basicInfo["brand"] = otherInfo["brand"]  
   end  


--初始化扩展属性  
item.init_expand(basicInfo)  
--初始化各分类的url  
item.init_breadcrumb(basicInfo) 
--初始化颜色尺码  
item.init_color_size(basicInfo)  
local template = require "resty.template"  
template.caching(true)  
template.render("item.html", basicInfo)  






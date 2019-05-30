local dkjson = require("dkjson")  
  
--lua对象到字符串  
local obj = {  
    id = 1,  
    name = "zhangsan",  
    age = nil,  
    is_male = false,  
    hobby = {"film", "music", "read"}  
}  
  
local str = dkjson.encode(obj, {indent = true})  
print(str);
  
--字符串到lua对象  
str = '{"hobby":["film","music","read"],"is_male":false,"name":"zhangsan","id":1,"age":null}'  

local obj, pos, err = dkjson.decode(str, 1, nil)  
print(obj)
print(obj.age)
print(obj.hobby)
print(obj.hobby[1])

for k, v in pairs(obj.hobby) do
    print(k, v)
end

for k,v in pairs(obj.hobby) do
 print(k,v)
end
  
  
  local str22='{"data2":[{"skuUuid":"1","uuid":"1","name":"我是广告1","content":"我是广告1xxx"},{"skuUuid":"2","uuid":"2","name":"我是广告2","content":"我是广告2yyy"},{"skuUuid":"222","uuid":"2a466dc9-929a-4c34-a90c-e0bf5ee9ea63","name":"我们不一样","content":"我们不一样我们不一样"}],"size":3}'
  
  
  local obj2= dkjson.decode(str22)  --decode 将 json字符串转为 lua对象
  
  print(obj2.size)
  print(obj2.data2)
  
  for k, v in pairs(obj2.data2) do
  
    print(type(v));
    print(v["content"]);
    print(#v);
    for k1, v1 in pairs(v) do
    print(v1)
    end
    
    for i=1,#v do 
      local vv=v[i] ;
      if i > 1 then  end
        
       print(vv)
    
    
    
      end
  end
  
  
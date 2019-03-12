ngx.say("hello world222222222!!!!!!!!!!");

local args = ngx.req.get_uri_args()

--ngx.say(args);

local body=ngx.encode_args(args);

local aa={type='11', skuId = '22'};
ngx.say(body);
local aaa=ngx.encode_args(aa);

ngx.say(aaa);


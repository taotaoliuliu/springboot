package com.aebiz.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity; 
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.ByteArrayEntity; 
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

public class HttpClientUtils {

	private final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class.getSimpleName());

	
	
    // 连接超时
    private static final int connection_timeout = 1000 * 3;
    // 指定时间内服务器端没有反应
    private static final int socket_timeout = 1000 * 3;
    // 请求超时
    private static final int request_timeout = 1000 * 3;
    // 每个主机
    private static int max_host_connections = 2000;
    // 总的连接数
    private static int max_total_connections = 5000;

    private static final String charset = "utf-8";
    // 自定义头前缀
    private static final String filter_header_prefix = "httpclient_";
    // 请求格式,默认json格式
    private static final String request_format = "httpclient_format";
    // 请求格式,默认json格式
    private static final String content_type = "Content-Type";

    private static HttpClient httpClient = null;
    private static Set<String> excludeHeaders = new HashSet<String>();
    private static RequestConfig default_request_config = null;
    static {
        default_request_config = RequestConfig.custom().setSocketTimeout(socket_timeout).setConnectTimeout(
                connection_timeout).setConnectionRequestTimeout(request_timeout).build();

        PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager();
        connMgr.setMaxTotal(max_total_connections);
        connMgr.setDefaultMaxPerRoute(max_host_connections);

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        httpClientBuilder.setConnectionManager(connMgr);
        httpClientBuilder.setDefaultRequestConfig(default_request_config);

        ConnectionConfig connConfig = ConnectionConfig.custom().setCharset(Charset.forName(charset)).build();

        httpClientBuilder.setDefaultConnectionConfig(connConfig);

        httpClient = httpClientBuilder.build();
        //过滤头
        excludeHeaders.add(HTTP.CONTENT_LEN.toLowerCase());
        excludeHeaders.add(HTTP.TRANSFER_ENCODING.toLowerCase());
    }

    /**
     * 请求Url并返回响应body,出现异常则返回null
     *
     * @param url
     */
    public static String get4Body(String url) {
        return get4Body(url, null);
    }

    /**
     * 请求Url并返回响应body
     *
     * @param url
     */
    public static String get4Body(String url, Map<String, String> headerMap) {
        HttpGet httpGet = new HttpGet(url);
        HttpResponseBody response = invokeRequest(httpGet, headerMap, null);
        return response.getBody();
    }

    /**
     * 请求Url并返回响应body,出现异常则返回null
     *
     * @param url
     */
    public static InputStream get4InputStreamBody(String url) {
        return get4InputStreamBody(url, null);
    }

    /**
     * 请求Url并返回响应body
     *
     * @param url
     */
    public static InputStream get4InputStreamBody(String url, Map<String, String> headerMap) {
        HttpGet httpGet = new HttpGet(url);
        HttpResponseBody response = invokeRequest(httpGet, headerMap, null);
        return response.getInputStream();
    }

    /**
     *
     * @param url
     *            请求url
     * @param headerMap
     *            header头信息
     * @param body
     *            请求消息体，在执行请求时，将body转换为json格式, <br/>
     *            如果body是String，则不进行转换 <br/>
     *            如果body是byte[]，则不进行转换
     * @return 响应消息体
     */
    public static String postJson4Body(String url, Map<String, String> headerMap, Object body) {
        return postJson(url, headerMap, body).getBody();
    }

    /**
     *
     * @param url
     *            请求url 请求消息体，在执行请求时，将body转换为json格式, <br/>
     *            如果body是String，则不进行转换 <br/>
     *            如果body是byte[]，则不进行转换
     * @return 响应消息体
     */
    public static String postJson4Body(String url, Object body) {
        return postJson4Body(url, null, body);
    }

    /**
     *
     * @param url
     *            请求url 请求消息体，在执行请求时，将body转换为json格式, <br/>
     *            如果body是String，则不进行转换 <br/>
     *            如果body是byte[]，则不进行转换
     * @return 响应消息对象
     */
    public static HttpResponseBody postJson(String url, Map<String, String> headerMap, Object body) {
        HttpPost httpPost = new HttpPost(url);
        HttpResponseBody response = invokeRequest(httpPost, headerMap, body);
        return response;
    }

    /**
     *
     * @param url
     *            按照http post协议格式请求
     *
     * @return 响应消息对象
     */
    public static HttpResponseBody post(String url, Map<String, Object> params) {
    	HttpResponseBody response = post(url, null, params);
        return response;
    }

    /**
     *
     * @param url
     *            按照http post协议格式请求
     *
     * @return 响应消息对象
     */
    public static String post4Body(String url, Map<String, Object> params) {
    	HttpResponseBody response = post(url, params);
        return response.getBody();
    }

    /**
     *
     * @param url
     *            按照http post协议格式请求
     *
     * @return 响应消息对象
     */
    public static HttpResponseBody post(String url, Map<String, String> headerMap, Map<String, Object> params) {
        HttpPost httpPost = new HttpPost(url);
        if (headerMap == null) {
            headerMap = new HashMap<String, String>();
        }
        headerMap.put(request_format, "post");
        headerMap.put(content_type, "application/json");
        HttpResponseBody response = invokeRequest(httpPost, headerMap, params);
        return response;
    }

    public static HttpResponseBody postByteArray(String url, Map<String, String> headerMap, byte[] body) {
        HttpPost httpGet = new HttpPost(url);
        return invokeRequest(httpGet, headerMap, body);
    }

    private static HttpResponseBody invokeRequest(HttpRequestBase httpRequset, Map<String, String> headerMap,
                                                    Object body) {
        HttpClient httpClient = getConcurrentHttpClient();
        addHeaders(httpRequset, headerMap);
        HttpResponseBody hcBuilder = HttpResponseBody.custom();
        HttpResponse httpResponse = null;
        InputStream in = null;
        try {
            if (body != null && httpRequset instanceof HttpRequestBase) {
                setRequsetBody(httpRequset, headerMap, body);
            }
            httpResponse = httpClient.execute(httpRequset);
            hcBuilder.setHttpResponse(httpResponse);
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                in = entity.getContent();
                ByteArrayInputStream bin = new ByteArrayInputStream(IOUtils.toByteArray(in));
                hcBuilder.setInputStream(bin);
            }
        } catch (IOException e) {
            logger.error("exception for request url :" + httpRequset.getURI(), e);
            hcBuilder.setEx(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                    logger.error("exception for request url with execute close:" + httpRequset.getURI(), e);
                    hcBuilder.setEx(e);
                }
            }
        }
        return hcBuilder;
    }

    @SuppressWarnings("unchecked")
    private static void setRequsetBody(HttpRequestBase httpRequset, Map<String, String> headerMap, Object body) {
        HttpPost httpPost = (HttpPost) httpRequset;
        if (headerMap != null && headerMap.get(request_format) != null && headerMap.get(request_format).equals("post")
                && body instanceof Map) {
            // post请求格式
            List<NameValuePair> formParams = new ArrayList<NameValuePair>();
            for (Entry<String, Object> entry : ((Map<String, Object>) body).entrySet()) {
                formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            httpPost.setEntity(new UrlEncodedFormEntity(formParams, Consts.UTF_8));
        } else {
            // 默认形式，json请求格式
            byte[] bodyByte = null;
            if (body instanceof String) {
                bodyByte = body.toString().getBytes(Consts.UTF_8);
            } else if (body instanceof byte[]) {
                bodyByte = (byte[]) body;
            } else {
                bodyByte = JSON.toJSONString(body).getBytes(Consts.UTF_8);
            }
            httpPost.setEntity(new ByteArrayEntity(bodyByte));
        }
    }

    private static void addHeaders(HttpRequestBase httpRequset, Map<String, String> headerMap) {
        if (headerMap != null && headerMap.size() != 0) {
            for (Entry<String, String> header : headerMap.entrySet()) {
                if (header.getKey().startsWith(filter_header_prefix)
                        || excludeHeaders.contains(header.getKey().toLowerCase())) {
                    continue;
                }
                httpRequset.setHeader(header.getKey(), header.getValue());
            }
        }
    }

    public static HttpClient getConcurrentHttpClient() {
        return httpClient;
    }
    public static void main(String[] args) {
        String body = get4Body("http://www.baidu.com");
        System.out.println(body);
    }
    
    static class HttpResponseBody {
    	
    	private  HttpResponse httpResponse;
    	
    	private  Exception ex;
    	
    	private  ByteArrayInputStream inputStream;

		public HttpResponse getHttpResponse() {
			return httpResponse;
		}
		public void setHttpResponse(HttpResponse httpResponse) {
			this.httpResponse = httpResponse;
		}
		public Exception getEx() {
			return ex;
		}
		public void setEx(Exception ex) {
			this.ex = ex;
		}
		public ByteArrayInputStream getInputStream() {
			return inputStream;
		}
		public void setInputStream(ByteArrayInputStream inputStream) {
			this.inputStream = inputStream;
		}
		public static HttpResponseBody custom(){
    		return new HttpResponseBody();
    	}
		
		public String  getBody(){
			if(inputStream==null) return null;
			ByteArrayOutputStream os = new ByteArrayOutputStream();//新建流。
			byte[] buffer = new byte[1024];
			int len;
			try {
				while ((len = inputStream.read(buffer)) > -1 ) {
					os.write(buffer, 0, len);
				}
				os.flush();
				byte b[] = os.toByteArray();//从流中获取数据数组。
				String str = new String(b,"UTF-8");
				return str;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
    }
}

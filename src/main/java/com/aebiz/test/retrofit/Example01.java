package com.aebiz.test.retrofit;

import java.util.Map;

import com.aebiz.entity.Ad;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Example01 {

	
	 @GET("ad/{id}") //这里的{id} 表示是一个变量
     Call<ResponseBody> getAd(/** 这里的id表示的是上面的{id} */@Path("id") String id);
	 
	 
	 
	 @GET("ad/{id}") //这里的{id} 表示是一个变量
     Call<Ad> getAd2(/** 这里的id表示的是上面的{id} */@Path("id") String id);
	 
	 
	 
	 
	// @Headers({
     //    "Content-Type: application/json",
	 //})
	 @FormUrlEncoded
	 @POST("ad/addAd")
	 Call<ResponseBody> createAd(@FieldMap Map<String,String> map);
	 @FormUrlEncoded
	 @POST("ad/addAd")
	 Call<ResponseBody> createAd2( @Field(value = "content") String  content,@Field(value = "name") String  name);
	 
	 
	 
	 
	 
	 
	 @GET("ad/{id}") //这里的{id} 表示是一个变量
     Call<Ad> getListPageAd(/** 这里的id表示的是上面的{id} */@Path("id") String id);
	 
	 
	 
}

package com.aebiz.test.retrofit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.aebiz.entity.Ad;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestEx {

	public static void main(String[] args) {

		// testAdd();

		testAdd2();

	}

	@Test
	public void get2() {

		Gson gson = new GsonBuilder()
				// 配置你的Gson
				.setDateFormat("yyyy-MM-dd hh:mm:ss").create();

		Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080/")
				.addConverterFactory(GsonConverterFactory.create(gson))

				.build();

		Example01 service = retrofit.create(Example01.class);

		Call<ResponseBody> call = service.getAd("48873850-4608-4a38-8312-d89c2cadfbe3");

		// 用法和OkHttp的call如出一辙
		// 不同的是如果是Android系统回调方法执行在主线程
		call.enqueue(new Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
				try {
					String string = response.body().string();

					Ad parseObject = JSON.parseObject(string, Ad.class);

					System.out.println(parseObject);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
				t.printStackTrace();
			}
		});

		Call<Ad> call2 = service.getAd2("48873850-4608-4a38-8312-d89c2cadfbe3");
		call2.enqueue(new Callback<Ad>() {

			@Override
			public void onFailure(Call<Ad> paramCall, Throwable t) {

			}

			@Override
			public void onResponse(Call<Ad> paramCall, Response<Ad> response) {
				Ad body = response.body();

				System.out.println(body);
			}

		});
	}

	// @Test
	public static void testAdd() {

		try {
			Gson gson = new GsonBuilder()
					// 配置你的Gson
					.setDateFormat("yyyy-MM-dd hh:mm:ss").create();

			Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080/")
					.addConverterFactory(GsonConverterFactory.create(gson))

					.build();

			Example01 service = retrofit.create(Example01.class);

			Ad ad = new Ad();

			ad.setContent("rreetor2222");
			ad.setName("zzzzzzzzz");

			Map<String, String> map = new HashMap<String, String>();

			map.put("content", "rreetor2222");
			Call<ResponseBody> call = service.createAd(map);

			call.enqueue(new Callback<ResponseBody>() {
				@Override
				public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
					try {
						String string = response.body().string();

						System.out.println(string);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(Call<ResponseBody> call, Throwable t) {
					t.printStackTrace();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testAdd2() {

		try {
			Gson gson = new GsonBuilder()
					// 配置你的Gson
					.setDateFormat("yyyy-MM-dd hh:mm:ss").create();

			Retrofit retrofit = new Retrofit.Builder().baseUrl("http://localhost:8080/")
					.addConverterFactory(GsonConverterFactory.create(gson))

					.build();

			Example01 service = retrofit.create(Example01.class);

			Ad ad = new Ad();

			ad.setContent("rr2222222222222");
			ad.setName("zzzzzzzzz");

			Map<String, String> map = new HashMap<String, String>();

			map.put("content", "rreetor2222");
			Call<ResponseBody> call = service.createAd2(ad.getContent(), ad.getName());

			call.enqueue(new Callback<ResponseBody>() {
				@Override
				public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
					try {
						String string = response.body().string();

						System.out.println(string);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(Call<ResponseBody> call, Throwable t) {
					t.printStackTrace();
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

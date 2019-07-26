package com.aebiz.inittools;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/init") 
public class ESToolsController {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	
	
	/**
	 * 创建索引
	 * @param dbName
	 */
	@RequestMapping(value = "/initEs")
	public void initEs(String dbName){
		
		CreateIndexResponse res = null;
		try {
			res = elasticsearchTemplate.getClient().admin().indices().prepareCreate(dbName).execute().actionGet();
		if (res.isAcknowledged()) {

			System.out.println("sucess!!");
		}
		else {
			System.out.println("exist!!");

		}
		
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	
	
	/**
	 * 创建类型
	 * @param dbName
	 */
	@RequestMapping(value = "/initEsType")
	public void initType(String dbName){
		
		
		try {
		XContentBuilder builder = XContentFactory.jsonBuilder().startObject().startObject("properties")
				.startObject("name").field("type", "string").field("store", "yes").endObject()
				
				.startObject("content").field("type", "string").field("store", "yes").endObject()
				
				.startObject("skuUuid").field("type", "string").field("store", "yes").field("index", "not_analyzed").endObject()
				.endObject().endObject();
		
		
		PutMappingRequest mapping = Requests.putMappingRequest(dbName).type(dbName).source(builder);
		PutMappingResponse res2 = elasticsearchTemplate.getClient().admin().indices().putMapping(mapping).actionGet();
		if (res2.isAcknowledged()) {
			System.out.println("创建成功！");
		}
		
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	
	
	
}

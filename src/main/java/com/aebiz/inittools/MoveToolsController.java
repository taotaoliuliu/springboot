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
import org.springframework.web.bind.annotation.RestController;

import com.aebiz.inittools.moveData.MoveDataService;


@RestController
@RequestMapping("/movedata") 
public class MoveToolsController {
	
	@Autowired
	private MoveDataService moveDataService;
	
	
	
	/**
	 * 创建索引
	 * @param dbName
	 */
	@RequestMapping(value = "/movedata")
	public String initEs(int pageShow){
		moveDataService.moveData(pageShow);
		
		return "success";
		
	}
	

	
	
	
}

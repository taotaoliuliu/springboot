package com.aebiz.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import com.aebiz.entity.Ad;
import com.aebiz.service.AdEsService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.github.pagehelper.PageInfo;

@Service
@Primary
public class AdEs2ServiceImpl implements AdEsService {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Override
	public PageInfo<Ad> getAdPage(int pageNum, int pageSize, Ad ad) {

	/*	Criteria cr = new Criteria();

		if (StringUtils.isNoneBlank(ad.getName())) {
			cr.and("name").fuzzy(ad.getName());

		}

		if (StringUtils.isNoneBlank(ad.getContent())) {
			cr.and("content").fuzzy(ad.getContent());
		}

		CriteriaQuery criteriaQuery = new CriteriaQuery(cr);

		criteriaQuery.setPageable(new PageRequest(pageNum, pageSize))
				.addSort(new Sort(new Sort.Order(Sort.Direction.DESC, "name")));*/
		
		
		
		
		
		
		BoolQueryBuilder query = QueryBuilders.boolQuery();// 411

		if (StringUtils.isNoneBlank(ad.getName())) {

			query.must(QueryBuilders.fuzzyQuery("name", ad.getName()));

		}

		if (StringUtils.isNoneBlank(ad.getContent())) {

			query.must(QueryBuilders.fuzzyQuery("content", ad.getContent()));
		}

		NativeSearchQuery searchQuery = (new NativeSearchQueryBuilder()).withIndices(new String[] { Ad.INDEX_NAME })
				.withTypes(new String[] { Ad.TYPE_NAME }).withQuery(query).withPageable(new PageRequest(0, 1000))
				.build();// 226 227

		Page<Ad> queryForPage = elasticsearchTemplate.queryForPage(searchQuery, Ad.class);

		PageInfo<Ad> page = new PageInfo<>();

		page.setList(queryForPage.getContent());
		page.setTotal(queryForPage.getTotalElements());

		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		return page;
	}

	@Override
	public void addMem(Ad ad) {
		String uuid = UUID.randomUUID().toString();

		String mJSON = JSON.toJSONString(ad);

		IndexQuery indexQuery = new IndexQueryBuilder().withId(uuid).withObject(mJSON).build();
		elasticsearchTemplate.index(indexQuery);
	}

	@Override
	public Ad getById(String uuid) {

		/*Criteria cr = new Criteria();
		cr.and("uuid").is(uuid);

		CriteriaQuery query = new CriteriaQuery(cr);*/
		
		
		BoolQueryBuilder query = QueryBuilders.boolQuery().must(QueryBuilders.termQuery("uuid", uuid));
		
		
		NativeSearchQuery searchQuery = (new NativeSearchQueryBuilder()).withIndices(new String[] { Ad.INDEX_NAME })
		.withTypes(new String[] { Ad.TYPE_NAME }).withQuery(query).withPageable(new PageRequest(0, 1000))
		.build();// 226 227
		
		List<Ad> queryForList = elasticsearchTemplate.queryForList(searchQuery, Ad.class);
		
		if(CollectionUtils.isNotEmpty(queryForList)){
			return queryForList.get(0);
		}

		return null;
	}

	@Override
	public void updateAd(Ad ad) {

		String mJSON = JSON.toJSONString(ad);

		UpdateQuery query = new UpdateQuery();

		query.setIndexName(Ad.INDEX_NAME);
		query.setType(Ad.TYPE_NAME);
		query.setId(ad.getUuid());
		// query.setClazz(Ad.class);

		UpdateRequest indexRequest = new UpdateRequest();// 151
		indexRequest.doc(mJSON);

		query.setUpdateRequest(indexRequest);

		elasticsearchTemplate.update(query);
		
		elasticsearchTemplate.refresh(Ad.INDEX_NAME);

	}

	@Override
	public void addAd(Ad ad) {
		String uuid = UUID.randomUUID().toString();
		//ad.setSkuUuid("111");

		String mJSON = JSON.toJSONString(ad,new SimplePropertyPreFilter(Ad.class,new String[0]), new SerializerFeature[0]);
		String mJSON2 = JSON.toJSONString(ad);
		
System.out.println("2222222");
System.out.println("222222233333");
		IndexQuery indexQuery = new IndexQueryBuilder().withIndexName(Ad.INDEX_NAME).withType(Ad.TYPE_NAME).withId(ad.getUuid())
				.withSource(mJSON2).build();
		elasticsearchTemplate.index(indexQuery);
		elasticsearchTemplate.refresh(Ad.INDEX_NAME);
		
		//elasticsearchTemplate.
	}
	
	public static void main(String[] args) {
		
		Ad ad =new Ad();
		ad.setName("222");
		ad.setContent("3344");
		String mJSON = JSON.toJSONString(ad,new SimplePropertyPreFilter(Ad.class,new String[0]));
		String mJSON2 = JSON.toJSONString(ad);

		System.out.println(mJSON);
		System.out.println(mJSON2);
	}

	@Override
	public void deleteAd(String uuid) {
		
		DeleteQuery deleteQuery = new DeleteQuery();// 183
		deleteQuery.setQuery(QueryBuilders.termsQuery("uuid", uuid));// 184
		deleteQuery.setIndex(Ad.INDEX_NAME);// 185
		deleteQuery.setType(Ad.TYPE_NAME);// 186
		this.elasticsearchTemplate.delete(deleteQuery);// 188
		this.elasticsearchTemplate.refresh(Ad.INDEX_NAME);// 189
		
		elasticsearchTemplate.delete(Ad.class, uuid);
	}

	@Override
	public List<Ad> getAd(Ad ad) {

		/*
		 * Criteria cr=new Criteria();
		 * 
		 * if(StringUtils.isNoneBlank(ad.getName())){
		 * cr.and("name").fuzzy(ad.getName());
		 * 
		 * }
		 * 
		 * 
		 * if(StringUtils.isNoneBlank(ad.getContent())){
		 * cr.and("content").fuzzy(ad.getContent());
		 * 
		 * }
		 * 
		 * CriteriaQuery query =new CriteriaQuery(cr);
		 */
		BoolQueryBuilder query = QueryBuilders.boolQuery();// 411

		if (StringUtils.isNoneBlank(ad.getName())) {

			query.must(QueryBuilders.fuzzyQuery("name", ad.getName()));

		}

		if (StringUtils.isNoneBlank(ad.getContent())) {

			query.must(QueryBuilders.fuzzyQuery("content", ad.getContent()));
		}

		NativeSearchQuery searchQuery = (new NativeSearchQueryBuilder()).withIndices(new String[] { Ad.INDEX_NAME })
				.withTypes(new String[] { Ad.TYPE_NAME }).withQuery(query).withPageable(new PageRequest(0, 1000))
				.build();// 226 227

		List<Ad> queryForList = elasticsearchTemplate.queryForList(searchQuery, Ad.class);
		return queryForList;
	}

}

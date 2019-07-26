package com.aebiz.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.storm.shade.org.eclipse.jetty.util.ajax.JSON;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.stereotype.Service;

import com.aebiz.entity.Ad;
import com.aebiz.service.AdEsService;
import com.github.pagehelper.PageInfo;

/**
 * 这种只适合@field 标记的情况
 * @author 55401
 *
 */
@Service
public class AdEsServiceImpl implements AdEsService{

	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Override
	public PageInfo<Ad> getAdPage(int pageNum, int pageSize , Ad ad) {
		
		
		Criteria cr=new Criteria();
		
		if(StringUtils.isNoneBlank(ad.getName())){
			cr.and("name").fuzzy(ad.getName());

		}
		
		if(StringUtils.isNoneBlank(ad.getContent())){
			cr.and("content").fuzzy(ad.getContent());
		}
		
		CriteriaQuery criteriaQuery =new CriteriaQuery(cr);
		
		criteriaQuery.setPageable(new PageRequest(pageNum,pageSize)).addSort(
				new Sort(new Sort.Order(Sort.Direction.DESC, "name")));
		
		Page<Ad> queryForPage = elasticsearchTemplate.queryForPage(criteriaQuery, Ad.class);
		
		
		PageInfo<Ad> page=new PageInfo<>();
		
		page.setList(queryForPage.getContent());
		page.setTotal(queryForPage.getTotalElements());
		
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		return page;
	}
	@Override
	public void addMem(Ad ad) {
		String uuid = UUID.randomUUID().toString();

		
		String mJSON = JSON.toString(ad);
		
		IndexQuery indexQuery = new IndexQueryBuilder().withId(uuid).withObject(mJSON).build();
		elasticsearchTemplate.index(indexQuery);
	}
	@Override
	public Ad getById(String uuid) {
		
		Criteria cr=new Criteria();
		cr.and("uuid").is(uuid);
		
		CriteriaQuery query =new CriteriaQuery(cr);
		Ad m = elasticsearchTemplate.queryForObject(query, Ad.class);
		
		return m;
	}
	@Override
	public void updateAd(Ad ad) {
		
		String mJSON = JSON.toString(ad);

		
		UpdateQuery query =new UpdateQuery();
		
		query.setIndexName(Ad.INDEX_NAME);
		query.setType(Ad.TYPE_NAME);
		query.setId(ad.getUuid());
	//	query.setClazz(Ad.class);
		
		UpdateRequest indexRequest = new UpdateRequest();// 151
		indexRequest.doc(mJSON);
		
		query.setUpdateRequest(indexRequest);
		
		elasticsearchTemplate.update(query);
			
		
	}
	@Override
	public void addAd(Ad ad) {
		String uuid = UUID.randomUUID().toString();

		
		String mJSON = JSON.toString(ad);
		
		IndexQuery indexQuery = new IndexQueryBuilder().withId(uuid).withObject(mJSON).build();
		elasticsearchTemplate.index(indexQuery);		
	}
	@Override
	public void deleteAd(String uuid) {
		elasticsearchTemplate.delete(Ad.class, uuid);
	}
	@Override
	public List<Ad> getAd(Ad ad) {
		
		
		
		Criteria cr=new Criteria();
		
		if(StringUtils.isNoneBlank(ad.getName())){
			cr.and("name").fuzzy(ad.getName());

		}
		
		
		if(StringUtils.isNoneBlank(ad.getContent())){
			cr.and("content").fuzzy(ad.getContent());

		}
		
		CriteriaQuery query =new CriteriaQuery(cr);
		
		
		List<Ad> queryForList = elasticsearchTemplate.queryForList(query, Ad.class);
		return queryForList;
	}

}

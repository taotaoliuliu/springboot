package com.aebiz.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aebiz.entity.Ad;

/**
 * UserMapper.xml代理
 * @date 2016/8/13
 */
@Mapper
public interface AdMapper {
    /**
     * uuid 查询
     * @param id
     * @return
     */
    Ad getById(String id);

    
    
	List<Ad> getAd(Ad ad);



	void addAd(Ad ad);
	
	void updateAd(Ad ad);
	
	void deleteAd(@Param(value="uuid")String uuid);
}

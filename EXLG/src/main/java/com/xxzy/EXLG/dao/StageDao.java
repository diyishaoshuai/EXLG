package com.xxzy.EXLG.dao;

import com.xxzy.EXLG.entity.StageEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * 
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:18
 */
@Mapper
public interface StageDao extends BaseMapper<StageEntity> {
    Long existStage(@Param("provinceId") Long provinceId, @Param("cityId") Long cityId, @Param("countyId") Long countyId);
}

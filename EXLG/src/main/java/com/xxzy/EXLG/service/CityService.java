package com.xxzy.EXLG.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.entity.CityEntity;

import java.util.List;
import java.util.Map;

/**
 * @author zhufeng
 */
public interface CityService extends IService<CityEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     *  通过省份id查找所有该省份下所有的城市
     * @param id
     * @return
     */
    List<CityEntity> getCitysById(Long id);
}


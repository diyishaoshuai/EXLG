package com.xxzy.EXLG.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.entity.CountyEntity;


import java.util.List;
import java.util.Map;


/**
 * @author zhufeng
 */
public interface CountyService extends IService<CountyEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     *  通过城市id查找所有该城市下的县城
     * @param id
     * @return
     */
    List<CountyEntity> getCountysById(Long id);
}


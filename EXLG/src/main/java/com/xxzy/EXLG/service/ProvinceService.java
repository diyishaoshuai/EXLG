package com.xxzy.EXLG.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.entity.CityEntity;
import com.xxzy.EXLG.entity.ProvinceEntity;


import java.util.List;
import java.util.Map;


/**
 * @author zhufeng
 */
public interface ProvinceService extends IService<ProvinceEntity> {

    PageUtils queryPage(Map<String, Object> params);

}


package com.xxzy.EXLG.service.impl;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.dao.ProvinceDao;
import com.xxzy.EXLG.entity.CityEntity;
import com.xxzy.EXLG.entity.ProvinceEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xxzy.EXLG.service.ProvinceService;


/**
 * @author zhufeng
 */
@Service("provinceService")
public class ProvinceServiceImpl extends ServiceImpl<ProvinceDao, ProvinceEntity> implements ProvinceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProvinceEntity> page = this.page(
                new Query<ProvinceEntity>().getPage(params),
                new QueryWrapper<ProvinceEntity>()
        );

        return new PageUtils(page);
    }


}
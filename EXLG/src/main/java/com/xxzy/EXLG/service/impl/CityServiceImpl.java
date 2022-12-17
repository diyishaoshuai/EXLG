package com.xxzy.EXLG.service.impl;



import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.dao.CityDao;
import com.xxzy.EXLG.entity.CityEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xxzy.EXLG.service.CityService;


/**
 * @author zhufeng
 */
@Service("cityService")
public class CityServiceImpl extends ServiceImpl<CityDao, CityEntity> implements CityService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CityEntity> page = this.page(
                new Query<CityEntity>().getPage(params),
                new QueryWrapper<CityEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CityEntity> getCitysById(Long id) {
        List<CityEntity> citys = this.baseMapper.selectList(new QueryWrapper<CityEntity>().eq("province_id", id));
        return citys;
    }

}
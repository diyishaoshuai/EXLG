package com.xxzy.EXLG.service.impl;



import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.dao.CountyDao;
import com.xxzy.EXLG.entity.CountyEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xxzy.EXLG.service.CountyService;


/**
 * @author zhufeng
 */
@Service("countyService")
public class CountyServiceImpl extends ServiceImpl<CountyDao, CountyEntity> implements CountyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CountyEntity> page = this.page(
                new Query<CountyEntity>().getPage(params),
                new QueryWrapper<CountyEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CountyEntity> getCountysById(Long id) {
        List<CountyEntity> citys = this.baseMapper.selectList(new QueryWrapper<CountyEntity>().eq("city_id", id));
        return citys;
    }

}
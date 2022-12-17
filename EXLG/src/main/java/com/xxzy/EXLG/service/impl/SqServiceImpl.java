package com.xxzy.EXLG.service.impl;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.dao.SqDao;
import com.xxzy.EXLG.entity.SqEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xxzy.EXLG.service.SqService;


@Service("sqService")
public class SqServiceImpl extends ServiceImpl<SqDao, SqEntity> implements SqService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SqEntity> page = this.page(
                new Query<SqEntity>().getPage(params),
                new QueryWrapper<SqEntity>()
        );

        return new PageUtils(page);
    }

}
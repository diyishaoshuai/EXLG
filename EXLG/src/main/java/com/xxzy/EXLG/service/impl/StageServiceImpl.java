package com.xxzy.EXLG.service.impl;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.dao.StageDao;
import com.xxzy.EXLG.entity.StageEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xxzy.EXLG.service.StageService;


@Service("stageService")
public class StageServiceImpl extends ServiceImpl<StageDao, StageEntity> implements StageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<StageEntity> page = this.page(
                new Query<StageEntity>().getPage(params),
                new QueryWrapper<StageEntity>()
        );

        return new PageUtils(page);
    }

}
package com.xxzy.EXLG.service.impl;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.dao.HqDao;
import com.xxzy.EXLG.entity.HqEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import com.xxzy.EXLG.service.HqService;


@Service("hqService")
public class HqServiceImpl extends ServiceImpl<HqDao, HqEntity> implements HqService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<HqEntity> page = this.page(
                new Query<HqEntity>().getPage(params),
                new QueryWrapper<HqEntity>()
        );

        return new PageUtils(page);
    }

}
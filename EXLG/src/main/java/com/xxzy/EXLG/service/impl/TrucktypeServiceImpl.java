package com.xxzy.EXLG.service.impl;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.dao.TrucktypeDao;
import com.xxzy.EXLG.entity.TrucktypeEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxzy.EXLG.service.TrucktypeService;


@Service("trucktypeService")
public class TrucktypeServiceImpl extends ServiceImpl<TrucktypeDao, TrucktypeEntity> implements TrucktypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TrucktypeEntity> page = this.page(
                new Query<TrucktypeEntity>().getPage(params),
                new QueryWrapper<TrucktypeEntity>()
        );

        return new PageUtils(page);
    }

}
package com.xxzy.EXLG.service.impl;

import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.dao.CargoDao;
import com.xxzy.EXLG.entity.CargoEntity;
import com.xxzy.EXLG.service.CargoService;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("cargoService")
public class CargoServiceImpl extends ServiceImpl<CargoDao, CargoEntity> implements CargoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CargoEntity> page = this.page(
                new Query<CargoEntity>().getPage(params),
                new QueryWrapper<CargoEntity>()
        );

        return new PageUtils(page);
    }

}
package com.xxzy.EXLG.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.entity.TruckEntity;
import com.xxzy.EXLG.vo.TruckVo;


import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:18
 */
public interface TruckService extends IService<TruckEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     *  保存车辆信息
     * @param truck
     */
    R saveOne(TruckVo truck);

    /**
     *  发车
     * @param id
     * @return
     */
    R sendCar(Long id);

    /**
     *  获取所有车辆且封装车辆能否转载该货物的信息  disabled
     * @param id
     * @return
     */
    List<TruckVo> getAllUsableTruck(Long id);
}


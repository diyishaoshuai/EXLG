package com.xxzy.EXLG.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.entity.OrderEntity;
import com.xxzy.EXLG.vo.AddOrderVo;
import com.xxzy.EXLG.vo.OrderVo;
import com.xxzy.EXLG.vo.ReceiveOrderVo;
import com.xxzy.EXLG.vo.SendOrderVo;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:19
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     *  通过给定的状态号,查出这一状态下的所有订单
     * @return
     */
    PageUtils getOrderByStatus(Map<String, Object> params);

    /**
     *  通过关键词查找相关的订单
     * @return
     */
    PageUtils getOrderByKey(Map<String, Object> params);

    /**
     * 获取所有没有发货的订单
     * @param params
     * @return
     */
    PageUtils getNotShippedList(Map<String, Object> params);

    /**
     *  处理订单装车
     * @param id
     * @return
     */
    R handleUpTruck(Long id,String truckMark);

    /**
     *  根据订单id签收订单
     * @param id
     * @return
     */
    R signOrder(Long id);

    /**
     * 寄出的订单
     * @param userId
     * @return
     */
    List<SendOrderVo> getSendOrdersByUserId(Long userId);

    /**
     *  添加订单
     * @param addOrderVo
     * @return
     */
    R addOrder(AddOrderVo addOrderVo);

    /**
     * 收到的订单
     * @param userId
     * @return
     */
    List<ReceiveOrderVo> getReceiveOrdersByUserId(Long userId);
}


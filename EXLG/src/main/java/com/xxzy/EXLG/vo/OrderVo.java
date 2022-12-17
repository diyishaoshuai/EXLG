package com.xxzy.EXLG.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gjq0117
 * @email  gjq0117@163.com
 * @date 2022/4/28 下午 03:31
 * @describe
 */
@Data
public class OrderVo {
    private Long id;   //订单id
    private String orderId; //订单号
    private String sendName; //寄件人姓名     (bean中没有封装,需要查询  sendUserId)
    private String sendUserTel; //寄件人电话
    private String startPlace;  //寄件详细地址
    private BigDecimal totalPrice;  //总共价格
    private String sendStage;  //发货站点名字  (bean中没有封装,需要查询  startStageId)
    private String receiveName; //收货人姓名  (bean中没有封装,需要查询   receiveUserId)
    private String receiveUserTel;  //收货人电话
    private String endPlace;  //收获地址
    private Double totalWeigh;  //总重量
    private Double pathLength; //总路程
    private String status; //订单状态
    private Date createTime;  //创建时间
    private Date updateTime;  //更新时间
}

package com.xxzy.EXLG.vo;

import lombok.Data;

/**
 * @author 竹风
 * date:2022/6/1
 */
@Data
public class ReceiveOrderVo {
    private String id;    // 订单id
    private String send;
    private String receive;
    private String departure;   // 寄货地址
    private String destination; // 收货地址
}


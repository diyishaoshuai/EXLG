package com.xxzy.EXLG.vo;

import lombok.Data;

@Data
public class SendOrderVo {
    private String id;    // 订单id
    private String send;
    private String receive;
    private String departure;   // 寄货地址
    private String destination; // 收货地址
}

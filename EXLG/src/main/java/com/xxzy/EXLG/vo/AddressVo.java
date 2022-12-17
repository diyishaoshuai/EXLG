package com.xxzy.EXLG.vo;

import lombok.Data;

/**
 *   地址簿信息
 */
@Data
public class AddressVo {

    private Long id;
    /**
     *  用户id
     */
    private Long userId;
    /**
     *  寄件人名
     */
    private String name;
    /**
     *  寄件人电话
     */
    private String phoneNumber;
    /**
     *  详细地址
     */
    private String address;
    /**
     *  省
     */
    private String province;
    /**
     *  市
     */
    private String city;
    /**
     *  县
     */
    private String county;

    /**
     * 区域地址
     */
    private String area;
}

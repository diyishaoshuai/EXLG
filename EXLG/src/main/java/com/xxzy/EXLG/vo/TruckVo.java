package com.xxzy.EXLG.vo;

import lombok.Data;

@Data
public class TruckVo {

    private Long id;
    /**
     *  类型
     */
    private String typeName;
    /**
     *  状态
     */
    private String status;
    /**
     *  车牌号
     */
    private String truckMark;
    /**
     *  最大承受重量(kg)
     */
    private Double maxWeight;
    /**
     *  剩余承受重量(kg)
     */
    private Double remainderWeight;
    /**
     *  判断该车辆是否还可以装货
     *  true : 不显示
     *  false： 显示
     */
    private Boolean disabled;
}

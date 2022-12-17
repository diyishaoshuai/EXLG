package com.xxzy.EXLG.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("address_book")
public class AddressBookEntity {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 寄件人姓名
     */
    private String sendUsername;
    /**
     * 寄件人电话
     */
    private String sendTel;
    /**
     * 所属省份id
     */
    private Long provinceId;
    /**
     * 所属城市id
     */
    private Long cityId;
    /**
     * 所属县级id
     */
    private Long countyId;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 区域地址
     */
    private String area;
    /**
     * 创建时间
     * 自动填充
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     * 自动填充
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 0:未删除  1：删除
     */
    private Integer isDelete;
}

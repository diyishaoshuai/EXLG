package com.xxzy.EXLG.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;


import lombok.Data;

/**
 * 
 * 
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:19
 */
@Data
@TableName("Cargo")
public class CargoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 所属订单编号
     */
    private Long orderId;
    /**
     * 重量(kg)
     */
    private Double weight;
    /**
     * 创建时间
     * 自动填充
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 更新时间
     * 自动填充
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 0:未删除  1：删除
     */

    private Integer isDelete;

}

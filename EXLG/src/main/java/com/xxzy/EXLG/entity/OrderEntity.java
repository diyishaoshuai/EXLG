package com.xxzy.EXLG.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("order_info")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 订单编号
	 */
	private String orderId;
	/**
	 * 寄件人id
	 */
	private Long sendUserId;
	/**
	 * 寄件人电话
	 */
	private String sendUserTel;
	/**
	 * 收件人id
	 */
	private Long receiveUserId;
	/**
	 *  所属id
	 */
	private Long affiliation_id;
	/**
	 * 收件人电话
	 */
	private String receiveUserTel;
	/**
	 * 所属子公司id
	 */
	private Long sqId;
	/**
	 * 0：未发货   1：已发货   2：运输中  3：已送达   4：已签收
	 */
	private Integer status;
	/**
	 * 总价格
	 */
	private BigDecimal totalPrice;
	/**
	 * 开始省份id
	 */
	private Long startProvinceId;
	/**
	 * 开始城市id
	 */
	private Long startCityId;
	/**
	 * 开始县城id
	 */
	private Long startCountyId;
	/**
	 * 开始站点id
	 */
	private Long startStageId;
	/**
	 * 起始详细地址
	 */
	private String startPlace;
	/**
	 * 目的省份id
	 */
	private Long endProvinceId;
	/**
	 * 目的城市id
	 */
	private Long endCityId;
	/**
	 * 目的县城id
	 */
	private Long endCountyId;
	/**
	 * 目的站点id
	 */
	private Long endStageId;
	/**
	 * 目的详细地址
	 */
	private String endPlace;
	/**
	 * 总路程（km）
	 */
	private Double pathLength;
	/**
	 *  总重量(kg)
	 */
	private Double totalWeigh;
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

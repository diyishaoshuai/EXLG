package com.xxzy.EXLG.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

@Data
public class UserVo {
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String telephone;
    /**
     * 密码
     */
    private String password;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 别名
     */
    private String nickname;
    /**
     * 0: 女  1: 男
     */
    private Integer sex;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String headimage;
    /**
     * 0:普通用户   1:工作人员
     */
    private String usertype;
    /**
     * 员工id   默认-1: 表示普通用户
     */
    private Long employeeId;
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
     * 收货站点ID
     */
    private Long stageId;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 修改时间
     */
    private Date updatetime;
    /**
     * 0:未删除  1：删除
     */
    private Integer isdelete;
}

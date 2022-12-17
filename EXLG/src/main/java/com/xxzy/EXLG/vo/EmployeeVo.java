package com.xxzy.EXLG.vo;

import lombok.Data;

import java.sql.Date;

@Data
public class EmployeeVo {
    private Long id;
    /**
     *  用户名
     */
    private String username;
    /**
     *  密码
     */
    private String password;
    /**
     * 姓名
     */
    private String name;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 0:女  1:男
     */
    private Integer sex;
    /**
     * 联系电话
     */
    private String telephone;
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
     * 邮箱
     */
    private String email;
    /**
     * 职位  0: 负责人(可对所有数据增删改查)  1: 普通员工  2: 司机
     */
    private Integer job;
    /**
     * 1: 配送点人员    2: 子公司人员
     */
    private Integer affiliationType;
    /**
     * 公司id或配送点id
     */
    private Long affiliationId;
    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 更新时间
     */
    private Date updatetime;
    /**
     * 0:未删除  1：删除
     */
    private Integer isdelete;
}

package com.xxzy.EXLG.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author gjq0117
 * @email  gjq0117@163.com
 * @date 2022/4/25 下午 05:04
 * 注册员工、站点或子公司信息
 * JSR303后端校验
 */
@Data
public class RegStageOrSQVo {
    /**
     *  用户名(必填)
     */
    @NotEmpty(message="用户名不能为空")
    private String username;
    /**
     *  密码(必填)
     */
    @Length(min = 6,max = 18,message = "密码必须是6-18位")
    private String password;
    /**
     *  手机号(必填)
     */
    @Pattern(regexp = "^[1]([3-9])[0-9]{9}$",message = "手机号格式不正确")
    private String telephone;
    /**
     *  注册类型:  1: 注册子公司  2: 注册站点
     */
    private Integer regType;
    /**
     *  注册地省名(必填)
     */
    @NotEmpty(message="请选择省份")
    private String province;
    /**
     *  注册地市名(必填)
     */
    @NotEmpty(message = "请选择市级信息")
    private String city;
    /**
     *  注册地县名(非必填)
     */
//    @NotEmpty(message = "请选择县级信息")
    private String county;
}

package com.xxzy.EXLG.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.entity.EmployeeEntity;
import com.xxzy.EXLG.vo.EmpLoginVo;
import com.xxzy.EXLG.vo.RegStageOrSQVo;


import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:19
 */
public interface EmployeeService extends IService<EmployeeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 注册站点或子公司
     *
     * @param regStageOrSQ
     * @return
     */
    R regStageOrSQ(RegStageOrSQVo regStageOrSQ);

    /**
     * 员工登录
     *
     * @param empLoginVo
     * @return
     */
    EmployeeEntity empLogin(EmpLoginVo empLoginVo);

    /**
     * 更新员工基本信息
     * id name age sex telephone email
     *
     * @param employee
     */
    R basicUpdateById(EmployeeEntity employee);

    /**
     * 查询此公司或站点的所有员工
     *
     * @param affiliationId
     * @param affiliationType
     * @return
     */
    PageUtils queryCompanyOrStageAllEmp(Map<String, Object> params, Long affiliationId, Integer affiliationType);

    /**
     * 添加单个员工
     *
     * @param employee
     * @param session
     */
    void saveEmpByOne(EmployeeEntity employee, HttpSession session);
}


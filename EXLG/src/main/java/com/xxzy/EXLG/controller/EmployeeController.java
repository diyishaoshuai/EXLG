package com.xxzy.EXLG.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.xxzy.EXLG.common.constant.JobConstant;
import com.xxzy.EXLG.common.constant.UserConstant;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.vo.EmpLoginVo;
import com.xxzy.EXLG.vo.RegStageOrSQVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.xxzy.EXLG.entity.EmployeeEntity;
import com.xxzy.EXLG.service.EmployeeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:19
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     *  员工登出
     */
    @GetMapping("/logout")
    public R empLogout(HttpSession session){
        session.removeAttribute(UserConstant.LOGIN_USER);
        return R.ok();
    }
    /**
     * 员工登录
     */
    @PostMapping("/empLogin")
    public R empLogin(@RequestBody EmpLoginVo empLoginVo, HttpSession session) {
        EmployeeEntity employeeEntity = employeeService.empLogin(empLoginVo);
        if (employeeEntity == null) {  //登录失败
            return R.error().put("msg", "用户名或密码错误");
        } else {  //登录成功, 将信息保存到session中
            Object attribute = session.getAttribute(UserConstant.LOGIN_USER);
            if (attribute == null) {
                session.setAttribute(UserConstant.LOGIN_USER, employeeEntity);    // session已经被springSession包装过的,直接存到redis
            }
            return R.ok().put("emp", employeeEntity);
        }
    }

    /**
     * 注册管理员(每个省份只能有一个总公司但可以有多个站点,每个县城只能有一个站点)
     */
    @PostMapping("/regStageOrSQ")
    public R regStageOrSQ(@RequestBody @Valid RegStageOrSQVo regStageOrSQ, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = result.getFieldErrors().stream().collect(Collectors.toMap(fieldError -> {
                return fieldError.getField();
            }, fieldError -> {
                return fieldError.getDefaultMessage();
            }));
            return R.error().put("errors", errors);
        }
        R r = employeeService.regStageOrSQ(regStageOrSQ);
        return r;
    }

    /**
     * 查询此公司或站点的所有员工
     * 带筛选功能
     *
     * @param session
     * @return
     */
    @GetMapping("/listByPart")
    public R listByPart(@RequestParam(required = false) Map<String, Object> params, HttpSession session) {
        System.out.println("--------------------------------------------------------");
        EmployeeEntity employeeEntity = (EmployeeEntity) session.getAttribute(UserConstant.LOGIN_USER);
        PageUtils page = employeeService.queryCompanyOrStageAllEmp(params, employeeEntity.getAffiliationId(), employeeEntity.getAffiliationType());
        return R.ok().put("page", page);
    }


    /**
     * 获取所有员工的职位
     * @return
     */
    @GetMapping("/getAllEmpJob")
    public R getAllEmpJob() {
        List<String> list = new ArrayList();
        for (JobConstant.JobTypeEnum value : JobConstant.JobTypeEnum.values()) {
            list.add(value.getMsg());
        }
        return R.ok().put("list", list);
    }


    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(required = false) Map<String, Object> params) {
        PageUtils page = employeeService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 根据员工id删除员工
     *
     * @param id
     * @return
     */
    @DeleteMapping("/deleteById/{id}")
    public R deleteById(@PathVariable("id") Long id) {
        employeeService.removeById(id);
        return R.ok();
    }

    /**
     * 根据员工id获取数据
     *
     * @param id
     * @return
     */
    @GetMapping("/getEmpById/{id}")
    public R getEmpById(@PathVariable("id") Long id) {
        EmployeeEntity employeeEntity = employeeService.getById(id);
        return R.ok().put("emp", employeeEntity);
    }

    /**
     * 保存或修改一条员工信息
     *
     * @param employee
     * @param session
     * @return
     */
    @PostMapping("/saveEmpByOne")
    public R saveEmpByOne(@RequestBody EmployeeEntity employee, HttpSession session) {
        if (employee.getId() != null) {
            employeeService.updateById(employee);
        } else {
            employeeService.saveEmpByOne(employee, session);
        }
        return R.ok();
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        EmployeeEntity employee = employeeService.getById(id);

        return R.ok().put("employee", employee);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody EmployeeEntity employee) {
        employeeService.save(employee);

        return R.ok();
    }

    /**
     * 修改
     * id name age sex telephone email
     */
    @PutMapping("/update")
    public R update(@RequestBody EmployeeEntity employee) {
        return employeeService.basicUpdateById(employee);
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        employeeService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}

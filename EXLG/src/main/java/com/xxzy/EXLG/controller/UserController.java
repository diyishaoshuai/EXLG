package com.xxzy.EXLG.controller;

import java.util.Arrays;
import java.util.Map;

import com.xxzy.EXLG.common.constant.UserConstant;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xxzy.EXLG.entity.UserEntity;
import com.xxzy.EXLG.service.UserService;

import javax.servlet.http.HttpSession;


/**
 * 
 *
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:18
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    /**
     *  登录+注册      用户名或手机号登录     如果是第一次登陆 就去注册,然后直接登陆,  如果不是第一次登陆  直接登陆
     * @param
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginvo){
        UserEntity login = userService.login(loginvo);
        return R.ok().put("userInfo",login);
    }
    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions(":user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

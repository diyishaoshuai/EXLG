package com.xxzy.EXLG.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.entity.UserEntity;
import com.xxzy.EXLG.vo.LoginVo;
import com.xxzy.EXLG.vo.UserVo;


import java.util.Map;

/**
 * 
 *
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:18
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     *  登录
     * @param
     * @return
     */
    UserEntity login(LoginVo loginVo);
}


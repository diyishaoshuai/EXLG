package com.xxzy.EXLG.service.impl;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.dao.UserDao;
import com.xxzy.EXLG.entity.UserEntity;
import com.xxzy.EXLG.vo.LoginVo;
import com.xxzy.EXLG.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxzy.EXLG.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 登录
     *
     * @param
     * @return
     */
    @Override
    public UserEntity login(LoginVo loginVo) {
        //1、去数据库中查找这个人是否存在
        UserEntity one = this.getOne(new QueryWrapper<UserEntity>()
                .eq("telephone", loginVo.getTelephone()));
        if (one == null) {
            // 没有这个人  注册
            UserEntity register = this.register(loginVo);
//            this.login(loginVo);
            return register;
        } else if (one.getPassword().equals(loginVo.getPassword())) {
            // 有 判断信息是否正确  直接登陆
            return one;
        } else {
            return null;
        }
    }

    /**
     * 注册  注册完后返回用户信息
     * @param loginVo
     * @return
     */
    public UserEntity register(LoginVo loginVo) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(loginVo, userEntity);
        // 设置默认属性
        userEntity.setHeadImage("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        userEntity.setNickName(loginVo.getTelephone());
        this.baseMapper.insert(userEntity);
        return userEntity;
    }
}
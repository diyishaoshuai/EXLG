package com.xxzy.EXLG.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.entity.SqEntity;


import java.util.Map;

/**
 * 
 *
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:18
 */
public interface SqService extends IService<SqEntity> {

    PageUtils queryPage(Map<String, Object> params);
}


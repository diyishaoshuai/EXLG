package com.xxzy.EXLG.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxzy.EXLG.entity.AddressBookEntity;
import com.xxzy.EXLG.vo.AddressVo;

import java.util.List;

public interface AddressBookService extends IService<AddressBookEntity> {
    /**
     *  添加地址
     * @param address
     */
    void save(AddressVo address);

    /**
     * 通过用户id查询其地址铺
     * @param userId
     * @return
     */
    List<AddressBookEntity> selectAddressById(String userId);

    /**
     * 通过id封装每个阶级的区域名
     * @param id
     * @return
     */
    String[] getAddressName(Long id);
}

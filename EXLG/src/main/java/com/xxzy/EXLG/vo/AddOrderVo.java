package com.xxzy.EXLG.vo;

import com.xxzy.EXLG.entity.AddressBookEntity;
import lombok.Data;

@Data
public class AddOrderVo {
    private AddressBookEntity send;
    private AddressBookEntity receive;
    private String objectMsg; // 物品重量
}

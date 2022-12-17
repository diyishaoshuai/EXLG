package com.xxzy.EXLG.controller;

import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.entity.AddressBookEntity;
import com.xxzy.EXLG.service.AddressBookService;
import com.xxzy.EXLG.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addressBook")
public class AddressBookController {

    @Autowired
    AddressBookService addressBookService;

    @PostMapping("/save")
    public R saveAddress(@RequestBody AddressVo address) {
        addressBookService.save(address);
        return R.ok();
    }

    @GetMapping("/get")
    public R getAddress(@RequestParam("userId") String userId) {
        List<AddressBookEntity> addressBookEntities = addressBookService.selectAddressById(userId);
        return R.ok().setData(addressBookEntities);
    }

    @GetMapping("/remove")
    public R removeById(@RequestParam("id") String id) {
        addressBookService.removeById(Long.parseLong(id));
        return R.ok();
    }

    @GetMapping("/getById")
    public R getById(@RequestParam("id") String id) {
        AddressBookEntity bookEntity = addressBookService.getById(Long.parseLong(id));
        String[] addressName = addressBookService.getAddressName(Long.parseLong(id));
        return R.ok().setData(bookEntity).put("addressName", addressName);
    }


}

package com.xxzy.EXLG.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxzy.EXLG.dao.AddressBookDao;
import com.xxzy.EXLG.entity.AddressBookEntity;
import com.xxzy.EXLG.entity.CityEntity;
import com.xxzy.EXLG.entity.CountyEntity;
import com.xxzy.EXLG.entity.ProvinceEntity;
import com.xxzy.EXLG.service.AddressBookService;
import com.xxzy.EXLG.service.CityService;
import com.xxzy.EXLG.service.CountyService;
import com.xxzy.EXLG.service.ProvinceService;
import com.xxzy.EXLG.vo.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookDao, AddressBookEntity> implements AddressBookService {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private CountyService countyService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public void save(AddressVo address) {
        AddressBookEntity addressBookEntity = new AddressBookEntity();
        addressBookEntity.setId(address.getId());
        // 通过省市县名字找到三者的id
        // 省
        String provinceName = address.getProvince();
        // 市
        String cityName = address.getCity();
        // 县
        String countyName = address.getCounty();
        // 开启异步编排
        CompletableFuture<ProvinceEntity> ProvinceFuture = CompletableFuture.supplyAsync(() -> {
            //  找省
            ProvinceEntity province = provinceService.getOne(new QueryWrapper<ProvinceEntity>().eq("name", provinceName));
            return province;
        }, executor);

        //  找市
        CompletableFuture<CityEntity> cityFuture = CompletableFuture.supplyAsync(() -> {
            CityEntity city = cityService.getOne(new QueryWrapper<CityEntity>().eq("name", cityName));
            return city;
        }, executor);

        CompletableFuture<CountyEntity> countyFuture = CompletableFuture.supplyAsync(() -> {
            //  找县
            CountyEntity county = countyService.getOne(new QueryWrapper<CountyEntity>().eq("name", countyName));
            return county;
        }, executor);
        // 组合完成
        CompletableFuture.allOf(ProvinceFuture, cityFuture, countyFuture);
        ProvinceEntity province = null;
        CityEntity city = null;
        CountyEntity county = null;
        try {
            province = ProvinceFuture.get();
            city = cityFuture.get();
            county = countyFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 属性对口
        addressBookEntity.setUserId(address.getUserId());
        addressBookEntity.setSendUsername(address.getName());
        addressBookEntity.setAddress(address.getAddress());
        addressBookEntity.setArea(address.getArea());
        addressBookEntity.setSendTel(address.getPhoneNumber());
        addressBookEntity.setProvinceId(province.getId());
        addressBookEntity.setCityId(city.getId());
        if (county != null) {
            addressBookEntity.setCountyId(county.getId());
        }
        if (address.getId() == null) {
            baseMapper.insert(addressBookEntity);
        } else {
            baseMapper.updateById(addressBookEntity);
        }
    }


    @Override
    public List<AddressBookEntity> selectAddressById(String userId) {
        QueryWrapper<AddressBookEntity> wrapper = new QueryWrapper<>();
//        wrapper.eq("user_id", userId);
        List<AddressBookEntity> addressBookEntities = baseMapper.selectList(wrapper);
        return addressBookEntities;
    }

    @Override
    public String[] getAddressName(Long id) {
        AddressBookEntity bookEntity = baseMapper.selectById(id);
        String[] addressName = new String[3];
        // 开启异步编排
        CompletableFuture<ProvinceEntity> provinceFuture = CompletableFuture.supplyAsync(() -> {
            //  找省
            ProvinceEntity province = provinceService.getOne(new QueryWrapper<ProvinceEntity>().eq("id", bookEntity.getProvinceId()));
            return province;
        }, executor);

        //  找市
        CompletableFuture<CityEntity> cityFuture = CompletableFuture.supplyAsync(() -> {
            CityEntity city = cityService.getOne(new QueryWrapper<CityEntity>().eq("id", bookEntity.getCityId()));
            return city;
        }, executor);

        CompletableFuture<CountyEntity> countyFuture = CompletableFuture.supplyAsync(() -> {
            //  找县
            CountyEntity county = null;
            //判断有没有县城,有就查询
            if (bookEntity.getCountyId() != null) {
                county = countyService.getOne(new QueryWrapper<CountyEntity>().eq("id", bookEntity.getCountyId()));
            }
            return county;
        }, executor);
        // 组合完成
        CompletableFuture.allOf(provinceFuture, cityFuture, countyFuture);
        try {
            if (countyFuture.get() == null) {
                addressName[0] = provinceFuture.get().getName();
                addressName[1] = cityFuture.get().getName();
                addressName[2] = "";
            } else {
                addressName[0] = provinceFuture.get().getName();
                addressName[1] = cityFuture.get().getName();
                addressName[2] = countyFuture.get().getName();
            }
        } catch (Exception e) {

        }
        return addressName;
    }
}

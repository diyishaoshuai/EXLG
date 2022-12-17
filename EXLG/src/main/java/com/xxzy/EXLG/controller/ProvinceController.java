package com.xxzy.EXLG.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.entity.CityEntity;
import com.xxzy.EXLG.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xxzy.EXLG.entity.ProvinceEntity;
import com.xxzy.EXLG.service.ProvinceService;





/**
 * @author zhufeng
 */
@RestController
@RequestMapping("/province")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    /**
     *   通过省份id返回所有的该省份下的城市
     */
    @GetMapping("/getCitysById/{id}")
    public R getCitysById(@PathVariable("id") Long id){
       List<CityEntity> citys =  cityService.getCitysById(id);
       return R.ok().put("citys",citys);
    }
    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = provinceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ProvinceEntity province = provinceService.getById(id);

        return R.ok().put("province", province);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ProvinceEntity province){
		provinceService.save(province);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody ProvinceEntity province){
		provinceService.updateById(province);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		provinceService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}

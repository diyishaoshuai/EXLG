package com.xxzy.EXLG.controller;

import java.util.Arrays;
import java.util.Map;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xxzy.EXLG.entity.CountyEntity;
import com.xxzy.EXLG.service.CountyService;





/**
 * @author zhufeng
 */
@RestController
@RequestMapping("/county")
public class CountyController {
    @Autowired
    private CountyService countyService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions(":county:list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = countyService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions(":county:info")
    public R info(@PathVariable("id") Long id){
		CountyEntity county = countyService.getById(id);

        return R.ok().put("county", county);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions(":county:save")
    public R save(@RequestBody CountyEntity county){
		countyService.save(county);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions(":county:update")
    public R update(@RequestBody CountyEntity county){
		countyService.updateById(county);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions(":county:delete")
    public R delete(@RequestBody Long[] ids){
		countyService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

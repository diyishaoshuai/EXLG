package com.xxzy.EXLG.controller;

import java.util.Arrays;
import java.util.Map;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.xxzy.EXLG.entity.TrucktypeEntity;
import com.xxzy.EXLG.service.TrucktypeService;




/**
 * 
 *
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:18
 */
@RestController
@RequestMapping("/trucktype")
public class TruckTypeController {
    @Autowired
    private TrucktypeService trucktypeService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = trucktypeService.queryPage(params);
        return R.ok().put("page", page);
    }
    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		TrucktypeEntity trucktype = trucktypeService.getById(id);
        return R.ok().put("trucktype", trucktype);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TrucktypeEntity trucktype){
		trucktypeService.save(trucktype);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody TrucktypeEntity trucktype){
		trucktypeService.updateById(trucktype);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		trucktypeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

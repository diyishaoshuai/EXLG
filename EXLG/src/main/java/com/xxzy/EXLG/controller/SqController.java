package com.xxzy.EXLG.controller;

import java.util.Arrays;
import java.util.Map;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xxzy.EXLG.entity.SqEntity;
import com.xxzy.EXLG.service.SqService;




/**
 * 
 *
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:18
 */
@RestController
@RequestMapping("/sq")
public class SqController {
    @Autowired
    private SqService sqService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions(":sq:list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = sqService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions(":sq:info")
    public R info(@PathVariable("id") Long id){
		SqEntity sq = sqService.getById(id);

        return R.ok().put("sq", sq);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions(":sq:save")
    public R save(@RequestBody SqEntity sq){
		sqService.save(sq);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions(":sq:update")
    public R update(@RequestBody SqEntity sq){
		sqService.updateById(sq);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions(":sq:delete")
    public R delete(@RequestBody Long[] ids){
		sqService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

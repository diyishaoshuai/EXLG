package com.xxzy.EXLG.controller;

import java.util.Arrays;
import java.util.Map;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xxzy.EXLG.entity.StageEntity;
import com.xxzy.EXLG.service.StageService;




/**
 * 
 *
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:18
 */
@RestController
@RequestMapping("/stage")
public class StageController {
    @Autowired
    private StageService stageService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions(":stage:list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = stageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions(":stage:info")
    public R info(@PathVariable("id") Long id){
		StageEntity stage = stageService.getById(id);

        return R.ok().put("stage", stage);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions(":stage:save")
    public R save(@RequestBody StageEntity stage){
		stageService.save(stage);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions(":stage:update")
    public R update(@RequestBody StageEntity stage){
		stageService.updateById(stage);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions(":stage:delete")
    public R delete(@RequestBody Long[] ids){
		stageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

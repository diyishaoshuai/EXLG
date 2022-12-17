package com.xxzy.EXLG.controller;

import java.util.Arrays;
import java.util.Map;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xxzy.EXLG.entity.HqEntity;
import com.xxzy.EXLG.service.HqService;




/**
 * 
 *
 * @author jianghao
 */
@RestController
@RequestMapping("/hq")
public class HqController {
    @Autowired
    private HqService hqService;

    /**
     * 列表
     */
    @GetMapping("/list")
    //@RequiresPermissions(":hq:list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = hqService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    //@RequiresPermissions(":hq:info")
    public R info(@PathVariable("id") Long id){
		HqEntity hq = hqService.getById(id);

        return R.ok().put("hq", hq);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    //@RequiresPermissions(":hq:save")
    public R save(@RequestBody HqEntity hq){
		hqService.save(hq);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    //@RequiresPermissions(":hq:update")
    public R update(@RequestBody HqEntity hq){
		hqService.updateById(hq);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    //@RequiresPermissions(":hq:delete")
    public R delete(@RequestBody Long[] ids){
		hqService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

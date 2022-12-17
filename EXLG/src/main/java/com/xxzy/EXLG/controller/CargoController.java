package com.xxzy.EXLG.controller;

import java.util.Arrays;
import java.util.Map;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.entity.CargoEntity;
import com.xxzy.EXLG.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author jianghao
 */
@RestController
@RequestMapping("/cargo")
public class CargoController {
    @Autowired
    private CargoService cargoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = cargoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CargoEntity cargo = cargoService.getById(id);

        return R.ok().put("cargo", cargo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CargoEntity cargo){
		cargoService.save(cargo);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody CargoEntity cargo){
		cargoService.updateById(cargo);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		cargoService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}

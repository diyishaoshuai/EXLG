package com.xxzy.EXLG.controller;

import java.util.List;
import java.util.Map;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.vo.TruckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xxzy.EXLG.entity.TruckEntity;
import com.xxzy.EXLG.service.TruckService;




/**
 * 
 *
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:18
 */
@RestController
@RequestMapping("/truck")
public class TruckController {

    @Autowired
    private TruckService truckService;

    /**
     *  获取所有车辆且封装车辆能否转载该货物的信息  disabled
     * @param id   订单id
     * @return
     */
    @GetMapping("/getAllUsableTruck/{id}")
    public R getAllUsableTruck(@PathVariable("id") Long id){
        List<TruckVo> trucks = truckService.getAllUsableTruck(id);
        return R.ok().put("trucks",trucks);
    }

    @GetMapping("/sendCar/{id}")
    public R sendCar(@PathVariable("id") Long id){
        return truckService.sendCar(id);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = truckService.queryPage(params);
        return R.ok().put("page", page);
    }
    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		TruckEntity truck = truckService.getById(id);

        return R.ok().put("truck", truck);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody TruckVo truck){
		R r = truckService.saveOne(truck);
        return r;
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody TruckEntity truck){
		truckService.updateById(truck);
        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete/{id}")
    public R delete(@PathVariable("id") Long id){
		truckService.removeById(id);
        return R.ok();
    }

}

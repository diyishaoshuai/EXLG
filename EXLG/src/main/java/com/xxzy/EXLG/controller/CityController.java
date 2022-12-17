package com.xxzy.EXLG.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.entity.CountyEntity;
import com.xxzy.EXLG.service.CountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.xxzy.EXLG.entity.CityEntity;
import com.xxzy.EXLG.service.CityService;






/**
 * @author zhufeng
 */
@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private CityService cityService;

    @Autowired
    private CountyService countyService;

    /**
     * @author gjq0117
     * @email  gjq0117@163.com
     * @date 2022/4/24 下午 07:47
     *
     *  通过城市id查找所有城市下的县城
     */
    @GetMapping("/getCountysById/{id}")
    public R getCountysById(@PathVariable("id") Long id){
        List<CountyEntity> countys = countyService.getCountysById(id);
        return R.ok().put("countys",countys);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam(required = false) Map<String, Object> params){
        PageUtils page = cityService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CityEntity city = cityService.getById(id);
        return R.ok().put("city", city);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CityEntity city){
		cityService.save(city);

        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody CityEntity city){
		cityService.updateById(city);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		cityService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

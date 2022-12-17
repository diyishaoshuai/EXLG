package com.xxzy.EXLG.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.dao.StageDao;
import com.xxzy.EXLG.entity.AddressBookEntity;
import com.xxzy.EXLG.entity.OrderEntity;
import com.xxzy.EXLG.entity.StageEntity;
import com.xxzy.EXLG.service.OrderService;
import com.xxzy.EXLG.vo.ReceiveOrderVo;
import com.xxzy.EXLG.vo.AddOrderVo;
import com.xxzy.EXLG.vo.SendOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;




/**
 * 
 *
 * @author gjq0117
 * @email gjq0117@163.com
 * @date 2022-04-22 20:17:19
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private StageDao stageDao;

    /**
     *  添加订单信息
     * @return
     */
    @PostMapping("/add")
    public R addOrder(@RequestBody AddOrderVo addOrderVo){
        return orderService.addOrder(addOrderVo);
    }

    /**
     *  查询我寄的订单
     * @param userId
     * @return
     */
    @GetMapping("/getSendOrdersByUserId")
    public R getSendOrdersByUserId(Long userId){
        List<SendOrderVo> vos = orderService.getSendOrdersByUserId(userId);
        return R.ok().put("orders",vos);
    }

    /**
     *  查询我收到订单
     * @param userId
     * @return
     */
    @GetMapping("/getReceiveOrdersByUserId")
    public R getReceiveOrdersByUserId(Long userId){
        List<ReceiveOrderVo> vos = orderService.getReceiveOrdersByUserId(userId);
        return R.ok().put("orders",vos);
    }

    /**
     *  根据订单id签收订单
     * @param id
     * @return
     */
    @GetMapping("/signOrder/{id}")
    public R signOrder(@PathVariable("id") Long id){
        return orderService.signOrder(id);
    }

    /**
     *  处理订单装车
     *  TODO  装车之后需要改变订单状态为运输中
     * @param id
     * @param truckMark
     * @return
     */
    @GetMapping("/handleUpTruck")
    public R handleUpTruck(@RequestParam("id") Long id, @RequestParam("truckMark") String truckMark){
        return orderService.handleUpTruck(id,truckMark);
    }

    /**
     * 获取没有发货的所有订单
     * @gjq0117
     * @param params
     * @return
     */
    @GetMapping("/notShippedList")
    public R getNotShippedList(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.getNotShippedList(params);
        return R.ok().put("page",page);
    }

    /**
     * 通过关键词模糊查询
     * @gjq0117
     * @param params
     * @return
     */
    @GetMapping("/getOrderByKey")
    public R getOrderByKey(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.getOrderByKey(params);
        return R.ok().put("page",page);
    }

    /**
     * @author gjq0117
     * @email  gjq0117@163.com
     * @date 2022/4/30 下午 04:35
     * @describe  通过给定的状态信息查询出该状态下所有的订单
     */
    @GetMapping("/getOrderByStatus")
    public R getOrderByStatus(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.getOrderByStatus(params);
        return R.ok().put("page",page);
    }

    /**
     * 列表
     * 查询所有订单(除去未发货)
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		OrderEntity order = orderService.getById(id);
        return R.ok().put("order", order);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody OrderEntity order){
		orderService.save(order);
        return R.ok();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody OrderEntity order){
		orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		orderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

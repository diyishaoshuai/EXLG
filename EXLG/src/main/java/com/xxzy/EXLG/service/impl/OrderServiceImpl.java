package com.xxzy.EXLG.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxzy.EXLG.common.constant.OrderConstant;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.dao.*;
import com.xxzy.EXLG.entity.*;
import com.xxzy.EXLG.interceptor.LoginUserInterceptor;
import com.xxzy.EXLG.vo.AddOrderVo;
import com.xxzy.EXLG.vo.OrderVo;
import com.xxzy.EXLG.vo.ReceiveOrderVo;
import com.xxzy.EXLG.vo.SendOrderVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xxzy.EXLG.service.OrderService;
import org.springframework.transaction.annotation.Transactional;

import static com.xxzy.EXLG.common.constant.OrderConstant.OrderStatusEnum.*;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Autowired
    private UserDao userdao;

    @Autowired
    private StageDao stageDao;

    @Autowired
    private TruckDao truckDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private ProvinceDao provinceDao;

    @Autowired
    private CityDao cityDao;

    @Autowired
    private CountyDao countyDao;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ThreadPoolExecutor executor;


    /**
     * @author gjq0117
     * @email  gjq0117@163.com
     * @date 2022/4/30 下午 04:45
     * @describe  查出所有订单信息(除去未发货的)
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EmployeeEntity employeeEntity = LoginUserInterceptor.employeeInfo.get();
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>().notIn("status", NOT_SEND.getCode()).and(c -> {
                    c.eq("affiliation_id",employeeEntity.getId());
                })
        );
        //对查询出来的列表进行封装  即查出相应的省市县信息
        List<OrderVo> collect = page.getRecords().stream().map(item -> {
            OrderVo orderVo = new OrderVo();
            copyOrderEntity(orderVo,item);
            return orderVo;
        }).collect(Collectors.toList());
        return new PageUtils(collect,collect.size(),(int)page.getSize(),(int)page.getCurrent());
    }

    /**
     * @author gjq0117
     * @email  gjq0117@163.com
     * @date 2022/4/30 下午 04:43
     * @describe  通过订单状态查找相应的订单信息
     */
    @Override
    public PageUtils getOrderByStatus(Map<String, Object> params) {
        EmployeeEntity employeeEntity = LoginUserInterceptor.employeeInfo.get();
        IPage<OrderEntity> page = null;
        List<OrderVo> orders = null;
        if (params.get("status") != null){
            Integer status = Integer.parseInt((String) params.get("status"));
            page = this.page(
                    new Query<OrderEntity>().getPage(params),
                    new QueryWrapper<OrderEntity>().eq("status",status).and(c -> {
                        c.eq("affiliation_id",employeeEntity.getId());
                    })
            );
            orders = page.getRecords().stream().map(item -> {
                OrderVo orderVo = new OrderVo();
                copyOrderEntity(orderVo,item);
                return orderVo;
            }).collect(Collectors.toList());
        }
        return new PageUtils(orders,orders.size(),(int)page.getSize(),(int)page.getCurrent());
    }

    /**
     * @author gjq0117
     * @email  gjq0117@163.com
     * @date 2022/4/30 下午 04:44
     * @describe  通过关键词key 模糊查询相应的信息(除去未发货)
     */
    @Override
    public PageUtils getOrderByKey(Map<String, Object> params) {
        EmployeeEntity employeeEntity = LoginUserInterceptor.employeeInfo.get();
        IPage<OrderEntity> page = null;
        List<OrderVo> orders = null;
        if (params.get("key") != null && params.get("key") != ""){    //有关键词通过关键词查询
            String key = (String) params.get("key");
            page = this.page(
                    new Query<OrderEntity>().getPage(params),
                    new QueryWrapper<OrderEntity>()
                            .like("order_id", key).or()
                            .like("start_place", key).or()
                            .like("end_place", key)
                            .notIn("status", NOT_SEND.getCode())
                            .and(c -> {
                                c.eq("affiliation_id",employeeEntity.getId());
                            })
            );
            orders = page.getRecords().stream().map(item -> {
                OrderVo orderVo = new OrderVo();
                copyOrderEntity(orderVo, item);
                return orderVo;
            }).collect(Collectors.toList());
            return new PageUtils(orders,orders.size(),(int)page.getSize(),(int)page.getCurrent());
        }else{      //没有关键词直接查询所有
            return this.queryPage(params);
        }

    }

    /**
     * 获取所有没有发货的订单
     * @param params
     * @return
     */
    @Override
    public PageUtils getNotShippedList(Map<String, Object> params) {
        EmployeeEntity employeeEntity = LoginUserInterceptor.employeeInfo.get();
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                new QueryWrapper<OrderEntity>().eq("status",NOT_SEND.getCode()).and(c -> {
                    c.eq("affiliation_id",employeeEntity.getId());
                })
        );
        //对查询出来的列表进行封装  即查出相应的省市县信息
        List<OrderVo> collect = page.getRecords().stream().map(item -> {
            OrderVo orderVo = new OrderVo();
            copyOrderEntity(orderVo,item);
            return orderVo;
        }).collect(Collectors.toList());
        return new PageUtils(collect,collect.size(),(int)page.getSize(),(int)page.getCurrent());
    }

    /**
     *  处理订单装车
     * @param id  订单id
     * @param truckMark 车牌号
     * @return
     */
    @Transactional
    @Override
    public R handleUpTruck(Long id,String truckMark) {
        OrderEntity byId = this.getById(id);
        //1 检验数据是否合法
        if (id < 0 || byId == null){
            return R.error().put("msg","订单不存在");
        }
        //2 根据车牌号找到对应的车辆,将改车的剩余重量减去订单重量 改变车辆信息
        TruckEntity truck_mark = truckDao.selectOne(new QueryWrapper<TruckEntity>().eq("truck_mark", truckMark));
        truck_mark.setRemainderWeight(truck_mark.getRemainderWeight() - byId.getTotalWeigh());
        truckDao.updateById(truck_mark);
        //3 改变订单状态 已发货
        byId.setStatus(ARRIVE.getCode());
        //4 更新订单信息
        this.updateById(byId);
        return R.ok().put("msg","订单已发货");
    }

    @Override
    public R signOrder(Long id) {
        //1.  更新订单状态
        boolean update = this.update(new UpdateWrapper<OrderEntity>().eq("id",id).set("status",READY_SIGN.getCode()));
        //2.  发短信
        //  获取实体对象
        OrderEntity orderEntity = orderDao.selectById(id);

        // 给MQ中发消息
        rabbitTemplate.convertAndSend("order-event-exchange","order-msg-queue",orderEntity);
        return update? R.ok() : R.error();
    }

    @Override
    public List<SendOrderVo> getSendOrdersByUserId(Long userId) {
        List<SendOrderVo> send_user_id = orderDao.selectList(new QueryWrapper<OrderEntity>().eq("send_user_id", userId)).stream().map(item -> {
            SendOrderVo sendOrderVo = new SendOrderVo();
            sendOrderVo.setId(item.getOrderId());
            //  通过寄件人Id查找寄件人信息
            CompletableFuture<UserEntity> sendUserEntityCompletableFuture = CompletableFuture.supplyAsync(() -> userdao.selectById(item.getSendUserId()), executor);
            //  通过收件人Id查找收件人信息
            CompletableFuture<UserEntity> ReceiveUserEntityCompletableFuture = CompletableFuture.supplyAsync(() -> userdao.selectById(item.getReceiveUserId()), executor);
            //  通过寄件城市Id查找寄件城市信息
            CompletableFuture<CityEntity> SendCityEntityCompletableFuture = CompletableFuture.supplyAsync(() -> cityDao.selectById(item.getStartCityId()), executor);
            //  通过收件城市Id查找收件城市信息
            CompletableFuture<CityEntity> ReceiveCityEntityCompletableFuture = CompletableFuture.supplyAsync(() -> cityDao.selectById(item.getEndCityId()), executor);
            CompletableFuture.allOf(sendUserEntityCompletableFuture,ReceiveUserEntityCompletableFuture,SendCityEntityCompletableFuture,ReceiveCityEntityCompletableFuture);
            try {
                UserEntity sendUserEntity = sendUserEntityCompletableFuture.get();
                UserEntity receiveUserEntity = ReceiveUserEntityCompletableFuture.get();
                CityEntity sendCityEntity = SendCityEntityCompletableFuture.get();
                CityEntity receiveCityEntity = ReceiveCityEntityCompletableFuture.get();
                if(sendCityEntity!=null){
                    sendOrderVo.setSend(sendUserEntity.getNickName());
                }
                if(receiveUserEntity!=null){
                    sendOrderVo.setReceive(receiveUserEntity.getNickName());
                }
               if(sendCityEntity!=null){
                   sendOrderVo.setDeparture(sendCityEntity.getName());
               }
               if(receiveCityEntity!=null){
                   sendOrderVo.setDestination(receiveCityEntity.getName());
               }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return sendOrderVo;
        }).collect(Collectors.toList());
        return send_user_id;
    }

    @Override
    public List<ReceiveOrderVo> getReceiveOrdersByUserId(Long userId) {
        List<ReceiveOrderVo> receiveUser = orderDao.selectList(new QueryWrapper<OrderEntity>().eq("receive_user_id", userId)).stream().map(item -> {
            ReceiveOrderVo receiveOrderVo = new ReceiveOrderVo();
            receiveOrderVo.setId(item.getOrderId());
            //  通过寄件人Id查找寄件人信息
            CompletableFuture<UserEntity> sendUserEntityCompletableFuture = CompletableFuture.supplyAsync(() -> userdao.selectById(item.getSendUserId()), executor);
            //  通过收件人Id查找收件人信息
            CompletableFuture<UserEntity> ReceiveUserEntityCompletableFuture = CompletableFuture.supplyAsync(() -> userdao.selectById(item.getReceiveUserId()), executor);
            //  通过寄件城市Id查找寄件城市信息
            CompletableFuture<CityEntity> SendCityEntityCompletableFuture = CompletableFuture.supplyAsync(() -> cityDao.selectById(item.getStartCityId()), executor);
            //  通过收件城市Id查找收件城市信息
            CompletableFuture<CityEntity> ReceiveCityEntityCompletableFuture = CompletableFuture.supplyAsync(() -> cityDao.selectById(item.getEndCityId()), executor);
            CompletableFuture.allOf(sendUserEntityCompletableFuture,ReceiveUserEntityCompletableFuture,SendCityEntityCompletableFuture,ReceiveCityEntityCompletableFuture);
            try {
                UserEntity sendUserEntity = sendUserEntityCompletableFuture.get();
                UserEntity receiveUserEntity = ReceiveUserEntityCompletableFuture.get();
                CityEntity sendCityEntity = SendCityEntityCompletableFuture.get();
                CityEntity receiveCityEntity = ReceiveCityEntityCompletableFuture.get();
                receiveOrderVo.setSend(sendUserEntity.getNickName());
                receiveOrderVo.setReceive(receiveUserEntity.getNickName());
                receiveOrderVo.setDeparture(sendCityEntity.getName());
                receiveOrderVo.setDestination(receiveCityEntity.getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return receiveOrderVo;
        }).collect(Collectors.toList());
        return receiveUser;
    }

    @Transactional
    @Override
    public R addOrder(AddOrderVo addOrderVo) {
        OrderEntity orderEntity = new OrderEntity();
        AddressBookEntity send = addOrderVo.getSend();  // 寄件人信息
        AddressBookEntity receive = addOrderVo.getReceive(); // 收件人信息
        // 1. 随机生产订单order_id
        orderEntity.setOrderId(this.productOrderId());
        // 2. 保存寄件人信息以及开始地址信息 send_user_id send_user_tel  start_province_id  start_city_id  start_county_id  start_stage_id  start_place
        this.saveSendUser(orderEntity,send);
        // 3. 保存收件人信息以及结束地址信息 receive_user_id receive_user_tel end_province_id end_city_id end_county_id end_stage_id end_place
        this.saveReceiveUser(orderEntity,receive);
        // 4. 保存所属信息 affiliation_id ==> 员工id
        EmployeeEntity employeeEntity = employeeDao.selectOne(new QueryWrapper<EmployeeEntity>().eq("province_id", send.getProvinceId()).and(e -> {
            e.eq("city_id", send.getCityId()).and((c) -> {
                c.eq("county_id", send.getCountyId());
            });
        }));
        orderEntity.setAffiliation_id(employeeEntity.getId());
        // 6. 保存sq_id

        // 7. 设置订单状态 status 默认0
        orderEntity.setStatus(OrderConstant.OrderStatusEnum.NOT_SEND.getCode());
        // 8. 设置总价格 total_price  path_length  total_weigh
        Double weight = Double.parseDouble(addOrderVo.getObjectMsg().substring(0,addOrderVo.getObjectMsg().length() - 2));
        orderEntity.setTotalWeigh(weight);
        orderEntity.setTotalPrice(new BigDecimal(weight * 5));
        orderDao.insert(orderEntity);
        return R.ok();
    }

    /**
     *  生产订单id的方法
     * @return
     */
    private String productOrderId(){
        Date date = new Date();
        long time = date.getTime();
        return ""+time;
    }
    /**
     *  保存寄件人信息
     */
    private void saveSendUser(OrderEntity orderEntity,AddressBookEntity send){
        orderEntity.setSendUserId(send.getUserId());  // send_user_id
        orderEntity.setSendUserTel(send.getSendTel());// send_user_tel
        orderEntity.setStartProvinceId(send.getProvinceId()); //start_province_id
        orderEntity.setStartCityId(send.getCityId()); // start_city_id
        orderEntity.setStartCountyId(send.getCountyId());  // start_county_id
        orderEntity.setStartPlace(send.getArea()+send.getAddress());  // start_place
        // 通过start_county_id或start_city_id 设置start_stage_id
        if (send.getCountyId() != null){
            StageEntity stageEntity = stageDao.selectOne(new QueryWrapper<StageEntity>().eq("county_id", send.getCountyId()));
            orderEntity.setStartStageId(stageEntity.getId());
        }else{
            StageEntity stageEntity = stageDao.selectOne(new QueryWrapper<StageEntity>().eq("city_id", send.getCityId()));
            orderEntity.setStartStageId(stageEntity.getId());
        }
    }
    private void saveReceiveUser(OrderEntity orderEntity,AddressBookEntity receive){
        orderEntity.setReceiveUserId(receive.getUserId());  // receive_user_id
        orderEntity.setReceiveUserTel(receive.getSendTel());  //receive_user_tel
        orderEntity.setEndProvinceId(receive.getProvinceId());  // end_province_id
        orderEntity.setEndCityId(receive.getCityId());  // end_city_id
        orderEntity.setEndCountyId(receive.getCountyId());  // end_county_id
        orderEntity.setEndPlace(receive.getArea()+receive.getAddress());  // end_place
        // 通过end_county_id或end_city_id 设置end_stage_id
        if (receive.getCountyId() != null){
            StageEntity stageEntity = stageDao.selectOne(new QueryWrapper<StageEntity>().eq("county_id", receive.getCountyId()));
            orderEntity.setEndStageId(stageEntity.getId());
        }else{
            StageEntity stageEntity = stageDao.selectOne(new QueryWrapper<StageEntity>().eq("city_id", receive.getCityId()));
            orderEntity.setStartStageId(stageEntity.getId());
        }
    }

    /**
     * @author gjq0117
     * @email  gjq0117@163.com
     * @date 2022/4/30 下午 04:10
     * @describe   订单实体转vo
     */
    private void copyOrderEntity(OrderVo vo, OrderEntity entity){
        BeanUtils.copyProperties(entity, vo);   //属性对口
        //继续查出不能对口的属性
        //1、通过寄货人id找到寄货人姓名 sendUserId
        UserEntity sendUser = userdao.selectById(entity.getSendUserId());
        vo.setSendName(sendUser.getNickName());
        //2、通过寄货站点的id找到寄货站点的名字
        StageEntity stageEntity = stageDao.selectById(entity.getStartStageId());
        vo.setSendStage(stageEntity.getName());
        //3、通过收货人的id找到收货人的姓名
        UserEntity receiveUser = userdao.selectById(entity.getReceiveUserId());
        vo.setReceiveName(receiveUser.getNickName());
        //4、订单状态关系对应
        Integer status = entity.getStatus();
        if (NOT_SEND.getCode().equals(status)){
            vo.setStatus(NOT_SEND.getMsg());
        }else if(READY_SEND.getCode().equals(status)){
            vo.setStatus(READY_SEND.getMsg());
        }else if(IN_TRANSIT.getCode().equals(status)){
            vo.setStatus(IN_TRANSIT.getMsg());
        }else if(ARRIVE.getCode().equals(status)){
            vo.setStatus(ARRIVE.getMsg());
        }else if(READY_SIGN.getCode().equals(status)){
            vo.setStatus(READY_SIGN.getMsg());
        }else{
            vo.setStatus("未知");
        }
    }

}
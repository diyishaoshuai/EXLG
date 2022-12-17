package com.xxzy.EXLG.service.impl;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.dao.*;
import com.xxzy.EXLG.entity.*;
import com.xxzy.EXLG.interceptor.LoginUserInterceptor;
import com.xxzy.EXLG.vo.TruckVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxzy.EXLG.service.TruckService;

import javax.annotation.Resource;

import static com.xxzy.EXLG.common.constant.TruckConstant.TruckStatusEnum.AWAIT;
import static com.xxzy.EXLG.common.constant.TruckConstant.TruckStatusEnum.TRANSPORT;


@Service("truckService")
public class TruckServiceImpl extends ServiceImpl<TruckDao, TruckEntity> implements TruckService {

    @Resource
    private TrucktypeDao trucktypeDao;

    @Resource
    private TruckDao truckDao;

    @Resource
    private StageDao stageDao;

    @Resource
    private SqDao sqDao;

    @Resource
    private OrderDao orderDao;

    /**
     * 查询本站点所有的车辆信息
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        EmployeeEntity employeeEntity = LoginUserInterceptor.employeeInfo.get();
        IPage<TruckEntity> page = this.page(
                new Query<TruckEntity>().getPage(params),
                new QueryWrapper<TruckEntity>().eq("employee_id", employeeEntity.getId())    //查询属于这个员工的车辆
        );
        List<TruckVo> collect = page.getRecords().stream().map(item -> {
            TruckVo truckVo = new TruckVo();
            //1 通过分类id找到分类的名字
            TrucktypeEntity type_id = trucktypeDao.selectOne(new QueryWrapper<TrucktypeEntity>().eq("id", item.getTypeId()));
            truckVo.setTypeName(type_id.getTypeName());
            //基本属性对口
            BeanUtils.copyProperties(item, truckVo);
            //2 分装状态信息
            if (item.getStatus() == AWAIT.getCode()) {   //等待状态
                truckVo.setStatus(AWAIT.getMsg());
            } else if (item.getStatus() == TRANSPORT.getCode()) {  //运输状态
                truckVo.setStatus(TRANSPORT.getMsg());
            }
            return truckVo;
        }).collect(Collectors.toList());
        return new PageUtils(collect, collect.size(), (int) page.getSize(), (int) page.getCurrent());
    }

    @Override
    public R saveOne(TruckVo truck) {
        EmployeeEntity employeeEntity = LoginUserInterceptor.employeeInfo.get();
        TruckEntity truckEntity = new TruckEntity();
        //1.基本属性对口
        BeanUtils.copyProperties(truck,truckEntity);
        //2.通过类型名找到类型id,设置type_id
        TrucktypeEntity type_name = trucktypeDao.selectOne(new QueryWrapper<TrucktypeEntity>().eq("type_name", truck.getTypeName()));
        if (type_name == null){
            return R.error().put("msg","车辆类型不存在");
        }
        truckEntity.setTypeId(type_name.getId());
        //3.设置状态为待机 0
        truckEntity.setStatus(0);
        //4.设置affiliation_id  设置所属id,即设置站点或子公司id
        //4.1 通过员工信息找到员工的affiliation_type来判断是属于子公司还是属于站点人员
        Integer affiliationType = employeeEntity.getAffiliationType();
        //4.2 确定是子公司或站点人员之后再根据员工的affiliation_id来找子公司或站点信息
        if (affiliationType == 1){   //配送点
            StageEntity stageEntity = stageDao.selectOne(new QueryWrapper<StageEntity>().eq("id", employeeEntity.getAffiliationId()));
            if (stageEntity == null){
                return R.error();
            }
            //4.3 设置affiliation_id
            truckEntity.setAffiliationId(stageEntity.getId());
        }else if(affiliationType == 2){  //子公司
            SqEntity sqEntity = sqDao.selectOne(new QueryWrapper<SqEntity>().eq("id", employeeEntity.getAffiliationId()));
            if (sqEntity == null){
                return R.error();
            }
            //4.3 设置affiliation_id
            truckEntity.setAffiliationId(sqEntity.getId());
        }else{
            return R.error();
        }
        //5.设置员工Id
        truckEntity.setEmployeeId(employeeEntity.getId());
        //6.设置剩余重量默认为最大重量
        truckEntity.setRemainderWeight(truck.getMaxWeight());
        this.save(truckEntity);
        return R.ok();
    }

    @Override
    public R sendCar(Long id) {
        //改变车辆状态
        this.update(new UpdateWrapper<TruckEntity>().eq("id",id).set("status",TRANSPORT.getCode()));
        return R.ok();
    }

    @Override
    public List<TruckVo> getAllUsableTruck(Long id) {
        //1 根据订单id查找出该订单的详细信息
        OrderEntity orderEntity = orderDao.selectById(id);
        //2  获取所有车辆 遍历每个车辆,设置disable属性  如果当前订单的重量大于该车剩余重量=>disable=false   反之disable = true
        List<TruckVo> collect = this.list().stream().map(truck -> {
            TruckVo truckVo = new TruckVo();
            //1 基本属性对口
            BeanUtils.copyProperties(truck,truckVo);
            //2 通过typeId找出typeName
            truckVo.setTypeName(trucktypeDao.selectById(truck.getTypeId()).getTypeName());
            //3 设置status属性映射关系  0:待机 1:运输
            Integer status = truck.getStatus();
            truckVo.setStatus(status == AWAIT.getCode() ? AWAIT.getMsg() : status == TRANSPORT.getCode() ? TRANSPORT.getMsg() : "故障");
            //4 根据remainderWeight 与 订单重量的大小关系以及status的值设置disable的值
            boolean isLoaded = truck.getRemainderWeight() >= orderEntity.getTotalWeigh();  //可装载?  判断可装载重量是否大于订单重量  true: 可  false： 不可
            truckVo.setDisabled(!(isLoaded && status == AWAIT.getCode()));
            return truckVo;
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     *  查找该站点拥有的所有车辆
     * @return
     */
    public List<TruckEntity> list(){
        EmployeeEntity employeeEntity = LoginUserInterceptor.employeeInfo.get();
        return truckDao.selectList(new QueryWrapper<TruckEntity>().eq("employee_id", employeeEntity.getId()));
    }
}
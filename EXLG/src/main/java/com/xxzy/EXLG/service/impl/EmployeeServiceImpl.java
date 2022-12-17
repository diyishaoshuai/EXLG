package com.xxzy.EXLG.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxzy.EXLG.common.constant.UserConstant;
import com.xxzy.EXLG.common.utils.PageUtils;
import com.xxzy.EXLG.common.utils.Query;
import com.xxzy.EXLG.common.utils.R;
import com.xxzy.EXLG.dao.EmployeeDao;
import com.xxzy.EXLG.dao.SqDao;
import com.xxzy.EXLG.dao.StageDao;
import com.xxzy.EXLG.entity.*;
import com.xxzy.EXLG.service.*;
import com.xxzy.EXLG.vo.EmpLoginVo;
import com.xxzy.EXLG.vo.RegStageOrSQVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.http.HttpSession;


@Service("employeeService")
@CrossOrigin
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, EmployeeEntity> implements EmployeeService {

    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private SqService sqService;

    @Autowired
    private SqDao sqDao;

    @Autowired
    private CountyService countyService;

    @Autowired
    private StageDao stageDao;

    @Autowired
    private EmployeeDao employeeDao;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<EmployeeEntity> page = this.page(
                new Query<EmployeeEntity>().getPage(params),
                new QueryWrapper<EmployeeEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional     //涉及多表操作,必须同时成功或失败
    public R regStageOrSQ(RegStageOrSQVo regStageOrSQ) {
        //1.判断用户名或电话号码是否存在
        EmployeeEntity one = employeeDao.selectOne(new QueryWrapper<EmployeeEntity>().eq("username", regStageOrSQ.getUsername())
                .or().eq("telephone", regStageOrSQ.getTelephone()));
        if (one != null) {    //用户已名或手机号存在
            return R.error().put("error", "用户名或手机号已存在");
        }
        //找到该省是否已经有子公司或县是否已经有站点(每个省只有一个子公司,每个县只有一个站点)   如果有,直接返回false
        String provinceName = regStageOrSQ.getProvince();       //省
        String cityName = regStageOrSQ.getCity();               //市
        String countyName = regStageOrSQ.getCounty();           //县
        //通过名字找到所有省市县信息

        /**
         *   开启异步编排
         */
        CompletableFuture<ProvinceEntity> ProvinceFuture = CompletableFuture.supplyAsync(() -> {
            //  找省
            ProvinceEntity province = provinceService.getOne(new QueryWrapper<ProvinceEntity>().eq("name", provinceName));
            return province;
        }, executor);

        //  找市
        CompletableFuture<CityEntity> cityFuture = CompletableFuture.supplyAsync(() -> {
            CityEntity city = cityService.getOne(new QueryWrapper<CityEntity>().eq("name", cityName));
            return city;
        }, executor);

        CompletableFuture<CountyEntity> countyFuture = CompletableFuture.supplyAsync(() -> {
            //  找县
            CountyEntity county = null;
            //判断有没有县城,有就查询
            if (countyName != null) {
                county = countyService.getOne(new QueryWrapper<CountyEntity>().eq("name", countyName));
            }
            return county;
        }, executor);
        // 组合完成
        CompletableFuture.allOf(ProvinceFuture,cityFuture,countyFuture);
        ProvinceEntity province = null;
        CityEntity city= null;
        CountyEntity county = null;
        try {
            province = ProvinceFuture.get();
            city = cityFuture.get();
            county = countyFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 查找该省有没有子公司
        long provinceCount = sqService.count(new QueryWrapper<SqEntity>().eq("province_id", province.getId()));
        //1、判断注册类型  1:子公司  2: 站点
        if (regStageOrSQ.getRegType() == 1) {   //注册子公司
            //  查找该省存不存在子公司
            //通过省份id查找该省是否存在子公司
            if (provinceCount == 0) {   //可以注册子公司
                //3、注册子公司信息
                SqEntity sqEntity = new SqEntity();
                sqEntity.setProvinceId(province.getId());    //设置省份id
                sqEntity.setName(provinceName + "分公司");    //设置公司名字  暂时默认值,之后可以通过个人中心修改
                sqEntity.setTelephone(regStageOrSQ.getTelephone());   //设置公司电话
                sqEntity.setLocation(provinceName + cityName + countyName);   //默认位置
                sqDao.insert(sqEntity);   //自增id会填充到对象中去
                //4、注册员工信息,同时绑定子公司或站点信息
                EmployeeEntity employeeEntity = new EmployeeEntity();
                employeeEntity.setUsername(regStageOrSQ.getUsername());   // 设置用户名
                //密码要进行加密存储
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encode = passwordEncoder.encode(regStageOrSQ.getPassword());
                employeeEntity.setPassword(encode);                    //设置密码
                employeeEntity.setName(regStageOrSQ.getUsername());           //设置员工姓名   暂时默认就是县级名,之后可以在个人中心修改
                employeeEntity.setTelephone(regStageOrSQ.getTelephone());  //设置站点电话
                employeeEntity.setProvinceId(province.getId());     //设置省份id
                employeeEntity.setCityId(city.getId());
                if (county != null) {
                    employeeEntity.setCountyId(county.getId());
                }
                employeeEntity.setAddress(provinceName + cityName + countyName);
                employeeEntity.setJob(0);     //0: 负责人(可对所有数据增删改查)  1: 普通员工  2: 司机
                employeeEntity.setAffiliationType(2);  //1: 配送点人员    2: 子公司人员
                employeeEntity.setAffiliationId(sqEntity.getId());    //  sqEntity.getId()  可以取到id是因为mybatisPlus封装了
                employeeDao.insert(employeeEntity);
                return R.ok();
            } else {
                return R.error().put("error", "该省份以存在子公司");
            }
        } else {     //注册县级站点
            //判断该省有没有子公司,如果该省没有子公司, 就不能创建站点
            if (provinceCount == 0) {   //没有子公司不能注册
                return R.error().put("error", "该省还没有子公司,不能直接创建站点");
            }
            //通过省县市名查找市id
            Long count = stageDao.existStage(province.getId(), city.getId(), county.getId());
            if (count == 0) {  //可以注册
                //3、注册站点信息
                //通过省份id找到该省下的子公司
                SqEntity sqEntity = sqService.getOne(new QueryWrapper<SqEntity>().eq("province_id", province.getId()));
                StageEntity stageEntity = new StageEntity();
                stageEntity.setName(countyName + "站点");    //设置站点名
                stageEntity.setSqId(sqEntity.getId());      //设置子公司id
                stageEntity.setTelephone(regStageOrSQ.getTelephone());  //设置站点联系电话
                stageEntity.setLocation(provinceName + cityName + countyName);   //设置站点地址
                stageEntity.setProvinceId(province.getId());// 设置省id
                stageEntity.setCityId(city.getId());        // 设置市id
                stageEntity.setCountyId(county.getId());    //设置县id
                stageDao.insert(stageEntity);
                //4、注册员工信息,同时绑定子公司或站点信息
                EmployeeEntity employeeEntity = new EmployeeEntity();
                employeeEntity.setUsername(regStageOrSQ.getUsername());   // 设置用户名
                //密码要进行加密存储
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encode = passwordEncoder.encode(regStageOrSQ.getPassword());
                employeeEntity.setPassword(encode);                    //设置密码
                employeeEntity.setName(regStageOrSQ.getUsername());           //设置员工姓名
                employeeEntity.setTelephone(regStageOrSQ.getTelephone());  //设置站点电话
                employeeEntity.setProvinceId(province.getId());     //设置省份id
                employeeEntity.setCityId(city.getId());
                if (county != null) {
                    employeeEntity.setCountyId(county.getId());
                }
                employeeEntity.setAddress(provinceName + cityName + countyName);
                employeeEntity.setJob(0);     //0: 负责人(可对所有数据增删改查)  1: 普通员工  2: 司机
                employeeEntity.setAffiliationType(1);  //1: 配送点人员    2: 子公司人员
                employeeEntity.setAffiliationId(stageEntity.getId());    //  sqEntity.getId()  可以取到id是因为mybatisPlus封装了
                employeeDao.insert(employeeEntity);
                return R.ok();
            } else {
                return R.error().put("error", "该地区已经存在一个站点,不能重复创建");
            }
        }


    }

    /**
     * 员工登录
     *
     * @param empLoginVo
     * @return
     */
    @Override
    public EmployeeEntity empLogin(EmpLoginVo empLoginVo) {
        //1、通过LoginInfo找出员工信息  用户名或者密码
        EmployeeEntity entity = this.getOne(new QueryWrapper<EmployeeEntity>().eq("username", empLoginVo.getLoginInfo()).or().eq("telephone", empLoginVo.getLoginInfo()));
        if (entity != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean matches = passwordEncoder.matches(empLoginVo.getPassword(), entity.getPassword());   //进行密码匹配
            return matches ? entity : null;
        }
        return null;
    }

    @Override
    public R basicUpdateById(EmployeeEntity employee) {
        //判断修改的电话是否已经存在
        //1. 获取需要修改的电话信息
        String tel = employee.getTelephone();
        //2. 去数据库中查询是否存在此手机号
        EmployeeEntity telephone = employeeDao.selectOne(new QueryWrapper<EmployeeEntity>().eq("telephone", tel));
        if (telephone != null) {  // 存在至少一个, 判断这个员工是否是自己,如果不是自己, 就直接报错
            if (telephone.getId() == employee.getId()) {   //是自己,就执行更新操作
                this.updateById(employee);
                return R.ok();
            }
            //不是自己,就返回错误信息
            return R.error().put("error", "电话号码已存在");
        }
        //数据库中没有这个电话号码  去数据库更新
        this.updateById(employee);
        return R.ok();

    }

    @Override
    public PageUtils queryCompanyOrStageAllEmp(Map<String, Object> params, Long affiliationId, Integer affiliationType) {
        IPage<EmployeeEntity> page = new Page<>();
        QueryWrapper<EmployeeEntity> wrapper = new QueryWrapper<EmployeeEntity>();
        wrapper.and(QueryWrapper -> QueryWrapper
                .eq("affiliation_id", affiliationId)
                .eq("affiliation_type", affiliationType));


        if (params.get("key") != null && params.get("key") != "") {
            String key = (String) params.get("key");
            wrapper.and(QueryWrapper -> QueryWrapper
                    .like("name", key).or()
                    .like("age", key).or()
                    .like("telephone", key).or()
                    .like("address", key).or());

        }

        if (params.get("job") != null && params.get("job") != "") {
            String job = (String) params.get("job");
            wrapper.and(QueryWrapper -> QueryWrapper
                    .eq("job", job));

        }

        List<EmployeeEntity> employeeEntities = baseMapper.selectList(wrapper);
        int totalSize = employeeEntities.size();
        page.setRecords(employeeEntities);
        page.setCurrent(1L);
        page.setTotal(totalSize);
        page.setPages(totalSize <= 10 ? 1 : totalSize / 10 + 1);
        return new PageUtils(page);
    }

    @Override
    public void saveEmpByOne(EmployeeEntity employee, HttpSession session) {
        EmployeeEntity employeeEntity = (EmployeeEntity) session.getAttribute(UserConstant.LOGIN_USER);
        employee.setProvinceId(employeeEntity.getProvinceId());
        employee.setCityId(employeeEntity.getCityId());
        employee.setCountyId(employeeEntity.getCountyId());
        employee.setAddress(employeeEntity.getAddress());
        employee.setAffiliationId(employeeEntity.getAffiliationId());
        employee.setAffiliationType(employeeEntity.getAffiliationType());
        baseMapper.insert(employee);
    }


}
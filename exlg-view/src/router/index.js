// 导入组件
import Vue from 'vue';
import Router from 'vue-router';
// 登录
import login from '@/views/login';
//注册
import regist from '@/views/regist'
// 首页
import index from '@/views/index';
/**
 * 基础菜单
 */
// 商品管理
import truck from '@/views/basic/truck';


// 员工管理
import employee from '@/views/basic/employee';

// import MP from '@/views/map';

// 商品详情
import orderDetailPage from '@/views/orderDetailPage';

// 已经发货的订单
import shippedOrder from '@/views/order/shippedOrder';

// 已经发货的订单
import notshippedOrder from '@/views/order/notshippedOrder';


// 图表界面
import statistics from '@/views/charts/statistics';

//个人中心界面
import person from '@/views/basic/person'


const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

// 启用路由
Vue.use(Router);


// 导出路由 
export default new Router({
  routes: [{
    path: '/',
    name: '',
    component: login,
    hidden: true,
    meta: {
      requireAuth: false
    }
  }, {
    path: '/login',
    name: '登录',
    component: login,
    hidden: true,
    meta: {
      requireAuth: false
    }
  }, {
    path: '/regist',
    name: '注册',
    component: regist,
    hidden: true,
  }, {
    path: '/index',
    name: '首页',
    component: index,
    iconCls: 'el-icon-tickets',
    children: [{
        path: '/basics/truck',
        name: '车辆管理',
        component: truck,
        meta: {
          requireAuth: true
        }
      },
      {
        path: '/basics/employee',
        name: '员工管理',
        component: employee,
        meta: {
          requireAuth: true
        }
      },
      {
        path: '/order/shippedOrder',
        name: '已发货',
        component: shippedOrder,
        meta: {
          requireAuth: true
        }
      },
      {
        path: '/order/notshippedOrder',
        name: '未发货',
        component: notshippedOrder,
        meta: {
          requireAuth: true
        }
      }, {
        path: '/order/shippedOrder/orderDetailPage',
        name: '订单详情',
        component: orderDetailPage,
        meta: {
          requireAuth: true
        },
        props($route) {
          return {
            startPlace: $route.query.startPlace,
            endPlace: $route.query.endPlace
          }
        }
      }, {
        path: '/charts/statistics',
        name: '数据可视化',
        component: statistics,
        meta: {
          requireAuth: true
        }
      },
      {
        path: '/person/update',
        name: '个人中心',
        component: person,
        meta: {
          requireAuth: true
        }
      }
    ]
  }]
})

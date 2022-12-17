/**
 *    全局 axios请求
 */
import axios from 'axios';

import router from '../router/index'
import store from '../vuex/store'

let base = 'http://localhost:8888'
// var _this = this
// console.log(this)

// 登录请求方法
const loginreq = (method, url, data) => {
  return axios({
    method: method,
    url: `${base}${url}`,
    data: data
  }).then(res => {
    //判断是否登录成功
    if (res.data.code === 0) { //登录成功
      //路由跳转
      // store.state.loginErrorMsg = ''
      router.push({
        path: '/goods/Goods'
      })
    } else { //登录失败  返回到登录界面  引导用户重新登
      //改变登录状态
      store.state.logining = false,
        // store.state.loginErrorMsg = res.data.msg
        router.push({
          path: '/login',
        })
    }
  });
};
// const getProvice = (method, url, params) =>{
//   return axios({
//     method: method,
//     url: `${base}${url}`
//   })
// }
// 通用公用方法
const req = (method, url, params) => {
  return axios({
    method: method,
    url: url,
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
      token: localStorage.getItem('logintoken')
    },
    data: params,
    traditional: true,
    transformRequest: [
      function (data) {
        let ret = ''
        for (let it in data) {
          ret +=
            encodeURIComponent(it) +
            '=' +
            encodeURIComponent(data[it]) +
            '&'
        }
        return ret
      }
    ]
  }).then(res => res.data);
};

export {
  loginreq,
  req
}

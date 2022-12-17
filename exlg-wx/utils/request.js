import config from './config'
export default  (url, data={}, method='GET') => {
    return new Promise((resolve, reject) => {
      // 1. new Promise初始化promise实例的状态为pending
      wx.request({
        url: config.host + url,
        data,
        method,
        header:{
            'cookie': 'exlg-wx'   //标志是小程序发来的请求, 可以绕过后端拦截器
        },
        success: (res) => {
          resolve(res.data); // resolve修改promise的状态为成功状态resolved
        },
        fail: (err) => {
          reject(err); // reject修改promise的状态为失败状态 rejected
        }
      })
    })
  }
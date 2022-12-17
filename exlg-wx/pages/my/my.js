var app = getApp()
Page({
    data: {
        userInfo: {},   // 用户信息
    },
    onLoad(){
        const userInfo = JSON.parse(wx.getStorageSync('userInfo'));
        console.log(userInfo)
        this.setData({
            userInfo
        })
    },

    // 跳转登陆界面
    toLogin(){
        wx.navigateTo({
          url: '/pages/login/login',
        })
    },

    jumpTo: function () {
        wx.navigateToMiniProgram({
            appId: 'wxab056e746467b13f', //appid
            path: '', //path
            extraData: { //参数
                foo: 'bar'
            },
            envVersion: 'release', //开发版develop 开发版 trial   体验版 release 正式版 
            success(res) {
                console.log('成功')
                // 打开成功
            }
        })
    },

    //地址簿交互
    interactBtn: function () {
        app.globalData.interact = false
    }

})
var app = getApp()
import request from '../../utils/request'
Page({
    data: {
        // 导航颜色切换
        daijichuColor: "#43DBD1",
        wojideColor: "black",
        woshoudeColor: "black",

        //下划线
        daijichuLine: " 4rpx solid",
        wojideLine: "none",
        woshoudeLine: "none",

        //搜索id
        searchId: '',

        //默认情况
        daijichuView: false,
        wojideView: true,
        woshoudeView: true,

        //复制内容
        copyContent: '',

        //订单信息
        wojide: [],
        woshoude:[]
    },

    // 生命周期函数
    // onShow: function () {
    //     this.setData({
    //         daijichu: app.globalData.daijichu,
    //         wojide:app.globalData.wojide,
    //         woshoude:app.globalData.woshoude
    //     })
    // },
    onLoad() {
        const userInfo = JSON.parse(wx.getStorageSync('userInfo'))
         this.getwojide(userInfo.id)
         this.getwoshoude(userInfo.id)
        const woshoude = this.getwoshoude(userInfo.id)
        this.setData({
            daijichu: app.globalData.daijichu,
            // wojide,
            // woshoude
        })
    },
    async getwojide(userId) {
        const result = await request('/order/getSendOrdersByUserId',{userId})
        if(result.code === 0){
            this.setData({
                wojide: result.orders
            })
        }
        console.log(result)
    },
    async getwoshoude(userId){
       const result = await request('/order/getReceiveOrdersByUserId',{userId})
       if(result.code===0){
           this.setData({
            woshoude:result.orders
           })
       }
       console.log(result)
    },

    //搜索功能的实现
    //按钮
    // searchBtn:function(){
    //     var that=this
    //     this.setData({
    //     })
    // },

    idInput(e) {
        console.log('订单号：', e.detail.value)
        this.setData({
            searchId: e.detail.value
        })
    },



    selectDaijichu: function () {
        this.setData({
            daijichuView: false,
            wojideView: true,
            woshoudeView: true,
            daijichuColor: "#43DBD1",
            wojideColor: "black",
            woshoudeColor: "black",
            daijichuLine: " 4rpx solid",
            wojideLine: "none",
            woshoudeLine: "none"
        })
    },

    selectWojide: function () {
        this.setData({
            daijichuView: true,
            wojideView: false,
            woshoudeView: true,
            daijichuColor: "black",
            wojideColor: "#43DBD1",
            woshoudeColor: "black",
            daijichuLine: "none",
            wojideLine: " 4rpx solid",
            woshoudeLine: "none"
        })
    },

    selectWoshoude: function () {
        this.setData({
            daijichuView: true,
            wojideView: true,
            woshoudeView: false,
            daijichuColor: "black",
            wojideColor: "black",
            woshoudeColor: "#43DBD1",
            daijichuLine: "none",
            wojideLine: "none",
            woshoudeLine: "4rpx solid"
        })
    },

    //复制功能
    copyId: function (e) {
        this.setData({
            copyContent: e.currentTarget.dataset.id
        })
        wx.showToast({
            title: '复制成功',
        })
        wx.setClipboardData({
            data: this.data.copyContent,
            success() {
                wx.showToast({
                    title: '复制成功',
                    icon: 'success',
                    duration: 1000
                })
            }

        })
        
    },

    // 卡片点击交互
    enterMapBtn1: function (e) {
        app.globalData.selectId = e.currentTarget.dataset.index
        app.globalData.daijichuFlag = true
        app.globalData.wojideFlag = false
        app.globalData.woshoudeFlag = false
    },
    enterMapBtn2: function (e) {
        app.globalData.selectId = e.currentTarget.dataset.index
        app.globalData.daijichuFlag = false
        app.globalData.wojideFlag = true
        app.globalData.woshoudeFlag = false
        // app.globalData.wojide = this.data.wojide
    },
    enterMapBtn3: function (e) {
        app.globalData.selectId = e.currentTarget.dataset.index
        app.globalData.daijichuFlag = false
        app.globalData.wojideFlag = false
        app.globalData.woshoudeFlag = true
    }
})
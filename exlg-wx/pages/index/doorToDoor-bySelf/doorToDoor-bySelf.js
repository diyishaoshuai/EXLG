import request from '../../../utils/request'
var app = getApp()
Page({
    data: {
        // left: {
        //     backgroundColor: "rgb(236,236,236)",
        //     color: "#43DBD1"
        // },
        // right: {
        //     backgroundColor: "white",
        //     color: "black"
        // },
        left: '',
        fight: '',

        // 上门取件/服务点自寄
        dooToDoorView: false,
        bySelfView: true,

        //时间选择器
        time: '',
        //物品信息选择器
        objectMsg: '',
        //物品选择器列表
        objectMsgOptions: [
            "1kg",
            "2kg",
            "3kg",
            "4kg",
            "5kg",
            "6kg",
            "7kg",
            "8kg",
            "9kg",
            "10kg",
            "11kg",
            "12kg",
            "13kg",
            "14kg",
            "15kg",
            "16kg",
            "17kg",
            "18kg",
            "19kg",
            "20kg"
        ],


        selectSendMsg: '',
        selectReceiveMsg: '',

        sendAdress: {},
        receiveAdress: {},

        //结账
        count: "--"
    },

    // 生命周期函数
    onShow: function () {
    
        this.setData({
            dooToDoorView: app.globalData.dooToDoorView,
            bySelfView: app.globalData.bySelfView,
            left: app.globalData.left,
            right: app.globalData.right,
            selectSendMsg: app.globalData.selectSendMsg,
            selectReceiveMsg: app.globalData.selectReceiveMsg,
            sendAdress: app.globalData.sendAdressMsg,
            receiveAdress: app.globalData.receiveAdressMsg
        })
    },
    //上门取件
    selectLeft: function () {
        this.setData({
            left: {
                backgroundColor: "rgb(236,236,236)",
                color: "#43DBD1"
            },
            right: {
                backgroundColor: "white",
                color: "black"
            },
            dooToDoorView: false,
            bySelfView: true,

            time: "12:01"
        })
    },
    //服务点自寄
    selectRight: function () {
        this.setData({
            left: {
                backgroundColor: "white",
                color: "black"
            },
            right: {
                backgroundColor: "rgb(236,236,236)",
                color: "#43DBD1"
            },
            dooToDoorView: true,
            bySelfView: false
        })
    },

    //交互模式
    selectSendMsgBtn: function () {
        app.globalData.interact = true
        app.globalData.selectSendMsg = false
        app.globalData.flag = 1
    },

    selectReceiveMsgBtn: function () {
        app.globalData.interact = true
        app.globalData.selectReceiveMsg = false
        app.globalData.flag = 2
    },


    // 时间选择器
    changeTime: function (e) {
        this.setData({
            time: e.detail.value
        })
        this.data.time = e.detail.value
        app.globalData.time = this.data.time
    },
    //物品信息选择器
    changeObjectMsg: function (e) {
        this.data.objectMsg = this.data.objectMsgOptions[e.detail.value]
        this.data.count = this.data.objectMsgOptions.indexOf(this.data.objectMsg)
        this.setData({
            objectMsg: this.data.objectMsgOptions[e.detail.value],
            count: this.data.objectMsgOptions.indexOf(this.data.objectMsg) + 1,
        })
    },

    //更新本页面
    go_update() {
        // console.log('我更新啦')
        this.setData({
            sendAdress: app.globalData.sendAdressMsg,
            receiveAdress: app.globalData.receiveAdressMsg
        })
    },
    // 提交订单信息
    async checkOut(){
        console.log(this.data.sendAdress)
        console.log(this.data.receiveAdress)
        console.log(this.data.objectMsg)
        const result  = await request('/order/add',{
            send: this.data.sendAdress,
            receive: this.data.receiveAdress,
            objectMsg: this.data.objectMsg
        },'post')
        console.log(result)
        if(result.code === 0){
            // 下单成功
            wx.showModal({
              title:'提示',
              content:'下单成功',
            })
        }else{
            // 下单失败
            wx.showModal({
                title:'提示',
                content:'下单失败',
                confirmText:'重试'
            })
        }
    },
})
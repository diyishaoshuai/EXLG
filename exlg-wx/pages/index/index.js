var app = getApp()
app.globalData.dooToDoorView
Page({
    data: {
        imagesSrc: ['https://s1.328888.xyz/2022/06/06/zrXYM.png',
            'https://s1.328888.xyz/2022/06/06/zrfM7.png'
        ],
    },
    //快递员上门跳转
    doorToDoor: function () {
        app.globalData.dooToDoorView = false,
            app.globalData.bySelfView = true,
            app.globalData.left = {
                backgroundColor: "rgb(236,236,236)",
                color: "#43DBD1"
            },
            app.globalData.right = {
                backgroundColor: "white",
                color: "black"
            },
            //页面初始化
            app.globalData.interact = false,
            app.globalData.selectSendMsg = true,
            app.globalData.selectReceiveMsg = true
    },

    //服务点自寄跳转
    bySelf: function () {
        app.globalData.dooToDoorView = true,
            app.globalData.bySelfView = false,
            app.globalData.left = {
                backgroundColor: "white",
                color: "black"
            },
            app.globalData.right = {
                backgroundColor: "rgb(236,236,236)",
                color: "#43DBD1"
            },

            //页面初始化
            app.globalData.interact = false,
            app.globalData.selectSendMsg = true,
            app.globalData.selectReceiveMsg = true
    },



    //扫码（假的）
    scanCode() {
        var myThis = this;
        wx.scanCode({
            success(res) {

            }
        })
    },
})
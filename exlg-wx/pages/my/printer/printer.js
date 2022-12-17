// pages/my/printer/printer.js
Page({
    searchBtn() {
        wx.showModal({
            title: "未在附近找到蓝牙设备",
            cancelColor: 'cancelColor',
            showCancel:false
        })
    }
})
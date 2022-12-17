// pages/address/address.js
import request from '../../utils/request'
let app = getApp()
Page({
    data: {
        addresses: [], // 所有地址列表
        userId: "",
    },
    // 生命周期
    onShow() {
        const userInfo = JSON.parse(wx.getStorageSync('userInfo'));
        this.setData({
                userId: userInfo.id,
            }),
            this.selectAddress()

    },

    // 查询所有地址簿
    async selectAddress() {
        const reslut = await request('/addressBook/get/', {
            userId: this.data.userId
        }, 'get')
        this.setData({
            addresses: reslut.data
        })
        console.log(this.data.addresses)
    },

    //根据id删除
    async deleteAddressById(addressId) {

        const reslut = await request('/addressBook/remove/', {
            id: addressId
        }, 'get')
        this.selectAddress()
    },
    // 删除功能的实现
    deleteBtn(e) {
        let that = this
        let index = e.currentTarget.dataset.index
        console.log(index)
        wx.showModal({
            title: '提示',
            content: '确定要删除吗？',
            success(res) {
                if (res.confirm) {
                    that.deleteAddressById(index)
                } else if (res.cancel) {
                    console.log('用户点击取消')
                }
            }
        })
    },

    //选择地址
    selectAdd: function (e) {
        if (app.globalData.interact) {
            console.log('可以点击添加地址')
            app.globalData.adressIndex = e.currentTarget.dataset.index
            if (app.globalData.flag === 1) {
                // console.log(this.data.addresses[app.globalData.adressIndex])
                app.globalData.sendAdressMsg = this.data.addresses[app.globalData.adressIndex]
            }
            else if (app.globalData.flag === 2) {
                // console.log(this.data.addresses[app.globalData.adressIndex])
                app.globalData.receiveAdressMsg = this.data.addresses[app.globalData.adressIndex]
            }

            let pages = getCurrentPages(); //获取小程序页面栈
            let beforePage = pages[pages.length - 2]; //获取上个页面的实例对象
            beforePage.setData({ //直接修改上个页面的数据（可通过这种方式直接传递参数）
                txt: '修改数据了'
            })
            beforePage.go_update(); //触发上个页面自定义的go_update方法
            wx.navigateBack({ //返回上一页  
                delta: 1
            })
        } else {
            console.log('不能点击添加地址')
        }
    }
})
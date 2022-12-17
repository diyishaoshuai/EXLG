import request from '../../../utils/request'
var app=getApp()
// pages/addresses/address/address.js
Page({
    data: {
        id:"",
        userId: 0,  // 用户id
        //地址
        address: {
            name: '',
            phoneNumber: '',
            province: '',
            city: '',
            county: '',
            address: '',
            area:''
        },

        // 三级联动选中的数据
        region: [],
    },
    onLoad(option){
        const userInfo = JSON.parse(wx.getStorageSync('userInfo'));
        
        if(option.id!=null){
            this.getAddressById(option.id)
            this.setData({
                id:option.id,
            })
        }
        
        this.setData({
           userId: userInfo.id,
        })
        
    },

        //根据id查询
        async getAddressById(addressId){
        
            const reslut = await request('/addressBook/getById/',{
                id:addressId
            },'get')
            this.setData({
                address: {
                    name: reslut.data.sendUsername,
                    phoneNumber: reslut.data.sendTel,
                    address: reslut.data.address
                },
                region:reslut.addressName               
            })
            console.log(this.data.address.name)
            console.log(reslut)
        },

    //保存并返回
    async save() {
        this.detailedaddressCompound()
        if(this.data.region[0]===this.data.region[1]){
            this.data.region[1] = this.data.region[2]
            this.data.region[2] = ""
        }
        // 发请求保存到数据库
        const reslut = await request('/addressBook/save',{
            id:this.data.id,
            name: this.data.address.name,
            phoneNumber: this.data.address.phoneNumber,
            address: this.data.address.address,
            area:this.data.address.area,
            userId: this.data.userId,
            province: this.data.region[0],
            city: this.data.region[1],
            county: this.data.region[2],
        },'post')
        console.log(reslut)
        wx.navigateBack({ //返回上一页  
            delta: 1
        })
    },

    //地区选择器
    //点击确定按钮
    bindRegionChange: function (e) {
        this.setData({
            region:e.detail.value
        })
        this.data.address.region=e.detail.value
    },

    //传入数据
    //姓名
    getNameValue(e){
        // console.log(e.detail.value)
        this.data.address.name=e.detail.value
    },
    //电话
    getPhoneNumber(e){
        this.data.address.phoneNumber=e.detail.value
    },
    //所在地区
    getDetailedAddress(e){
            this.data.address.address=e.detail.value
    },

    // //地区合成
    detailedaddressCompound(){
        this.data.address.province = this.data.region[0];
        this.data.address.city = this.data.region[1];
        this.data.address.county = this.data.region[2];
        if(this.data.region[0]===this.data.region[1]){
            this.data.address.area = this.data.region[0]+this.data.region[2];
        }else{
            this.data.address.area = this.data.region[0]+this.data.region[1]+this.data.region[2];
        }
        
    }
})
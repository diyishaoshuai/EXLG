// pages/search/map/map.js
let app = getApp()
Page({
    data: {

    },
    onLoad(option){
        console.log(option)
        this.setData({
            daijichu: app.globalData.daijichu,
            wojide: [{
                send: "",
                receive: "",
                departure: option.departure,
                destination: option.destination,
                id: "",
                passingByAdress: [{
                    state: "已签收",
                    time: "2022/09/12 07:08",
                    adress: "常德市,校内71栋邮政服务部整理后会另有取件短息，投递员，电话：12345678910.风雨兼程。哇哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈哈"
                },
                {
                    state: "运输中",
                    time: "2022/09/11 07:08",
                    adress: "长沙市，航班到达"
                }, {
                    state: "运输中",
                    time: "2022/09/10 07:08",
                    adress: "深圳市，到达【深圳邮件处理中心】（经传）"
                }, {
                    state: "运输中",
                    time: "2022/09/09 07:08",
                    adress: "东莞市，离开【东莞邮件风湖里中心】，下一站【深圳邮件处理中心】"
                }
            ]
            },
        ],
        woshoude: [{
            send: "",
            receive: "",
            departure: option.departure,
            destination: option.destination,
            id: "",
            passingByAdress: [{
                state: "已签收",
                time: "2022/09/12 07:08",
                adress: "天津市,校内71栋邮政服务部整理后会另有取件短息，投递员，电话：12345678910.风雨兼程。哇哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈哈"
            },
            {
                state: "运输中",
                time: "2022/09/11 07:08",
                adress: "长沙市，航班到达"
            }, {
                state: "运输中",
                time: "2022/09/10 07:08",
                adress: "深圳市，到达【深圳邮件处理中心】（经传）"
            }, {
                state: "运输中",
                time: "2022/09/09 07:08",
                adress: "东莞市，离开【东莞邮件风湖里中心】，下一站【深圳邮件处理中心】"
            }
        ]
        }],
            selectId:app.globalData.selectId,

            daijichuFlag:app.globalData.daijichuFlag,
            wojideFlag:app.globalData.wojideFlag,
            woshoudeFlag:app.globalData.woshoudeFlag
        })
    }
})
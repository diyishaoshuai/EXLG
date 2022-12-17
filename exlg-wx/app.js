// app.js

App({
    globalData: {
        // 订单，待寄出，我寄的，我收的*
        daijichu: [{
            send: "高家淇",
            receive: "张森威",
            departure: "北京市",
            destination: "长沙市",
            id: "123456789",
            passingByAdress: [{
                    state: "已签收",
                    time: "2022/09/12 07:08",
                    adress: "长沙市,校内71栋邮政服务部整理后会另有取件短息，投递员，电话：12345678910.风雨兼程。哇哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈哈"
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
        }, {
            send: "张森威",
            receive: "高家淇",
            departure: "长沙市",
            destination: "北京市",
            id: "987654321",
            passingByAdress: [{
                state: "已签收",
                time: "2022/09/12 07:08",
                adress: "北京市,校内71栋邮政服务部整理后会另有取件短息，投递员，电话：12345678910.风雨兼程。哇哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈啊哈哈哈哈"
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
        }, {
            send: "高家淇",
            receive: "张森威",
            departure: "北京市",
            destination: "长沙市",
            id: "555555555"
        }],

        wojide: [{
                send: "高家淇",
                receive: "张森威",
                departure: "北京市",
                destination: "长沙市",
                id: "123456789",
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
            {
                send: "高家淇",
                receive: "张森威",
                departure: "常德市",
                destination: "邵阳市",
                id: "123456789"
            },
        ],

        woshoude: [{
            send: "高家淇",
            receive: "张森威",
            departure: "南京市",
            destination: "天津市",
            id: "123456789",
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
        }, {
            send: "高家淇",
            receive: "张森威",
            departure: "北京市",
            destination: "长沙市",
            id: "123456789"
        }, {
            send: "高家淇",
            receive: "张森威",
            departure: "北京市",
            destination: "长沙市",
            id: "123456789"
        }, {
            send: "高家淇",
            receive: "张森威",
            departure: "北京市",
            destination: "长沙市",
            id: "123456789"
        }, {
            send: "高家淇",
            receive: "张森威",
            departure: "北京市",
            destination: "长沙市",
            id: "123456789"
        }],

        //地址簿*
        // adresses: [{
        //         name: "张森威",
        //         phoneNumber: "17680565260",
        //         adress: "湖南省长沙市长沙县湖南信息学院XX栋XXXX"
        //     },
        //     {
        //         name: "艾如画",
        //         phoneNumber: "17680565260",
        //         adress: "湖南省长沙市长沙县湖南信息学院XX栋XXXX"
        //     }
        // ],

        //index中的相关参数
        //快递员上门&服务点自寄跳转复用参数
        dooToDoorView: '',
        bySelfView: '',
        left: '',
        right: '',

        //地址簿按钮控制交互参数
        interact: false,

        //地址簿传值索引
        adressIndex: '',

        //服务点自寄、上门取件选择信息
        selectSendMsg: true,
        selectReceiveMsg: true,
        flag: '',

        //信息内容*
        sendAdressMsg: {},
        receiveAdressMsg: {},

        //上门取件时间*
        time: '',

        //查询界面点击进入
        selectId: '',
        daijichuFlag:'',
        wojideFlag:'',
        woshoudeFlag:''
    
    },


})
package com.xxzy.EXLG.common.constant;


public class TruckConstant {

    /**
     *  车辆状态基本信息
     */
    public enum TruckStatusEnum {
        AWAIT(0,"待机"),
        TRANSPORT(1,"运输中");

        private Integer code;
        private String msg;

        TruckStatusEnum(Integer code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public String getMsg() {
            return msg;
        }

        public Integer getCode() {
            return code;
        }
    }
}

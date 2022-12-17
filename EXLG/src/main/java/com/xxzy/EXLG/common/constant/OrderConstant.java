package com.xxzy.EXLG.common.constant;

public class OrderConstant {

    /**
     * 订单状态
     */
    public enum OrderStatusEnum {
        NOT_SEND(0,"未发货"),
        READY_SEND(1,"已发货"),
        IN_TRANSIT(2,"运输中"),
        ARRIVE(3,"已送达"),
        READY_SIGN(4,"已签收");

        private Integer code;
        private String msg;

        OrderStatusEnum(Integer code, String msg) {
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

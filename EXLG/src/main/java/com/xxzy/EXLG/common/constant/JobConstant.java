package com.xxzy.EXLG.common.constant;

/**
 * @author 竹风
 * date:2022/5/15
 */
public class JobConstant {

    /**
     * 职位类型
     */
    public enum JobTypeEnum {

        PRINCIPAL(0, "负责人"),
        EMPLOYEE(1, "普通员工"),
        DRIVER(2, "司机");

        private Integer code;
        private String msg;

        JobTypeEnum(Integer code, String msg) {
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

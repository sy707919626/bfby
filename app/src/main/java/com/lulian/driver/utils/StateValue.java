package com.lulian.driver.utils;

/**
 * Created by MARK on 2017/7/8.
 */

public class StateValue {

    /**
     * 运单状态
     */
    public static class OrderState {
        /**
         * 配载中    相当于待接单
         */
        public static final int RECEIVER = 1;
        /**
         * 运输中    相当于待送达
         */
        public static final int DELIVERY = 2;
        /**
         * 已送达    上传回单之后
         */
        public static final int FINISH = 3;
        /**
         * 已结算    暂时不管
         */
        public static final int BALANCE = 4;
    }



    /**
     * 车辆认证状态
     */
    public static class VehicleCertifiedState {
        /**
         * 未认证
         */
        public static final int UNCERTIFIED = 0;//未认证
        /**
         * 已认证
         */
        public static final int HASCERTIFIED = 1;//已认证
        /**
         * 认证失败
         */
        public static final int FAILEDCERTIFIED= 2;//认证失败
    }

    /**
     * 车辆认证类型
     */
    public static class VehicleCertifiedType {
        /**
         * 内部车
         */
        public static final int INSIDE = 0;//内部车
        /**
         * 外协车
         */
        public static final int OUTSIDE = 1;//外协车
    }

    /**
     * 运力状态
     */
    public static class Vehiclestatus {
        /**
         * 休息
         */
        public static final int REST = 0;
        /**
         * 停止服务
         */
        public static final int NOUSE = 1;
        /**
         * 运输中
         */
        public static final int USEING = 2;
        /**
         * 使用中
         */
        public static final int EMPTY = 3;
    }

}

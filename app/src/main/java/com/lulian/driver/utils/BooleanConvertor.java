package com.lulian.driver.utils;

/**
 * @author hxb
 */
public class BooleanConvertor {

    public static boolean intToBoolean(int i) {
        return i == 0;
    }

    public static int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

}

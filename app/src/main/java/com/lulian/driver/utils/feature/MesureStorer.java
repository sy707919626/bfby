package com.lulian.driver.utils.feature;


/**
 * 用来保存动态测量出来的界面上view的尺寸
 */
public class MesureStorer {

    private static int mainBottomTabHeight;//首页底部tab栏的高度

    public static int getMainBottomTabHeight() {
        return mainBottomTabHeight;
    }

    public static void setMainBottomTabHeight(int mainBottomTabHeight) {
        MesureStorer.mainBottomTabHeight = mainBottomTabHeight;
    }
}

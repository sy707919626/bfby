package com.lulian.driver.view;


import android.annotation.SuppressLint;

import com.lulian.driver.base.SpinnerItem;
import com.lulian.driver.entity.AllowCarType;
import com.lulian.driver.entity.SimpleBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hxb
 */
public class DictDataTool {

    public static final String TRUCK_TYPE_TEXT_UNCONFIRM="待定";
    public static final String DEFAULT_UNLIMITED_TEXT = "不限";

    public static final String TRUCK_TYPE_UNLIMITED_ID = "A949D65C-64AF-425D-8F57-F1F0EDC6197C";//车型列表中"不限"选项的id

    /**
     * 获取运单待运输状态
     *
     * @return
     */
    public static List<SpinnerItem> getOrderStatu() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("全部", "0"));
        list.add(new SpinnerItem("待取货", "1"));
        list.add(new SpinnerItem("装货中", "2"));
        return list;
    }

    /**
     * 获取运单待运输中
     *
     * @return
     */
    public static List<SpinnerItem> getOrderStatu1() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("运输中", "0"));
        list.add(new SpinnerItem("反馈异常单", "0"));
        list.add(new SpinnerItem("到达目的地", "3"));
        list.add(new SpinnerItem("确认收货", "4"));
        return list;
    }

    /**
     * 获取运单完成
     *
     * @return
     */
    public static List<SpinnerItem> getOrderStatu2() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("已完成", "0"));
        list.add(new SpinnerItem("提醒付款", "0"));
        list.add(new SpinnerItem("待评价", "6"));
        list.add(new SpinnerItem("确认收货", "7"));
        return list;
    }

    /**
     * 获取车队长等级筛选项
     */
    public static List<SpinnerItem> getCDZstart() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("等级", "0"));
        list.add(new SpinnerItem("一星", "1"));
        list.add(new SpinnerItem("二星", "2"));
        list.add(new SpinnerItem("三星", "3"));
        list.add(new SpinnerItem("四星", "4"));
        list.add(new SpinnerItem("五星", "5"));
        return list;
    }

    /**
     * 获取车队长等级筛选项
     */
    public static List<SpinnerItem> getCDZdistance() {
        ArrayList<SpinnerItem> list = new ArrayList<>();
        list.add(new SpinnerItem("距离", "0"));
        list.add(new SpinnerItem("由远及近", "0"));
        list.add(new SpinnerItem("由近及远", "1"));
        return list;
    }


    /**
     * 获取准驾车型列表
     * @return
     */
    public static List<AllowCarType> getLicAllowCarTypeList() {
        List<AllowCarType> list = new ArrayList<>();

        list.add(new AllowCarType("A1", "大型客车", "大型载客汽车"));
        list.add(new AllowCarType("A2", "牵引车", "重型、中型全挂、半挂汽车列车"));
        list.add(new AllowCarType("A3", "城市公交车", "核准载10人以上的城市公共汽车"));
        list.add(new AllowCarType("B1", "中型客车", "中型载客汽车，含核准载10人以上、19人以下的城市公共汽车"));
        list.add(new AllowCarType("B2", "大型货车", "重型、中型载货汽车；大、重、中型专项作业车"));
        list.add(new AllowCarType("C1", "小型汽车", "小型、微型载客汽车以及轻型、微型载货汽车、轻、小、微专项作业车"));
        list.add(new AllowCarType("C2", "小型自动排挡汽车", "小型、微型自动排挡载客汽车以及轻型、微型自动排挡载货汽车"));
        list.add(new AllowCarType("C3", "低速载货汽车", "低速载货汽车，原四轮农用运输车"));
        list.add(new AllowCarType("C4", "三轮汽车", "三轮汽车，原三轮农用运输车"));

        return list;
    }


    /**
     * 获取准驾车型的map形式
     * map key:车型编码,map value:{@link AllowCarType}
     */
    public static Map<String,AllowCarType> getLicAllowCarTypeMap(){
        Map<String, AllowCarType> map = new HashMap<>();
        List<AllowCarType> list = getLicAllowCarTypeList();

        for (AllowCarType alType : list) {
            map.put(alType.getCode(), alType);
        }

        return map;
    }


    /**
     * 获取时间范围选项列表
     * @return
     */
    public static List<SimpleBean> getTimeScopeList(boolean hasUnLimited){
        List<SimpleBean> list = new ArrayList<>();

        if(hasUnLimited){
            list.add(new SimpleBean("", DEFAULT_UNLIMITED_TEXT));
        }
        list.add(new SimpleBean("1","今天"));
        list.add(new SimpleBean("2","明天"));
        list.add(new SimpleBean("3","明天以后"));

        return list;
    }


    /**
     * 订单状态和状态文字的映射map
     */
    public static Map<Integer,String> getOrderStatusMap(){
        @SuppressLint("UseSparseArrays")
        HashMap<Integer, String> map = new HashMap<>();
        map.put(0,"草稿");
        map.put(1,"已撤单");
        map.put(2,"已发布");
        map.put(3,"询价中");
        map.put(4,"待支付");
        map.put(5,"待派车");
        map.put(6,"待发货");

        return map;
    }


    /**
     * 运单状态和状态文字的映射map
     * @return
     */
    public static Map<Integer,String> getWayBillStatusMap(){
        @SuppressLint("UseSparseArrays")
        HashMap<Integer, String> map = new HashMap<>();

        map.put(1, "待取货");
        map.put(2, "已装车");
        map.put(3, "运送中");
        map.put(4, "已到达");
        map.put(12, "已卸货");
        map.put(5, "已完成");
        map.put(6, "申请退单");
        map.put(7, "已关闭");
        map.put(11, "取消");

        return map;
    }


}

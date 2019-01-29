package com.lulian.driver.entity.server.resulte;

import com.lulian.driver.entity.server.MyPersonInfoBean;
import com.lulian.driver.entity.server.MyTruckInfoBean;

import java.io.Serializable;
import java.util.List;

/**
 * 获取个人信息和车辆信息接口返回的数据对象
 * @author hxb
 */
public class MyInfoBean implements Serializable {

    private MyPersonInfoBean Person;
    private List<MyTruckInfoBean> Vehicles;

    public MyPersonInfoBean getPerson() {
        return Person;
    }

    public void setPerson(MyPersonInfoBean person) {
        Person = person;
    }

    public List<MyTruckInfoBean> getVehicles() {
        return Vehicles;
    }

    public void setVehicles(List<MyTruckInfoBean> vehicles) {
        Vehicles = vehicles;
    }
}

package com.lulian.driver.entity.server.resulte;

/**
 * Created by MARK on 2018/6/26.
 */

public class City {
    /*"Id": "210100",
            "Name": "沈阳市",
            "FullName": "辽宁 沈阳市"*/
    public City(){

    }
    public City(String Name){
        setName(Name);
    }
    private String Id;
    private String Name;
    private String FullName;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }
}

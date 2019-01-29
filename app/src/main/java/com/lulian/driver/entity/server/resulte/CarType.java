package com.lulian.driver.entity.server.resulte;

/**
 * Created by MARK on 2018/6/26.
 */

public class CarType {
    private String Text;
    private String Id;

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


    @Override
    public boolean equals(Object obj) {
        if(obj instanceof CarType){
            CarType ct = (CarType) obj;
            return this.Id.equals(ct.getId());
        }
        return false;
    }
}

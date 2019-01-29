package com.lulian.driver.entity.server.resulte;

/**
 * Created by MARK on 2018/7/13.
 */

public class StartTags {
    private String CreateId;
    private int Deleted;
    private String Tag;
    private int Star;
    private int Category;
    private String Id;

    public String getCreateId() {
        return CreateId;
    }

    public void setCreateId(String createId) {
        CreateId = createId;
    }

    public int getDeleted() {
        return Deleted;
    }

    public void setDeleted(int deleted) {
        Deleted = deleted;
    }

    public String getTag() {
        return Tag;
    }

    public void setTag(String tag) {
        Tag = tag;
    }

    public int getStar() {
        return Star;
    }

    public void setStar(int star) {
        Star = star;
    }

    public int getCategory() {
        return Category;
    }

    public void setCategory(int category) {
        Category = category;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}

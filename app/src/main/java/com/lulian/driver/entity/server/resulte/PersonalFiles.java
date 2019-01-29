package com.lulian.driver.entity.server.resulte;

/**
 * {
 "Id": "string",
 "FileName": "string",
 "Url": "string"
 }
 */

public class PersonalFiles {
    private String Id;
    private String FileName;
    private String Url;
    private String FieldFlag;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getFieldFlag() {
        return FieldFlag;
    }

    public void setFieldFlag(String fieldFlag) {
        FieldFlag = fieldFlag;
    }
}

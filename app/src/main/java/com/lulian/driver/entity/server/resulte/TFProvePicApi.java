package com.lulian.driver.entity.server.resulte;

/**
 * Created by Administrator on 2018/8/10.
 */

/*
      "TransportFormID": "AF79A306-3E0C-4D9A-86B7-8D7AA021D682",
      "FileId": null,
      "FileName": null,
      "Url": null,
      "FieldFlag": null

* */
public class TFProvePicApi {
    private String TransportFormID;
    private String FileId;
    private String FileName;
    private String Url;
    private String FieldFlag;

    public String getTransportFormID() {
        return TransportFormID;
    }

    public void setTransportFormID(String transportFormID) {
        TransportFormID = transportFormID;
    }

    public String getFileId() {
        return FileId;
    }

    public void setFileId(String fileId) {
        FileId = fileId;
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

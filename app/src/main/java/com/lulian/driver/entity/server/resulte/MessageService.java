package com.lulian.driver.entity.server.resulte;

/**
 * Created by MARK on 2018/6/28.
 */

public class MessageService {
    /**
     * CreateId : 49ad954c-df3e-4431-8c54-054c7b4cf3b6
     * CreateTime : 2018-08-07 09:30:46
     * Deleted : 0
     * Content : 您发布的货源订单已被车队长接单，请及时前往查看并联系车队长。
     * UserId : 49ad954c-df3e-4431-8c54-054c7b4cf3b6
     * Readed : 0
     * MessageType : 2
     * Title : 订单被接单
     * OrderId : fcb0bbd6-41ee-40d5-9030-930f42b69245
     * Id : 08f303eb-a7a9-4ef4-928b-e5d0a9ef69e7
     */

    private String CreateId;
    private String CreateTime;
    private int Deleted;
    private String Content;
    private String UserId;
    private int Readed;
    private int MessageType;
    private String Title;
    private String OrderId;
    private String Id;

    public String getCreateId() {
        return CreateId;
    }

    public void setCreateId(String CreateId) {
        this.CreateId = CreateId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String CreateTime) {
        this.CreateTime = CreateTime;
    }

    public int getDeleted() {
        return Deleted;
    }

    public void setDeleted(int Deleted) {
        this.Deleted = Deleted;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public int getReaded() {
        return Readed;
    }

    public void setReaded(int Readed) {
        this.Readed = Readed;
    }

    public int getMessageType() {
        return MessageType;
    }

    public void setMessageType(int MessageType) {
        this.MessageType = MessageType;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String OrderId) {
        this.OrderId = OrderId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }
}

package com.lulian.driver;

import com.lulian.driver.entity.server.MyPersonInfoBean;
import com.lulian.driver.entity.server.MyTruckInfoBean;
import com.lulian.driver.entity.server.ProofPhotoBean;
import com.lulian.driver.entity.server.req.ReqHallOrderListBean;
import com.lulian.driver.entity.server.req.ReqOfferBean;
import com.lulian.driver.entity.server.req.ReqWayBillListBean;
import com.lulian.driver.entity.server.req.SaveDriverInfoBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MARK on 2018/6/14.
 */

public interface HttpService {

    //获取token
    @FormUrlEncoded
    @POST("api/Tokens/GetToken")
    Observable<String> getToken(@FieldMap Map<String, String> options);

    //获取验证码
    @POST("/api/AppSys/SendVerifySms")
    Observable<String> getMsmCode(@Header("authorization") String authorization, @Query("phone") String phone,
                                  @Query("verifyType") String verifyType);

    //校验验证码(接口已经取消)
    @GET("/api/AppUser/CheckMsmCode?")
    Observable<String> checkMsmCode(@Header("authorization") String authorization,
                                    @Query("mobile") String mobile,
                                    @Query("code") String code);

    //注册用户
    @POST("/api/AppSys/Register")
    Observable<String> register(@Header("authorization") String authorization, @Header("user") String userHead,
                                @Body RequestBody registerBody);

    //检测手机号是否已经注册
    @GET("api/AppSys/CheckIsRegister")
    Observable<String> checkIsRegister(@Header("authorization") String authorization, @Header("user") String user,
                                       @Query("phone") String phone);


    //保存我的个人信息和车辆信息总和
    @POST("api/AppDriver/SaveDriverInfo")
    Observable<String> saveDriverInfo(@Header("authorization") String authorization, @Header("user") String user,
                                      @Body SaveDriverInfoBean driverInfoBean);

    //保存我的个人信息
    @POST("api/AppDriver/SaveDriverInfoStep1")
    Observable<String> saveDriverInfoStep1(@Header("authorization") String authorization, @Header("user") String user,
                                           @Body MyPersonInfoBean personInfoBean);


    //保存我的车辆信息
    @POST("api/AppDriver/SaveDriverInfoStep2")
    Observable<String> saveDriverInfoStep2(@Header("authorization") String authorization, @Header("user") String user,
                                           @Body MyTruckInfoBean truckInfoBean);


    //获取我的个人信息和车辆信息总和
    @GET("api/AppDriver/GetMyInfo")
    Observable<String> getMyInfo(@Header("authorization") String authorization, @Header("user") String user);

    //获取我的个人信息
    @GET("api/AppDriver/GetMyDriverInfo")
    Observable<String> getMyDriverInfo(@Header("authorization") String authorization, @Header("user") String user);

    //获取我的车辆信息
    @GET("api/AppDriver/GetMyVehicles")
    Observable<String> getMyVehiclesInfo(@Header("authorization") String authorization, @Header("user") String user);


    //登录用户
    @POST("/api/AppSys/Login")
    Observable<String> login(@Header("authorization") String authorization,
                             @Body RequestBody loginBody);

    //修改密码
    @POST("/api/AppSys/ResetPwd")
    Observable<String> resetPwd(@Header("authorization") String authorization, @Header("user") String userHead,
                                @Body RequestBody loginBody);

    //文件图片上传
    @POST("/api/AppSys/UploadCard")
    Observable<String> uploadImage(@Header("authorization") String authorization, @Header("user") String user,
                                   @Body MultipartBody fileBody, @Query("fileType") String fileType);


    //司机户注册完善资料
    @POST("/api/AppDriver/SaveDriverInfo")
    Observable<String> saveInfo(@Header("authorization") String authorization,
                                @Body RequestBody infoBody);

    //获取车队长列表
    @POST("/api/AppDriver/getDriverLeaderList")
    Observable<String> getDriverLeaderList(@Header("authorization") String authorization, @Header("user") String user,
                                           @Body RequestBody infoBody);

    //微信支付接口
    @POST("/api/PaymentForWeChatApp/PostPreMoney")
    Observable<String> postPreMoney(@Header("authorization") String authorization, @Header("user") String user,
                                    @Body RequestBody infoBody);

    //根据电话号码获取车队长列表
    @POST("/api/AppDriver/getDriverLeaderByPhone")
    Observable<String> getDriverLeaderByPhone(@Header("authorization") String authorization, @Header("user") String user,
                                              @Query("phoneNo") String phoneNo);

    //邀请车队长注册
    @POST("/api/AppDriver/addInviteDriverLeader")
    Observable<String> addInviteDriverLeader(@Header("authorization") String authorization, @Header("user") String user,
                                             @Body RequestBody infoBody);


    //获取车源详情 TYPE:1.车队长 2,司机 3,货主
    @POST("/api/AppDriverLeader/GetUserInfo")
    Observable<String> getDriverVehicleDetail(@Header("authorization") String authorization,
                                              @Header("user") String user, @Query("userid") String driverUserId,
                                              @Query("type") String type);

    //获取个人信息
    @GET("/api/AppDriver/getMyDatum")
    Observable<String> getMyDatum(@Header("authorization") String authorization, @Header("user") String user);

    //保存个人信息
    @POST("/api/AppDriver/PostMydatum")
    Observable<String> postMydatum(@Header("authorization") String authorization,
                                   @Header("user") String user, @Body RequestBody infoBody);

    //获取车辆信息
    @GET("/api/Vehicles/getVehiclesInfo")
    Observable<String> getVehiclesInfo(@Header("authorization") String authorization, @Header("user") String user,
                                       @Query("driverid") String driverid, @Query("plateno") String plateno);

    //保存车辆信息
    @POST("/api/AppDriver/PostMyVehiclesInfo")
    Observable<String> postMyVehiclesInfo(@Header("authorization") String authorization, @Header("user") String user,
                                          @Body RequestBody infoBody);

    //修改接单状态
    @POST("/api/AppDriver/ModeifyWorkStatus")
    Observable<String> ModeifyWorkStatus(@Header("authorization") String authorization, @Header("user") String user,
                                         @Query("driverid") String driverid);


    //加入车源到我的车队
    @POST("/api/AppDriverLeader/AddDriver")
    Observable<String> addDriverVehicle(@Header("authorization") String authorization,
                                        @Header("user") String user, @Query("DriversId") String DriversId
            , @Query("DriverVehicleId") String DriverVehicleId);

    //移出车源
    @POST("/api/AppDriverLeader/RemoveDriver")
    Observable<String> removeDriverVehicle(@Header("authorization") String authorization,
                                           @Header("user") String user, @Query("DriversId") String driverUserId
            , @Query("DriverVehicleId") String DriverVehicleId);

    //备注司机
    @POST("/api/AppDriverLeader/RemarkDriver")
    Observable<String> remarkDriver(@Header("authorization") String authorization,
                                    @Header("user") String user,
                                    @Query("driverUserId") String driverUserId, @Query("remark") String driverPhone);

    //刷新司机定位
    @POST("/api/AppLeader/RefreshDriverGPS")
    Observable<String> refreshDriverGPS(@Header("authorization") String authorization,
                                        @Header("user") String user,
                                        @Query("driverUserId") String driverUserId, @Query("gps") String gps);

    //加入黑名单
    @POST("/api/AppDriverLeader/AddBlacklist")
    Observable<String> addBlacklist(@Header("authorization") String authorization,
                                    @Header("user") String user,
                                    @Query("driverUserId") String driverUserId, @Query("reason") String reason);


    //获取车型列表(需替换)
    @POST("api/AppDriverLeader/GetVehicleTypeList")
    Observable<String> getVehicleTypeList(@Header("authorization") String authorization,
                                          @Header("user") String user);

    //获取车长(需替换)
    @POST("/api/AppDriverLeader/GetVehicleLengthList")
    Observable<String> GetVehicleLengthList(@Header("authorization") String authorization,
                                            @Header("user") String user);


    //获取地区列表
    @POST("/api/AppLeader/GetAreaList")
    Observable<String> getAreaList(@Header("authorization") String authorization,
                                   @Header("user") String user,
                                   @Query("level") String level, @Query("pid") String pid);


    // 获取最新消息列表（指令驱动)
    @POST("/api/AppLeader/GetNewMessageList")
    Observable<String> getNewMessageList(@Header("authorization") String authorization,
                                         @Header("user") String user);


    //获取我的消息列表
    @POST("/api/AppSys/GetMessageList")
    Observable<String> getMyMessageList(@Header("authorization") String authorization, @Header("user") String user,
                                        @Body RequestBody infoBody);

    //获取我的消息数量
    @POST("/api/AppSys/GetUnReadedMessageCount")
    Observable<String> getUnReadedMessageCount(@Header("authorization") String authorization, @Header("user") String user,
                                               @Body RequestBody infoBody);

    //获取消息详情
    @POST("/api/AppSys/GetMessageDetail")
    Observable<String> getMessageDetail(@Header("authorization") String authorization, @Header("user") String user,
                                        @Query("messageid") String messageid);


    //获取订单列表(司机端)
    @POST("/api/AppDriver/getOrderList")
    Observable<String> queryOrder(@Header("authorization") String authorization, @Header("user") String user,
                                  @Body RequestBody infoBody, @Query("Sendtype") int sendType);


    //退单
    @POST("/api/AppDriverLeader/ReturnOrder")
    Observable<String> returnOrder(@Header("authorization") String authorization,
                                   @Header("user") String user,
                                   @Body RequestBody infoBody);


    //获取订单详情
    @POST("/api/AppDriver/getOrderDetail")
    Observable<String> getOrderInfo(@Header("authorization") String authorization,
                                    @Header("user") String user, @Query("orderId") String orderid);

    //拒绝任务
    @POST("/api/AppDriver/refuseTask")
    Observable<String> getRefuseTask(@Header("authorization") String authorization,
                                     @Header("user") String user, @Query("orderId") String orderid);

    //接受任务
    @POST("/api/AppDriver/ReceiveTask")
    Observable<String> getReceiveTask(@Header("authorization") String authorization,
                                      @Header("user") String user, @Query("orderId") String orderid);


    //文件图片上传（不可上传证件）
    @POST("/api/AppSys/UploadFile")
    Observable<String> uploadFile(@Header("authorization") String authorization, @Header("user") String user,
                                  @Body MultipartBody fileBody);

    //运单列表
    @POST("/api/AppDriver/getTransportFormList")
    Observable<String> getTransportFormList(@Header("authorization") String authorization,
                                            @Header("user") String user, @Body RequestBody infoBody);


    //根据运单id查运单详细信息
    @GET("/api/AppDriverLeader/getTFInfo")
    Observable<String> getOrder(@Header("authorization") String authorization,
                                @Header("user") String user,
                                @Query("OrderId") String OrderId);

    //根据运单ID和运单状态值查询各跟踪节点的证明照片
    @POST("/api/AppDriverLeader/getTFProvePic")
    Observable<String> getTFProvePic(@Header("authorization") String authorization,
                                     @Header("user") String user,
                                     @Query("tfid") String tfid,
                                     @Query("statusvalue") int statusvalue);

    //根据订单ID，获取退回理由 navigation:运单/我的运单 /订单（运单）详细/其它人退回理由
    @GET("/api/AppDriverLeader/getOrderBackInfo")
    Observable<String> getOrderBackInfo(@Header("authorization") String authorization,
                                        @Header("user") String user,
                                        @Query("OrderId") String OrderId);


    //根据订单id查询司机详细navigation:运单列表/评价（订单）/受评价司机信息author:Lixz
    @GET("/api/AppDriverLeader/getDriverInfo")
    Observable<String> getDriverInfo(@Header("authorization") String authorization,
                                     @Header("user") String user,
                                     @Query("OrderId") String OrderId);

    //根据订单号查询货主信息navigation:运单列表/评价（订单）/受评价货主信息author:lixz
    @GET("/api/AppDriverLeader/getClientInfo")
    Observable<String> getClientInfo(@Header("authorization") String authorization,
                                     @Header("user") String user,
                                     @Query("Orderid") String Orderid);

    //根据订单号查询车队长信息。navigation:运单列表/评价（订单）/受评价车队长信息。author:Lixz
    @GET("/api/AppDriverLeader/getDriverLead")
    Observable<String> getDriverLead(@Header("authorization") String authorization,
                                     @Header("user") String user,
                                     @Query("OrderId") String OrderId);

    // /api/AppDriverLeader/getTFProblemList获取运单异常记录列表；rtype记录人类型 1：司机；2：车队长；3：货主
    @GET("/api/AppDriverLeader/getTFProblemList")
    Observable<String> getTFProblemList(@Header("authorization") String authorization,
                                        @Header("user") String user,
                                        @Query("tfid") String tfid, @Query("rtype") String rtype);

    // 提交运单异常记录； 1、调用AppSysController.UploadFile接口上传图片;
    // 2、调用本接口上传问题。navigation:运单/运单列表/提交异常记录；
    @POST("/api/AppDriverLeader/PostTFProblem")
    Observable<String> postTFProblem(@Header("authorization") String authorization,
                                     @Header("user") String user, @Query("tfid") String tfid,
                                     @Query("recordtype") int recordtype,
                                     @Body RequestBody infoBody);


    //根据星级查询评价标签内容。navigation：运单/评价/列出评价标签项。author:Lixz。
    @POST("/api/AppDriverLeader/getCommentsTags")
    Observable<String> getCommentsTags(@Header("authorization") String authorization,
                                       @Header("user") String user,
                                       @Query("stargrade") int stargrade, @Query("category") int category);

    //写入评价结果。评价/提交评价。ixz
    @POST("/api/AppDriverLeader/SubmitCommentResutl")
    Observable<String> SubmitCommentResutl(@Header("authorization") String authorization,
                                           @Header("user") String user, @Body RequestBody infoBody);


    //已到货源地；确认发车；到达目的地；确认收货;
    @POST("/api/AppDriver/PostDriverActionInfo")
    Observable<String> checkOnLoad(@Header("authorization") String authorization,
                                   @Header("user") String user,
                                   @Query("tfid") String tfid,
                                   @Query("dealstatus") String dealstatus,
                                   @Body RequestBody filesBody);


    //指派司机运单/待取货详细/重新指派司机
    @POST("/api/AppDriverLeader/ArrangedDriver")
    Observable<String> arrangedDriver(@Header("authorization") String authorization,
                                      @Header("user") String user,
                                      @Query("orderid") String orderid,
                                      @Query("driverid") String driverid);


    //  获取《我的》界面中的信息 已完成
    @POST("/api/AppDriverLeader/GetMySelfInfo")
    Observable<String> getMySelfInfo(@Header("authorization") String authorization,
                                     @Header("user") String user);


    //获取平台虚拟账户信息
    @GET("/api/MyInfo_Account/GetListByUserId")
    Observable<String> GetListByUserId(@Header("authorization") String authorization,
                                       @Header("user") String user, @Query("userid") String userid);

    //获取平台内部支付,参数：支付方账号Id,支付方支付密码，收款方账号Id,支付金额
    @POST("/api/MyInfo_Account/PlatPay")
    Observable<String> PlatPay(@Header("authorization") String authorization,
                               @Header("user") String user, @Body RequestBody infoBody);

    //修改卡密码[即重设密码]
    @POST("/api/MyInfo_Account/ReSetPassword")
    Observable<String> ReSetPassword(@Header("authorization") String authorization,
                                     @Header("user") String user, @Body RequestBody infoBody);


    //钱包明细
    @POST("/api/System_PlatformRunningWater/Page")
    Observable<String> getWalletDetail(@Header("authorization") String authorization,
                                       @Header("user") String user, @Body RequestBody infoBody);


    //配货大厅订单列表
    @POST("api/AppDriver/GetOrderListHall")
    Observable<String> getOrderListHall(@Header("authorization") String authorization,
                                        @Header("user") String user,
                                        @Body ReqHallOrderListBean reqBean);


    //获取订单详情
    @GET("api/AppDriver/GetOrderDetail")
    Observable<String> getOrderDetail(@Header("authorization") String authorization,
                                      @Header("user") String user,
                                      @Query("orderid") String orderId);

    //对订单进行报价
    @POST("api/AppDriver/PostDriverOrderQuotation")
    Observable<String> orderOffer(@Header("authorization") String authorization,
                                  @Header("user") String user,
                                  @Body ReqOfferBean bean);

    //获取运单列表
    @POST("/api/AppDriver/GetTransportFormList")
    Observable<String> getWayBillList(@Header("authorization") String authorization,
                                      @Header("user") String user,
                                      @Body ReqWayBillListBean bean);

    //获取运单详情
    @POST("/api/AppDriver/GetTransportFormDetail")
    Observable<String> getWayBillDetail(@Header("authorization") String authorization,
                                        @Header("user") String user,
                                        @Query("vFormID") String id);

    /**
     * 改变运单状态的接口
     */
    @POST("/api/AppDriver/ChangeTransportForm")
    Observable<String> changeTransportForm(@Header("authorization") String authorization,
                                           @Header("user") String user,
                                           @Query("FormID") String id,
                                           @Query("Status") int changeToStatus,
                                           @Body List<ProofPhotoBean> proofPhotoList);
    /**
     * 收藏车队长接口
     */
    @GET("/api/AppDriver/AddMyDreaverLeader")
    Observable<String> addDriverLeader(@Header("authorization") String authorization,
                                       @Header("user") String user,
                                       @Query("userid") String driverLeaderId);
}

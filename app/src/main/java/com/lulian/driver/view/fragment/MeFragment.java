package com.lulian.driver.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.hedgehog.ratingbar.RatingBar;
import com.lulian.driver.R;
import com.lulian.driver.base.BaseFragment;
import com.lulian.driver.entity.api.GetListByUserIdApi;
import com.lulian.driver.entity.api.GetMySelfInfoApi;
import com.lulian.driver.entity.api.ModeifyWorkStatus;
import com.lulian.driver.entity.server.resulte.CapitalAcount;
import com.lulian.driver.entity.server.resulte.CapitalAcountInfo;
import com.lulian.driver.entity.server.resulte.MeBean;
import com.lulian.driver.utils.L;
import com.lulian.driver.utils.ProjectUtil;
import com.lulian.driver.view.activity.caractivity.MyDatumActivity;
import com.lulian.driver.view.activity.caractivity.MessageActivity;
import com.lulian.driver.view.activity.caractivity.MyCollectActivity;
import com.lulian.driver.view.activity.caractivity.MyWalletActivity;
import com.lulian.driver.view.activity.caractivity.SettingActivity;
import com.lulian.driver.view.activity.caractivity.UserIntroduceActivity;
import com.lulian.driver.view.wheel.MyGridView;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import q.rorbin.badgeview.QBadgeView;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by MARK on 2018/6/9.
 */

public class MeFragment extends BaseFragment {
    Unbinder unbinder;
    @BindView(R.id.me_txt_left)
    TextView meTxtLeft;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.me_img_right)
    ImageView meImgRight;
    @BindView(R.id.me_txt_right)
    TextView meTxtRight;
    @BindView(R.id.me_layout_ba)
    LinearLayout meLayoutBa;
    @BindView(R.id.me_img_head)
    ImageView meImgHead;
    @BindView(R.id.me_txt_name)
    TextView meTxtName;
    @BindView(R.id.ratingbar)
    RatingBar ratingbar;
    @BindView(R.id.me_img_n)
    ImageView meImgN;
    @BindView(R.id.me_relativeLayout_n)
    RelativeLayout meRelativeLayoutN;
    @BindView(R.id.me_txt_shiming)
    TextView meTxtShiming;
    @BindView(R.id.me_txt_companyrenz)
    TextView meTxtCompanyrenz;
    @BindView(R.id.state_selection_btn)
    Button stateSelectionBtn;
    @BindView(R.id.me_layout_authentication)
    LinearLayout meLayoutAuthentication;
    @BindView(R.id.me_ll_top)
    LinearLayout meLlTop;
    @BindView(R.id.vip_register_time)
    TextView vipRegisterTime;
    @BindView(R.id.me_authentication_rztime)
    TextView meAuthenticationRztime;
    @BindView(R.id.last_login_time)
    TextView lastLoginTime;
    @BindView(R.id.me_txt_hotline)
    TextView meTxtHotline;
    @BindView(R.id.me_txt_call)
    TextView meTxtCall;
    @BindView(R.id.me_txt_yuer)
    TextView meTxtYuer;
    @BindView(R.id.me_txt_yingshou)
    TextView meTxtYingshou;
    @BindView(R.id.me_layout_wallet)
    LinearLayout meLayoutWallet;
    @BindView(R.id.me_txt_daiquhuo)
    TextView meTxtDaiquhuo;
    @BindView(R.id.metxtdaiquhuonum)
    TextView metxtdaiquhuonum;
    @BindView(R.id.me_layout_daiquhuo)
    LinearLayout meLayoutDaiquhuo;
    @BindView(R.id.me_txt_transmiting)
    TextView meTxtTransmiting;
    @BindView(R.id.metxttransmitingnum)
    TextView metxttransmitingnum;
    @BindView(R.id.me_layout_transmiting)
    LinearLayout meLayoutTransmiting;
    @BindView(R.id.me_txt_pingjia)
    TextView meTxtPingjia;
    @BindView(R.id.metxtpingjianum)
    TextView metxtpingjianum;
    @BindView(R.id.me_layout_pingjia)
    LinearLayout meLayoutPingjia;
    @BindView(R.id.me_gridview_list)
    MyGridView meGridviewList;
    @BindView(R.id.me_image_shiming)
    ImageView meImageShiming;
    @BindView(R.id.me_image_companyrenz)
    ImageView meImageCompanyrenz;

    private int neType = 0;
    private View view;
    private MeBean me;
    private boolean isJD;

    private int[] titlte = {
            R.string.mydata,
            R.string.share,
            R.string.mybuy,
            R.string.sum,
            R.string.find,
            R.string.carshop,
            R.string.setting,
            R.string.aboutme,
            R.string.help};

    private int[] img = {
            R.drawable.database,
            R.drawable.myshare,
            R.drawable.goumai,
            R.drawable.mymileage,
            R.drawable.mydata,
            R.drawable.goumai,
            R.drawable.mysetting,
            R.drawable.mycollect,
            R.drawable.myhelp};

    private SimpleAdapter saImageItems;
    private CapitalAcountInfo caInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me, container, false);
        unbinder = ButterKnife.bind(this, view);

        QBadgeView qbadge = new QBadgeView(getActivity());
        qbadge.bindTarget(meLayoutBa).setBadgeGravity(Gravity.END | Gravity.TOP).setBadgeNumber(app.getMessageNum());

        initData();
        initView();
        return view;
    }

    @Override
    public void onSuccess(String data) {
        if (neType == 0) {
            JSONObject jsonObject = JSONObject.parseObject(data);
//            String state = jsonObject.getString("state");
//            String msg = jsonObject.getString("msg");
//
//            Log.e("啊啊啊啊啊啊啊啊 ： ", data);

            me = parseObject(data, MeBean.class);
            setValue();

            neType = 1;
            GetListByUserIdApi glbui = new GetListByUserIdApi();
            glbui.setHeader(app.getAuthorization());
            glbui.setUserHeader(app.getUserHeader());
            glbui.setUserId(app.getUserId());
            pClass.startHttpRequest(mActivity, glbui);

        } else if (neType == 1) {
            L.e("test1", "neType=" + neType);
            List<CapitalAcount> capitalAcount = JSONObject.parseArray(data, CapitalAcount.class);
//            for (int i=0;i<capitalAcount.size();i++){
            L.e("test1", "capitalAcount.size()=" + capitalAcount.size());
            caInfo = JSONObject.parseObject(capitalAcount.get(0).getMyInfo_AccountEntity(),
                    CapitalAcountInfo.class);
//            }
        } else if (neType == 2) {

            JSONObject jsonObject = JSONObject.parseObject(data);
            String state = jsonObject.getString("state");
            if (state.equals("1")) {
                initData();
            }

        }
    }

    @SuppressLint("ResourceAsColor")
    private void setValue() {
        meTxtName.setText(me.getName());
        ratingbar.setStar(me.getStar());
        if (me.getUserState() == 0) {
            meImageShiming.setImageResource(R.drawable.weirenzhen);
            meTxtShiming.setTextColor(R.color.red);
            meTxtShiming.setText("未通过实名认证");
        } else {
            meImageShiming.setImageResource(R.drawable.renzhen);
            meTxtShiming.setTextColor(R.color.green);
            meTxtShiming.setText("实名认证");
        }
        if (me.getVehicleRegState() == 0) {
            meImageCompanyrenz.setImageResource(R.drawable.weirenzhen);
            meTxtCompanyrenz.setTextColor(R.color.red);
            meTxtCompanyrenz.setText("未通过车辆认证");
        } else {
            meImageCompanyrenz.setImageResource(R.drawable.renzhen);
            meTxtCompanyrenz.setTextColor(R.color.green);
            meTxtCompanyrenz.setText("车辆认证");
        }

        if (me.getWorkStatus() == 1) {
            stateSelectionBtn.setBackgroundResource(R.drawable.kejiedan);
            isJD = true;
        } else {
            stateSelectionBtn.setBackgroundResource(R.drawable.xiuxizhong);
            isJD = false;
        }

        vipRegisterTime.setText(me.getCreateTime());
        meAuthenticationRztime.setText(me.getUpdateTime());
        lastLoginTime.setText(me.getLogintime());

        meTxtYuer.setText(me.getBalance() + "元");
        meTxtYingshou.setText(me.getShouldCash() + "元");

        metxtdaiquhuonum.setText("(" + me.getOrderDQH() + ")");
        metxttransmitingnum.setText("(" + me.getOrderYSZ() + ")");
        metxtpingjianum.setText("(" + me.getOrderDPJ() + ")");
        Picasso.get().load(assembleImgUrl(me.getHeadIco())).memoryPolicy(NO_CACHE, NO_STORE).into(meImgHead);
    }


    private void initData() {
        neType = 0;
        GetMySelfInfoApi gmsi = new GetMySelfInfoApi();
        gmsi.setHeader(app.getAuthorization());
        gmsi.setUserHeader(app.getUserHeader());
        pClass.startHttpRequest(mActivity, gmsi);

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < titlte.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", img[i]);//添加图像资源的ID
            map.put("ItemText", getString(titlte[i]));//按序号做ItemText
            lstImageItem.add(map);
        }
        //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        saImageItems = new SimpleAdapter(getActivity(), //没什么解释
                lstImageItem,//数据来源
                R.layout.gridview_item,//night_item的XML实现
                //动态数组与ImageItem对应的子项
                new String[]{"ItemImage", "ItemText"},
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[]{R.id.itemimage, R.id.itemtext});

    }

    private void initView() {
        meGridviewList = (MyGridView) view.findViewById(R.id.me_gridview_list);
        //添加并且显示
        meGridviewList.setAdapter(saImageItems);
        meGridviewList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.e("test1", "position:" + position);
                switch (position) {
                    case 0: //我的资料
                        Intent intent = new Intent(mActivity, MyDatumActivity.class);
                        mActivity.startActivity(intent);
                        break;
                    case 1: //
                        mActivity.startActivity(new Intent(mActivity, MyCollectActivity.class));
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:

                        break;
                    case 9:
                        mActivity.startActivity(new Intent(mActivity, SettingActivity.class));
                        break;

                    case 10:

                        break;
                }
            }
        });
    }


    @Override
    public void onError(ApiException e) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        L.e("test", "MeFragmentonDestroyView");
    }


    @SuppressLint("ResourceAsColor")
    @OnClick({R.id.me_layout_wallet, R.id.me_layout_daiquhuo,
            R.id.me_layout_transmiting, R.id.me_layout_pingjia,
            R.id.me_relativeLayout_n, R.id.state_selection_btn,
            R.id.me_layout_ba, R.id.me_txt_left, R.id.me_txt_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.me_layout_ba:
                //消息
                mActivity.startActivity(new Intent(mActivity, MessageActivity.class));
                break;

            case R.id.me_txt_left:
                //扫一扫
                break;

            case R.id.me_layout_wallet: //我的钱包
                if(!app.isComplete()){
                    ProjectUtil.showGoToComplateDatumTip(mActivity);
                    return;
                }

                Intent intentYE = new Intent(mActivity, MyWalletActivity.class);
                intentYE.putExtra("money", me.getBalance());
                startActivity(intentYE);

                break;

            case R.id.me_layout_daiquhuo: //待取货
//                Intent intent = new Intent(mActivity, WaybillListActivity.class);
//                intent.putExtra("param", 1);
//                mActivity.startActivity(intent);

                break;
            case R.id.me_layout_transmiting: //运输中

//                Intent intent1 = new Intent(mActivity, WaybillListActivity.class);
//                intent1.putExtra("param", 1);
//                mActivity.startActivity(intent1);

                break;
            case R.id.me_layout_pingjia: //未评价
                break;
            case R.id.me_relativeLayout_n: //查看资料(我的简介)

                Intent intent3 = new Intent(mActivity, UserIntroduceActivity.class);
                intent3.putExtra("cargoUserId", me.getUserId());
                mActivity.startActivity(intent3);

                break;

            case R.id.state_selection_btn:
                if (isJD) {
                    stateSelectionBtn.setBackgroundResource(R.drawable.xiuxizhong);
                    isJD = false;
                } else {
                    stateSelectionBtn.setBackgroundResource(R.drawable.kejiedan);
                    isJD = true;
                }

                neType = 2;
                ModeifyWorkStatus modeify = new ModeifyWorkStatus();
                modeify.setDriverid(me.getUserId());
                modeify.setHeader(app.getAuthorization());
                modeify.setUserHeader(app.getUserHeader());
                pClass.startHttpRequest(mActivity, modeify);
                break;

            case R.id.me_txt_call:
                ProjectUtil.checkCallPhone(getActivity(), meTxtHotline.getText().toString().trim());
                break;
        }
    }
}

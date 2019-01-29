package com.lulian.driver.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lulian.driver.R;
import com.lulian.driver.adapter.SelectCityAdapter;
import com.lulian.driver.base.BaseActivity;
import com.lulian.driver.entity.api.GetAreaListApi;
import com.lulian.driver.entity.server.resulte.City;
import com.lulian.driver.utils.ProjectUtil;
import com.rxretrofitlibrary.retrofit_rx.exception.ApiException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alibaba.fastjson.JSON.parseArray;



/**
 * Created by MARK on 2018/6/8.
 */

public class CityPickerActivity extends BaseActivity {
    @BindView(R.id.city_linear)
    LinearLayout cityLinear;
    @BindView(R.id.recitypicker1)
    ListView recitypicker1;
    @BindView(R.id.recitypicker2)
    ListView recitypicker2;
    @BindView(R.id.recitypicker3)
    ListView recitypicker3;
    @BindView( R.id.city_submit)
    Button citySubmit;

    private int neType = 0;
    private SelectCityAdapter providerad;
    private List<City> proList;
    private List<City> cityList;
    private SelectCityAdapter cityAdapter;
    private List<City> areaList;
    private SelectCityAdapter areaAdapter;
    private String areaId="";
    private String name="";
    private String fullName="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_citypicker);
        ButterKnife.bind(this);
        initpickerData("", "1");
        initView();
        setData();
    }

    private void initView() {
    }

    private void initpickerData(String pId, String level) {
        GetAreaListApi gal = new GetAreaListApi();
        gal.setHeader(app.getAuthorization());
        gal.setUserHeader(app.getUserHeader());
        gal.setLevel(level);
        gal.setpId(pId);//省id或市id
        pClass.startHttpRequest(this, gal);
    }

    private void setData() {
        recitypicker1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                providerad.setSeclection(position);
                providerad.notifyDataSetChanged();
                String proId = proList.get(position).getId();
                initpickerData(proId, "2");
                neType = 1;
            }
        });
        recitypicker2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setSeclection(position);
                cityAdapter.notifyDataSetChanged();
                String proId = cityList.get(position).getId();
                initpickerData(proId, "3");
                neType = 2;
            }
        });
        recitypicker3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                areaAdapter.setSeclection(position);
                areaAdapter.notifyDataSetChanged();
                areaId = areaList.get(position).getId();
                name = areaList.get(position).getName();
                fullName = areaList.get(position).getFullName();
                ProjectUtil.show(CityPickerActivity.this, "fullName:" + fullName + ";   name:" + name + ";    areaId:" + areaId);
            }
        });
    }

    @Override
    public void onSuccess(String data) {

        if (neType == 0) {
            proList = parseArray(data, City.class);
            providerad = new SelectCityAdapter(CityPickerActivity.this, proList);
            recitypicker1.setAdapter(providerad);
        } else if (neType == 1) {
            cityList = parseArray(data, City.class);
            cityAdapter = new SelectCityAdapter(CityPickerActivity.this, cityList);
            recitypicker2.setAdapter(cityAdapter);
        } else if (neType == 2) {
            areaList = parseArray(data, City.class);
            areaAdapter = new SelectCityAdapter(CityPickerActivity.this, areaList);
            recitypicker3.setAdapter(areaAdapter);
        }
    }

    @Override
    public void onError(ApiException e) {
        super.onError(e);
    }

    @OnClick(R.id.city_submit)
    public void onViewClicked() {
        if(areaId.equals("")&&name.equals("")&&fullName.equals("")){
            ProjectUtil.show(this,"");
        }else {
            Intent intent = new Intent();
            intent.putExtra("areaId", areaId);
            intent.putExtra("name", name);
            intent.putExtra("fullname", fullName);
            setResult(1, intent);
            finish();
        }
    }
}

package com.lulian.driver.view.wheel;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lulian.driver.R;


public class EnsureDialog extends Dialog {

    private final Activity mContext;
    private Button btnOk, btnCancle;
    private TextView tvContent;
    private View.OnClickListener onClickListener;
    private String message;
    private String okText;

    public EnsureDialog(Activity _context, String message, String okText, View.OnClickListener onClickListener) {
        super(_context, _context.getResources().getIdentifier("dialog",
                "style", _context.getPackageName()));
        this.mContext = _context;
        this.onClickListener = onClickListener;
        this.message = message;
        this.okText = okText;

        WindowManager.LayoutParams a = getWindow().getAttributes();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        a.gravity = Gravity.CENTER;
        a.dimAmount = 0.5f; // 添加背景遮盖
        getWindow().setAttributes(a);

    }

    public EnsureDialog(Activity _context, String message, View.OnClickListener onClickListener) {
        this(_context, message, "确 定", onClickListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pop_show_ensure);

        btnCancle = (Button) findViewById(R.id.btn_cancle);
        btnOk = (Button) findViewById(R.id.btn_ok);
        tvContent = (TextView) findViewById(R.id.tv_content);

//        tvContent.setText("亲！您的网络出问题了，请检查设置！");
        tvContent.setText(message);
        btnOk.setText(okText);

        btnCancle.setOnClickListener(onClickListener);
        btnOk.setOnClickListener(onClickListener);
    }
}

package com.admin.textimgspandemo.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

import com.admin.textimgspandemo.R;

import java.util.Date;

/**
 * Created by admin on 2018/1/19.
 */

public class LoadingDialog extends Dialog {

    private Date curDate;
    public LoadingDialog(Context context) {
        super(context, R.style.alert_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

//        // 将对话框的大小按屏幕大小的百分比设置
//        WindowManager m = getWindow().getWindowManager();
//        Display d = m.getDefaultDisplay();
//        final WindowManager.LayoutParams p = getWindow().getAttributes();
//        p.height = (int) (d.getHeight() * 0.6);
//        p.width = (int) (d.getHeight() * 0.8);
//        getWindow().setAttributes(p);

        setCanceledOnTouchOutside(false);
        ProgressWheel progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        ProgressHelper helper = new ProgressHelper(getContext());
        helper.setProgressWheel(progressWheel);
    }

}

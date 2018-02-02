package com.admin.textimgspandemo.act;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.admin.textimgspandemo.R;
import com.admin.textimgspandemo.mode.ImageMode;
import com.admin.textimgspandemo.util.ToastUtil;
import com.admin.textimgspandemo.views.LoadingDialog;
import com.admin.textimgspandemo.views.TinderStackLayout;

import org.xutils.common.Callback;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 图片卡片，拖拽移除
 */

public class ActMoveImageCard extends AppCompatActivity {

    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.btn_again_load_image)
    TextView btnAgainLoadImage;
    @BindView(R.id.tinderStackLayout)
    TinderStackLayout tinderStackLayout;

    private LoadingDialog loadingDialog;
    private List<ImageMode> imageModeList = new ArrayList<ImageMode>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_moveimagecard);
        ButterKnife.bind(this);
        initImage();
        initTinderStackLayout();
    }

    /**
     * 初始化图片imageView
     */
    public void initImage() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(ActMoveImageCard.this);
        }
        loadingDialog.show();
        x.image().bind(imageView, "http://pic1.win4000.com/wallpaper/8/575e50b24e386.jpg", new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                loadingDialog.dismiss();
                ToastUtil.showMessage("获取图片成功");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                loadingDialog.dismiss();
                ToastUtil.showMessage(ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }

    /**
     * 初始化数据并赋值 tinderStackLayout
     */
    public void initTinderStackLayout() {
        List<String> imgUrlList = new ArrayList<>();
        imgUrlList.add("http://pic.pp3.cn/uploads/1304/278.jpg");
        imgUrlList.add("https://b-ssl.duitang.com/uploads/item/201509/15/20150915230506_RxMdi.jpeg");
        imgUrlList.add("https://b-ssl.duitang.com/uploads/item/201509/15/20150915230826_mAzBK.jpeg");
        imgUrlList.add("https://b-ssl.duitang.com/uploads/item/201509/15/20150915231833_MaXHj.jpeg");
        imgUrlList.add("https://b-ssl.duitang.com/uploads/item/201504/29/20150429141301_YtMrW.jpeg");
        imgUrlList.add("https://b-ssl.duitang.com/uploads/item/201509/15/20150915230216_RnJwe.jpeg");
        imgUrlList.add("https://b-ssl.duitang.com/uploads/item/201509/15/20150915225336_RrVGL.thumb.700_0.jpeg");
        imgUrlList.add("https://b-ssl.duitang.com/uploads/item/201509/15/20150915230716_AePMx.thumb.700_0.jpeg");
        imgUrlList.add("https://b-ssl.duitang.com/uploads/item/201509/15/20150915230645_ncaiV.thumb.700_0.jpeg");
        imgUrlList.add("https://b-ssl.duitang.com/uploads/item/201509/15/20150915231758_HZTdW.thumb.700_0.jpeg");

        for (int i = 0; i < imgUrlList.size(); i++) {
            ImageMode imageMode = new ImageMode((i + 1) + "_sakura", imgUrlList.get(i));
            imageModeList.add(imageMode);
        }
        tinderStackLayout.setDatas(imageModeList);
    }

    @OnClick({R.id.btn_again_load_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_again_load_image://重新加载图片
                if (imageModeList != null && imageModeList.size() > 0) {
                    tinderStackLayout.reLoadCard(imageModeList);
                }
                break;
        }
    }
}


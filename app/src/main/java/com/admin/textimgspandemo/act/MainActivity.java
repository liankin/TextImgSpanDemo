package com.admin.textimgspandemo.act;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.admin.textimgspandemo.R;
import com.admin.textimgspandemo.mode.ImageMode;
import com.admin.textimgspandemo.util.ToastUtil;
import com.admin.textimgspandemo.views.CustomImageSpan;
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
 * 富文本排版：
 * 1.文字与图片中间线对齐排列
 * 2.图片卡片，拖拽移除
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.image_and_text_view)
    TextView imageAndTextView;
    @BindView(R.id.tinderStackLayout)
    TinderStackLayout tinderStackLayout;
    @BindView(R.id.image_view)
    ImageView imageView;
    @BindView(R.id.tv_again_load_image)
    TextView tvAgainLoadImage;

    //自定义对齐方式--与文字中间线对齐
    private final int ALIGN_FONTCENTER = 2;
    private LoadingDialog loadingDialog;
    private  List<ImageMode> imageModeList = new ArrayList<ImageMode>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initImage();
        initImageAndTextSpan();
        initTinderStackLayout();
    }

    public void initImage() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(MainActivity.this);
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

    public void initImageAndTextSpan() {
        // "你们好，这个 是小Android!"
        // 这句话的空格位序为7，图片设置为铺盖此空格的位置；
        // 如果不加空格，则会覆盖“是”这个字！
        SpannableString spannableString = new SpannableString("你们好，这个 是小Android!");
        //调用自定义的imageSpan,实现文字与图片的横向居中对齐
        CustomImageSpan imageSpan = new CustomImageSpan(this, R.mipmap.ic_launcher, ALIGN_FONTCENTER);
        //setSpan插入内容的时候，起始位置不替换，会替换起始位置到终止位置间的内容，含终止位置。
        //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE模式用来控制是否同步设置新插入的内容与start/end 位置的字体样式，此处没设置具体字体，所以可以随意设置
        spannableString.setSpan(imageSpan, 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        imageAndTextView.setText(spannableString);
    }

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

    @OnClick({ R.id.tv_again_load_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_again_load_image:
                if(imageModeList != null && imageModeList.size() > 0){
                    tinderStackLayout.reLoadCard(imageModeList);
                }
                break;
        }
    }
}

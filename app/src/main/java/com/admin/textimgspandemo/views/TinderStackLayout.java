package com.admin.textimgspandemo.views;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;

import com.admin.textimgspandemo.act.MainActivity;
import com.admin.textimgspandemo.mode.ImageMode;
import com.admin.textimgspandemo.util.DensityUtil;
import com.admin.textimgspandemo.views.TinderCardView;

import java.util.List;

import static android.R.attr.delay;

public class TinderStackLayout extends FrameLayout implements TinderCardView.OnLoadMoreListener {
    private ViewGroup.LayoutParams params = null;
    public static final float BASESCALE_X_VALUE = 50.0f;
    public static final int BASESCALE_Y_VALUE = 8;
    public static final int DURATIONTIME = 300;
    private static final int STACK_SIZE = 4;
    private int index = 0;
    private int scaleY;
    private TinderCardView tc;
    private List<ImageMode> mList;

    //private LoadingDialog loadingDialog;

    public TinderStackLayout(Context context) {
        this(context, null);
    }

    public TinderStackLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TinderStackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化控件大小参数
     */
    public void init() {
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        scaleY = DensityUtil.dip2px(getContext(), BASESCALE_Y_VALUE);
    }

    /**
     * 初始化所有数据
     * @param list
     */
    public void setDatas(List<ImageMode> list) {
        this.mList = list;
        if (mList == null) {
            return;
        }
        //showLoadingDialog(true);
        //循环生成一定个数的卡片视图，并为每一个绑定数据、添加监听，再添加到父视图中
        for (int i = index; index < i + STACK_SIZE; index++) {
            tc = new TinderCardView(getContext());
            tc.bindData(mList.get(index));
            tc.setOnLoadMoreListener(this);
            addCard(tc);
        }
        //showLoadingDialog(false);
    }

    /**
     * 把单个卡片视图添加到父视图中
     * @param view
     */
    private void addCard(TinderCardView view) {
        int count = getChildCount();
        addView(view, 0, params);
        float scaleX = 1 - (count / BASESCALE_X_VALUE);
        //设置Y轴偏移、放大
        view.animate()
                .x(0)
                .y(count * scaleY)
                .scaleX(scaleX)
                .setInterpolator(new AnticipateOvershootInterpolator())
                .setDuration(DURATIONTIME);
    }

    @Override
    public void onLoad() {
        //showLoadingDialog(true);
        for (int i = index; index < i + (STACK_SIZE - 1); index++) {
            if (index == mList.size()) {
                //showLoadingDialog(false);
                return;
            }
            tc = new TinderCardView(getContext());
            tc.bindData(mList.get(index));
            tc.setOnLoadMoreListener(this);
            addCard(tc);
        }
      /*  int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            TinderCardView tinderCardView = (TinderCardView) getChildAt(i);
            if (tinderCardView != null) {
                float scaleValue = 1 - ((childCount - 1 - i) / 50.0f);
                //设置Y轴偏移、放大
                tinderCardView.animate()
                        .x(0)
                        .y((childCount - 1 - i) * scaleY)
                        .scaleX(scaleValue)
                        .rotation(0)
                        .setInterpolator(new AnticipateOvershootInterpolator())
                        .setDuration(DURATIONTIME);
            }
        }*/
        //showLoadingDialog(false);
    }

    /*private void showLoadingDialog(boolean isShow){
        if(isShow){
            if (loadingDialog == null) {
                loadingDialog = new LoadingDialog(getContext());
            }
            loadingDialog.show();
        }else {
            new Handler().postDelayed(new Runnable(){
                public void run() {
                    loadingDialog.dismiss();
                }
            }, 2 * 1000);
        }
    }*/

    /**
     * 重新加载视图
     * @param list
     */
    public void reLoadCard(List<ImageMode> list){
        index = 0;
        setDatas(list);
    }
}

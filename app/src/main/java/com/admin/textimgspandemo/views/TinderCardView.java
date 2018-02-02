package com.admin.textimgspandemo.views;

import android.animation.Animator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.admin.textimgspandemo.R;
import com.admin.textimgspandemo.mode.ImageMode;
import com.admin.textimgspandemo.util.DensityUtil;
import com.bumptech.glide.Glide;

public class TinderCardView extends FrameLayout implements View.OnTouchListener {
    private static final int PADDINGVALUE=16;
    private static final float CARD_ROTATION_DEGREES = 40.0f;
    public static final int DURATIONTIME=300;
    private ImageView iv;
    private TextView tv_name;
    private ImageView iv_tips;
    private int padding;
    private float downX;
    private float downY;
    private float newX;
    private float newY;
    private float dX;
    private float dY;
    private float rightBoundary;//当卡片视图中心向右边移动到小于rightBoundary的范围内时，可移除
    private float leftBoundary;//当卡片视图中心向左边移动到小于leftBoundary的范围内时，可移除
    private int screenWidth;
    private OnLoadMoreListener listener;

    public TinderCardView(Context context) {
        this(context,null);
    }

    public TinderCardView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public TinderCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){

        if(!isInEditMode()){
            inflate(context, R.layout.layout_cardview,this);
            screenWidth= DensityUtil.getScreenWidth(context);
            leftBoundary =  screenWidth * (1.0f/6.0f);
            rightBoundary = screenWidth * (5.0f/6.0f);
            iv=(ImageView) findViewById(R.id.iv);
            tv_name=(TextView) findViewById(R.id.tv_name);
            iv_tips=(ImageView)findViewById(R.id.iv_tips);
            padding = DensityUtil.dip2px(context, PADDINGVALUE);
            setOnTouchListener(this);
        }

    }

    @Override
    public boolean onTouch(final View view, MotionEvent motionEvent) {
        TinderStackLayout tinderStackLayout = ((TinderStackLayout) view.getParent());
        TinderCardView topCard = (TinderCardView) tinderStackLayout.getChildAt(tinderStackLayout.getChildCount() - 1);
        if (topCard.equals(view)) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downX = motionEvent.getX();
                    downY = motionEvent.getY();
                    view.clearAnimation();
                    return true;
                case MotionEvent.ACTION_MOVE:
                    newX = motionEvent.getX();
                    newY = motionEvent.getY();
                    dX = newX - downX;
                    dY = newY - downY;
                    float posX = view.getX() + dX;
                    view.setX(view.getX() + dX);
                    view.setY(view.getY() + dY);
                    float rotation = (CARD_ROTATION_DEGREES * (posX)) / screenWidth;
                    int halfCardHeight = (view.getHeight() / 2);
                    if(downY < halfCardHeight - (2*padding)){
                        view.setRotation(rotation);//当按下的位置小于卡片视图高度的一半时(按在卡片上部分)---顺时针旋转
                    } else {
                        view.setRotation(-rotation);//按在卡片下部分---逆时针旋转
                    }

                    float alpha = (posX - padding) / (screenWidth * 0.3f);
                    if(alpha>0){
                        //右滑卡片---喜欢图标，根据滑动距离，显现
                        iv_tips.setAlpha(alpha);
                        iv_tips.setImageResource(R.mipmap.ic_like);
                    }else{
                        //左滑卡片---不喜欢图标，根据滑动距离，显现
                        iv_tips.setAlpha(-alpha);
                        iv_tips.setImageResource(R.mipmap.ic_nope);

                    }

                    return true;
                case MotionEvent.ACTION_UP:
                    if(isBeyondLeftBoundary(view)){
                        //卡片向屏幕左边移除，设置其x 为-(screenWidth * 2)
                        removeCard(view, -(screenWidth * 2));
                    }
                    else if(isBeyondRightBoundary(view)){
                        //卡片向屏幕右边移除，设置其x 为(screenWidth * 2)
                        removeCard(view,(screenWidth * 2));
                    }else{
                        //卡片恢复原位
                        resetCard(view);
                    }


                    return true;
                default :
                    return super.onTouchEvent(motionEvent);
            }
        }
        return super.onTouchEvent(motionEvent);

    }

    //当卡片视图中心移动到屏幕左边界一定范围内时，可移除
    private boolean isBeyondLeftBoundary(View view){
        return (view.getX() + (view.getWidth() / 2) < leftBoundary);
    }

    //当卡片视图中心移动到屏幕右边界一定范围内时，可移除
    private boolean isBeyondRightBoundary(View view){
        return (view.getX() + (view.getWidth() / 2) > rightBoundary);
    }

    /**
     * 移除卡片视图
     * @param view
     * @param xPos
     */
    private void removeCard(final View view, int xPos){
        view.animate()
                .x(xPos)
                .y(0)
                .setInterpolator(new AccelerateInterpolator())
                .setDuration(DURATIONTIME)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        ViewGroup viewGroup = (ViewGroup) view.getParent();
                        if(viewGroup != null) {
                            viewGroup.removeView(view);
                            int count=viewGroup.getChildCount();
                            if(count==1 && listener!=null){
                                listener.onLoad();
                            }
                        }
                    }
                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }


    /**
     * 卡片视图恢复原位
     * @param view
     */
    private void resetCard(final View view){
        view.animate()
                .x(0)
                .y(0)
                .rotation(0)
                .setInterpolator(new OvershootInterpolator())
                .setDuration(DURATIONTIME);
        iv_tips.setAlpha(0f);
    }

    /**
     * 单个卡片视图绑定数据
     * @param u
     */
    public void bindData(ImageMode u){
        if(u==null){
            return;
        }
        if(!TextUtils.isEmpty(u.getAvatarUrl())){
            Glide.with(iv.getContext())
                    .load(u.getAvatarUrl())
                    .into(iv);
            //x.image().bind(iv, u.getAvatarUrl());
        }
        if(!TextUtils.isEmpty(u.getName())){
            tv_name.setText(u.getName());
        }
    }

    /**
     * 监听回调
     */
    public interface OnLoadMoreListener{
        void onLoad();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener){
        this.listener=listener;
    }
}


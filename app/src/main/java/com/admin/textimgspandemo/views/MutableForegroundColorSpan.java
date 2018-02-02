package com.admin.textimgspandemo.views;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * Created by admin on 2018/2/2.
 */

public class MutableForegroundColorSpan extends CharacterStyle
        implements UpdateAppearance {
    public static final String TAG = "MutableForegroundColorSpan";
    private int mColor = Color.BLACK;
    private int mAlpha = 0 ;
    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setColor(mColor);
        tp.setAlpha(mAlpha);
    }
    public int getColor() {
        return mColor;
    }
    public void setColor(int color) {
        this.mColor = color;
    }
    public void setAlpha(int alpha) {
        mAlpha = alpha;
    }
}


package com.admin.textimgspandemo.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.style.LineBackgroundSpan;

/**
 * 设置每一行的背景颜色
 */

public class TextLineBackgroundSpan implements LineBackgroundSpan {

    private final int color;

    public TextLineBackgroundSpan(int color) {
        this.color = color;
    }

    @Override
    public void drawBackground(Canvas c, Paint p, int left, int right, int top, int baseline, int bottom, CharSequence text, int start, int end, int lnum) {
        final int paintColor = p.getColor();
        p.setColor(color);
        c.drawRect(new Rect(left, top, right, bottom), p);
        p.setColor(paintColor);
    }
}

package com.admin.textimgspandemo.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * 实现图片和文字居中对齐
 */

public class VerticalImageSpan extends ImageSpan {

    private Drawable drawable;
    public VerticalImageSpan(Drawable drawable) {
        super(drawable);
        this.drawable=drawable;
    }

    /**
     * 1.通过fontMetricsInt设置从而实现图片和文字居中对齐，
     * 其实计算的根本为计算baseline的位置，因为TextView是按照baseline对齐的。
     * 2.图片的baseline为图片中央往下fontHeight / 2，
     * 这样也就实现了图片和文字的居中对齐。
     */
    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fontMetricsInt) {
        Drawable drawable = getDrawable();
        if(drawable==null){
            drawable= this.drawable;
        }
        Rect rect = drawable.getBounds();
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;
            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;
            fontMetricsInt.ascent = -bottom;
            fontMetricsInt.top = -bottom;
            fontMetricsInt.bottom = top;
            fontMetricsInt.descent = top;
        }
        return rect.right;
    }

    /**
     * draw方法用来绘制图片，绘制x坐标为span的其实坐标，
     * 绘制y坐标可以通过计算得到，具体计算请看上面的源码。
     */
    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable drawable = getDrawable();
        canvas.save();
        int transY = ((bottom - top) - drawable.getBounds().bottom) / 2 + top;
        canvas.translate(x, transY);
        drawable.draw(canvas);
        canvas.restore();
    }
}

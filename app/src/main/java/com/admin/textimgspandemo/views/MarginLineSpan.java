package com.admin.textimgspandemo.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

/**
 * 每行都偏移相应距离，然后每行都绘制矩形，就连成了一条竖线。
 */

public class MarginLineSpan  implements LeadingMarginSpan {

    private int mStripeWidth;
    private int mGapWidth;
    private int mPaintColor;

    public MarginLineSpan(int stripeWidth, int gapWidth, int paintColor){
        mStripeWidth = stripeWidth;
        mGapWidth = gapWidth;
        mPaintColor = paintColor;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return mStripeWidth + mGapWidth;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {
        Paint.Style style = p.getStyle();
        int color = p.getColor();

        p.setStyle(Paint.Style.FILL);
        p.setColor(mPaintColor);

        c.drawRect(x, top, x + dir * mStripeWidth, bottom, p);

        p.setStyle(style);
        p.setColor(color);
    }
}

package com.admin.textimgspandemo.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;

/**
 *  整个段落右移一段距离，然后在移动留下的空间处绘制了一个小圆点。
 */

public class MarginDotSpan implements LeadingMarginSpan {

    private int mBulletRadius;//小圆点的半径
    private int mGapWidth;//文字离小圆点的距离
    private boolean mWantColor;//小圆点是否需要颜色
    private int mPaintColor;//小圆点的颜色
    private Path sBulletPath;

    public MarginDotSpan(int bulletRadius, int gapWidth, boolean wantColor, int paintColor){
        mWantColor = wantColor;
        mPaintColor = paintColor;
        mBulletRadius = bulletRadius;
        mGapWidth = gapWidth;
    }

    // 无论是否是第一行都返回了偏移距离为2 * BULLET_RADIUS + mGapWidth，因此整个段落都移动了相应的距离。
    @Override
    public int getLeadingMargin(boolean first) {
        return 2 * mBulletRadius + mGapWidth;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {
        // 判断了这一行的起始位置是否是整个Span的起始位置，
        // 如果是则绘制圆形，如果把这个判断去掉，那么每一行都将绘制小圆形。
        if (((Spanned) text).getSpanStart(this) == start) {
            Paint.Style style = p.getStyle();
            int oldcolor = 0;
            if (mWantColor) {
                oldcolor = p.getColor();
                p.setColor(mPaintColor);
            }
            p.setStyle(Paint.Style.FILL);
            if (c.isHardwareAccelerated()) {
                if (sBulletPath == null) {
                    sBulletPath = new Path();
                    // Bullet is slightly better to avoid aliasing artifacts on mdpi devices.
                    sBulletPath.addCircle(0.0f, 0.0f, 1.2f * mBulletRadius, Path.Direction.CW);
                }
                c.save();
                c.translate(x + dir * mBulletRadius, (top + bottom) / 2.0f);
                c.drawPath(sBulletPath, p);
                c.restore();
            } else {
                c.drawCircle(x + dir * mBulletRadius, (top + bottom) / 2.0f, mBulletRadius, p);
            }
            if (mWantColor) {
                p.setColor(oldcolor);
            }
            p.setStyle(style);
        }
    }
}

package com.admin.textimgspandemo.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

/**
 * 两端文字环绕图片
 */

public class TextRoundSpan  implements LeadingMarginSpan.LeadingMarginSpan2{

    private int margin;
    private int lines;

    public TextRoundSpan(int lines, int margin) {
        this.margin = margin;
        this.lines = lines;
    }

    // 当当前行数小于等于getLeadingMarginLineCount()，
    // getLeadingMargin(boolean first)中first的值为true。
    @Override
    public int getLeadingMarginLineCount() {
        return lines;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        if(first) {
            return margin;
        } else {
            return 0;
        }
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {

    }
}

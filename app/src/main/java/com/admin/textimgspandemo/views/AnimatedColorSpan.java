package com.admin.textimgspandemo.views;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.UpdateAppearance;

/**
 * 字体多色渐变动画
 */
public class AnimatedColorSpan extends CharacterStyle implements UpdateAppearance {
    private final int[] mColors;
    private Shader shader = null;
    private Matrix matrix = new Matrix();
    private float translateXPercentage = 0;

    public AnimatedColorSpan(Context context, int[] colors) {
        mColors = colors;
    }
    public void setTranslateXPercentage(float percentage) {
        translateXPercentage = percentage;
    }
    public float getTranslateXPercentage() {
        return translateXPercentage;
    }
    @Override
    public void updateDrawState(TextPaint paint) {
        paint.setStyle(Paint.Style.FILL);
        float width = paint.getTextSize() * mColors.length;
        if (shader == null) {
            shader = new LinearGradient(0, 0, 0, width, mColors, null,
                    Shader.TileMode.MIRROR);
        }
        matrix.reset();
        matrix.setRotate(90);
        matrix.postTranslate(width * translateXPercentage, 0);
        shader.setLocalMatrix(matrix);
        paint.setShader(shader);
    }
}

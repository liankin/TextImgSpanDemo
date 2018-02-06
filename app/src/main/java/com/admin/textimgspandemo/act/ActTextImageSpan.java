package com.admin.textimgspandemo.act;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.util.Property;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.admin.textimgspandemo.R;
import com.admin.textimgspandemo.views.AnimatedColorSpan;
import com.admin.textimgspandemo.views.CustomImageSpan;
import com.admin.textimgspandemo.views.FrameSpan;
import com.admin.textimgspandemo.views.MarginDotSpan;
import com.admin.textimgspandemo.views.MarginLineSpan;
import com.admin.textimgspandemo.views.MutableForegroundColorSpan;
import com.admin.textimgspandemo.views.RainbowSpan;
import com.admin.textimgspandemo.views.TextLineBackgroundSpan;
import com.admin.textimgspandemo.views.TextRoundSpan;
import com.admin.textimgspandemo.views.TypeWriterSpanGroup;
import com.admin.textimgspandemo.views.VerticalImageSpan;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 富文本排版：
 * 1.文字与图片中间线对齐排列
 * 2.
 */

public class ActTextImageSpan extends AppCompatActivity {

    @BindView(R.id.tv_custom_image_span)
    TextView tvCustomImageSpan;
    @BindView(R.id.tv_frame_span)
    TextView tvFrameSpan;
    @BindView(R.id.tv_vertical_image_span)
    TextView tvVerticalImageSpan;
    @BindView(R.id.tv_rainbow_span)
    TextView tvRainbowSpan;
    @BindView(R.id.tv_animated_color_span)
    TextView tvAnimatedColorSpan;
    @BindView(R.id.tv_type_writer_span)
    TextView tvTypeWriterSpan;
    @BindView(R.id.tv_margin_dot_span)
    TextView tvMarginDotSpan;
    @BindView(R.id.tv_margin_line_span)
    TextView tvMarginLineSpan;
    @BindView(R.id.tv_text_round_span)
    TextView tvTextRoundSpan;
    @BindView(R.id.img_text_round)
    ImageView imgTextRound;
    @BindView(R.id.tv_text_line_background_span)
    TextView tvTextLineBackgroundSpan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_textimgspan);
        ButterKnife.bind(this);

        initCustomImageSpan();
        initFrameSpan();
        initVerticalImageSapn();
        initRainbowSpan();
        initAnimatedColorSpan();
        initTypeWriterSpan();
        initMarginDotSpan();
        initMarginLineSpan();
        initTextRoundSpan();
        initTextLineBackgroundSpan();
    }

    /**
     * 文字图片---以文字中间线对齐排列
     */
    public void initCustomImageSpan() {
        //自定义对齐方式--与文字中间线对齐
        final int ALIGN_FONTCENTER = 2;

        String str1 = "你们好，这个";
        String str2 = "是小Android!";
        SpannableString spannableString = new SpannableString(str1 + " " + str2);
        //调用自定义的imageSpan,实现文字与图片的横向居中对齐
        CustomImageSpan imageSpan = new CustomImageSpan(this, R.mipmap.ic_launcher, ALIGN_FONTCENTER);
        //setSpan插入内容的时候，起始位置不替换，会替换起始位置到终止位置间的内容，含终止位置。
        //Spanned.SPAN_EXCLUSIVE_EXCLUSIVE模式用来控制是否同步设置新插入的内容与start/end 位置的字体样式，此处没设置具体字体，所以可以随意设置
        //用图片替换字符串中的空格" "
        spannableString.setSpan(imageSpan, str1.length(), str1.length() + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvCustomImageSpan.setText(spannableString);
    }

    /**
     * 给字符串添加边框的效果
     */
    public void initFrameSpan() {
        String str1 = "FrameSpan实现给相应的字符序列添加边框的效果：";
        String str2 = "计算字符序列的宽度,根据计算的宽度、上下坐标、起始坐标绘制矩形,绘制文字";
        SpannableString spannableString = new SpannableString(str1 + str2);
        //在str1的周围画框
        spannableString.setSpan(new FrameSpan(), 0, str1.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvFrameSpan.setText(spannableString);
    }

    /**
     * 图文垂直居中
     */
    public void initVerticalImageSapn() {
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        drawable.setBounds(0, 0, 50, 50);
        String str1 = "VerticalImageSpan可以实现图片和文字居中对齐";
        String str2 = "图片保持了和文字居中对齐";
        SpannableString spannableString = new SpannableString(str1 + " " + str2);
        //用图片替换字符串中的空格" "
        spannableString.setSpan(new VerticalImageSpan(drawable), str1.length(), str1.length() + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvVerticalImageSpan.setText(spannableString);
    }

    /**
     * 字体多色渐变
     */
    public void initRainbowSpan() {
        int[] mColors = {getResources().getColor(R.color.colorPink),
                getResources().getColor(R.color.colorBule),
                getResources().getColor(R.color.colorAccent)};// {0xffe774bb, 0xff63a3e7, 0xffFF4081};
        String str1 = "彩虹样的Span";
        String str2 = "主要是用到了Paint的Shader技术";
        SpannableString spannableString = new SpannableString(str1 + str2);
        spannableString.setSpan(new RainbowSpan(ActTextImageSpan.this, mColors), 0, str1.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvRainbowSpan.setText(spannableString);
    }

    /**
     * 字体多色渐变动画
     */
    public void initAnimatedColorSpan() {
        int[] mColors = {0xffFF34B3, 0xffEEB422, 0xffB3EE3A};
        String str1 = "快看：";
        String str2 = "字体多色渐变动画";
        AnimatedColorSpan span = new AnimatedColorSpan(ActTextImageSpan.this, mColors);
        final SpannableString spannableString = new SpannableString(str1 + str2);
        int start = str1.length();
        int end = start + str2.length();
        spannableString.setSpan(span, start, end, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(
                span, ANIMATED_COLOR_SPAN_FLOAT_PROPERTY, 0, 100);
        objectAnimator.setEvaluator(new FloatEvaluator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tvAnimatedColorSpan.setText(spannableString);
            }
        });
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(DateUtils.MINUTE_IN_MILLIS * 3);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();
    }

    //动画属性变化器
    private static final Property<AnimatedColorSpan, Float> ANIMATED_COLOR_SPAN_FLOAT_PROPERTY
            = new Property<AnimatedColorSpan, Float>(Float.class, "ANIMATED_COLOR_SPAN_FLOAT_PROPERTY") {
        @Override
        public void set(AnimatedColorSpan span, Float value) {
            span.setTranslateXPercentage(value);
        }

        @Override
        public Float get(AnimatedColorSpan span) {
            return span.getTranslateXPercentage();
        }
    };

    /**
     * 模拟打字效果显示文字
     */
    public void initTypeWriterSpan() {
        String str = "模拟打字效果显示文字";
        final SpannableString spannableString = new SpannableString(str);
        //添加字体多色渐变效果
        /*int[] mColors = {0xffe774bb, 0xff63a3e7, 0xffFF4081};
        spannableString.setSpan(new RainbowSpan(ActTextImageSpan.this, mColors),
                0, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvTypeWriterSpan.setText(spannableString);*/
        // 添加Span
        final TypeWriterSpanGroup group = new TypeWriterSpanGroup(0);
        for (int index = 0; index <= str.length() - 1; index++) {
            MutableForegroundColorSpan span = new MutableForegroundColorSpan();
            group.addSpan(span);
            spannableString.setSpan(span, index, index + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        // 添加动画
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(group, TYPE_WRITER_GROUP_ALPHA_PROPERTY, 0.0f, 1.0f);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //刷新
                tvTypeWriterSpan.setText(spannableString);
            }
        });
        objectAnimator.setDuration(5000);
        objectAnimator.start();
    }

    //动画属性变化器
    private static final Property<TypeWriterSpanGroup, Float> TYPE_WRITER_GROUP_ALPHA_PROPERTY =
            new Property<TypeWriterSpanGroup, Float>(Float.class, "TYPE_WRITER_GROUP_ALPHA_PROPERTY") {
                @Override
                public void set(TypeWriterSpanGroup spanGroup, Float value) {
                    spanGroup.setAlpha(value);
                }

                @Override
                public Float get(TypeWriterSpanGroup spanGroup) {
                    return spanGroup.getAlpha();
                }
            };

    /**
     * 整个段落右移一段距离，然后在移动留下的空间处绘制了一个小圆点
     */
    private void initMarginDotSpan() {
        boolean wantColor = true;
        int paintColor = getResources().getColor(R.color.colorRed);
        int bulletRadius = 10;
        int gapWidth = 15;
        String str = "整个段落右移了一段距离，然后在移动留下的空间处绘制了一个小圆点。";
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new MarginDotSpan(bulletRadius, gapWidth, wantColor, paintColor), 0, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvMarginDotSpan.setText(spannableString);
    }

    /**
     * 每行都偏移相应距离，然后每行都绘制矩形，就连成了一条竖线
     */
    private void initMarginLineSpan() {
        int stripeWidth = 10;
        int gapWidth = 15;
        int paintColor = getResources().getColor(R.color.colorAccent);
        String str = "每行都偏移相应距离，然后每行都绘制矩形，就连成了一条竖线";
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new MarginLineSpan(stripeWidth, gapWidth, paintColor), 0, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvMarginLineSpan.setText(spannableString);
    }

    /**
     * 两端文字环绕图片
     */
    private void initTextRoundSpan() {
        imgTextRound.setImageResource(R.drawable.img_rice);

//       /* Drawable drawable = imgTextRound.getDrawable();
//        int imgWidth = drawable.getIntrinsicWidth();*/
//
//        /*int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        imgTextRound.measure(w, h);
//        int imgHeight = imgTextRound.getMeasuredHeight();
//        int imgWidth = imgTextRound.getMeasuredWidth();*/

        ViewGroup.LayoutParams params = imgTextRound.getLayoutParams();
        int imgHeight = params.height;
        int imgWidth = params.width;

        //float fontSpacing = tvTextRoundSpan.getPaint().getFontSpacing();
        int fontSpacing = tvTextRoundSpan.getLineHeight();
        int lines = (int) (imgHeight / fontSpacing) + 1;
        String str = "如果希望做到两端文字环绕图片的效果，其实可以考虑编写Span实现LeadingMarginSpan2。" +
                "具体做法其实比较简单，相对布局中放置img和tv,然后根据ImageView的大小计算TextView需要偏移的距离和行数，" +
                "整个效果就可以实现。使用ViewGroup.LayoutParams params = imgTextRound.getLayoutParams()的方法获取图片控件的宽高滴。";
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new TextRoundSpan(lines, imgWidth + 30), 0, str.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        tvTextRoundSpan.setText(spannableString);
    }

    /**
     * 设置每一行的背景颜色
     */
    private void initTextLineBackgroundSpan(){
        String str = "设置每一行的背景颜色\n";
        tvTextLineBackgroundSpan.setText("Lines:\n", TextView.BufferType.EDITABLE);
        appendLine(tvTextLineBackgroundSpan.getEditableText(),str , Color.BLACK);
        appendLine(tvTextLineBackgroundSpan.getEditableText(),str, Color.RED);
        appendLine(tvTextLineBackgroundSpan.getEditableText(),str, Color.BLACK);
    }
    private void appendLine(Editable text, String string, int color) {
        final int start = text.length();
        text.append(string);
        final int end = text.length();
        text.setSpan(new TextLineBackgroundSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

}

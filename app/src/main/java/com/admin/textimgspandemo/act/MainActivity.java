package com.admin.textimgspandemo.act;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.admin.textimgspandemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 1.文字图片富文本排列
 * 2.图片卡片，拖拽移除
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_text_and_image_span)
    TextView btnTextAndImageSpan;
    @BindView(R.id.btn_move_image_card)
    TextView btnMoveImageCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_text_and_image_span, R.id.btn_move_image_card})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.btn_text_and_image_span:
                intent = new Intent(MainActivity.this, ActTextImageSpan.class);
                startActivity(intent);
                break;
            case R.id.btn_move_image_card:
                intent = new Intent(MainActivity.this, ActMoveImageCard.class);
                startActivity(intent);
                break;
        }
    }
}

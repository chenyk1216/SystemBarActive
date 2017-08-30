package cn.chenyk.systembarlib;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import cn.chenyk.systembarkit.manager.SystemBarManager;

/**
 * Created by chenyk on 2017/8/28.
 * 状态栏图片延伸 - 全图片背景效果
 */

public class FullBgImageViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_full_bg_imageview);

        new SystemBarManager.builder(this)
                .setAlpha(122)//不透明度
                .build();

//        StatusBarUtil.setTranslucentForImageView(this, 0, null);
    }

}

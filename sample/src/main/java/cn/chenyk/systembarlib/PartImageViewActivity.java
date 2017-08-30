package cn.chenyk.systembarlib;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import cn.chenyk.systembarkit.manager.SystemBarManager;

/**
 * Created by chenyk on 2017/8/29.
 * 状态栏图片延伸 - 部分图片效果
 */

public class PartImageViewActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_part_imageview);

        new SystemBarManager.builder(this)
                .setSystemBarColor(Color.BLACK)//状态栏颜色
                .setAlpha(122)//不透明度
                .build();
    }
}

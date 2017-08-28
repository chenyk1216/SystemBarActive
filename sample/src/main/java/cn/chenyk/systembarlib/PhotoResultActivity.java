package cn.chenyk.systembarlib;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.WindowManager;

import cn.chenyk.systembarkit.manager.SystemBarManager;
import cn.chenyk.systembarkit.widget.StatusBarView;

/**
 * Created by chenyk on 2017/8/28.
 * 图片效果页面
 */

public class PhotoResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_photo_result);
//        setTranslucent(this);

        new SystemBarManager.builder(this)
                .setStatusBarColor(Color.TRANSPARENT)//状态栏颜色
                .setAlpha(0)//不透明度
                .build();
    }

    /**
     * 使状态栏透明 * <p> * 适用于图片作为背景的界面,此时需要图片填充到状态栏 * * @param activity 需要设置的activity
     */
    public void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
            StatusBarView.addStatusBarView(PhotoResultActivity.this, Color.TRANSPARENT);
        }
    }
}

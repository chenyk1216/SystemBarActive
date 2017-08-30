package cn.chenyk.systembarlib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.chenyk.systembarkit.SystemBarTintType;
import cn.chenyk.systembarkit.manager.SystemBarManager;

/**
 * Created by chenyk on 2017/4/12.
 * 结果页面
 */

public class ResultActivity extends Activity {
    public static String EXTRA_TINTTYPE_KEY = "extra_tintType_key";//色彩类型key
    public static String EXTRA_ALPHA_KEY = "extra_alpha_key";//透明度key
    public static String EXTRA_COLOR_KEY = "extra_Color_key";//颜色key

    private SystemBarTintType mTintType;
    private int mAlpha;
    private int mStatusBarColor;

    /**
     * 快捷启动当前页面
     */
    public static void startCurrentActivity(Activity activity, String tintType, String alpha,
                                            String statusBarColor) {
        Intent intent = new Intent(activity, ResultActivity.class);
        intent.putExtra(EXTRA_TINTTYPE_KEY, tintType);
        intent.putExtra(EXTRA_ALPHA_KEY, alpha);
        intent.putExtra(EXTRA_COLOR_KEY, statusBarColor);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_result);
        Intent intent = getIntent();
        String tintTypeCode = intent.getStringExtra(EXTRA_TINTTYPE_KEY);
        mTintType = "0".equals(tintTypeCode) ? SystemBarTintType.PURECOLOR
                : SystemBarTintType.GRADIENT;
        mAlpha = Integer.valueOf(intent.getStringExtra(EXTRA_ALPHA_KEY));
        String colorCode = intent.getStringExtra(EXTRA_COLOR_KEY);
        mStatusBarColor = "red".equals(colorCode) ? R.color.title_color_red :
                "green".equals(colorCode) ? R.color.title_color_green : R.color.title_color_blue;
        new SystemBarManager.builder(this)
                .setSystemBarColor(mStatusBarColor)//状态栏颜色
                .setSystemBarTintType(mTintType)//色彩类型：纯色、渐变
                .setAlpha(mAlpha)//不透明度
                .build();
    }
}

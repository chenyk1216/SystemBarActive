package cn.chenyk.systembarkit.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.ViewGroup;
import android.view.WindowManager;

import cn.chenyk.systembarkit.utils.CommUtil;
import cn.chenyk.systembarkit.widget.StatusBarView;


/**
 * Created by chenyk on 2017/4/9.
 * 系统栏管理类
 * 状态栏操作，后续增加导航栏
 */

public class SystemBarManager {
    private Activity mActivity;
    private TintType mTintType;   //色彩类型
    private int mAlpha;  //透明度值
    private int mStatusBarColor;   //状态栏颜色

    public enum TintType {
        GRADIENT, PURECOLOR  //渐变，纯色   ps:纯色效果仅适用于android 5.0以上
    }

    /**
     * 构造函数
     *
     * @param activity
     * @param tintType
     * @param alpha
     * @param statusBarColor
     */
    private SystemBarManager(Activity activity, TintType tintType, int alpha, int statusBarColor) {
        this.mActivity = activity;
        this.mTintType = tintType;
        this.mAlpha = alpha;
        this.mStatusBarColor = CommUtil.getColor(mActivity, statusBarColor);
        windowConfig();
    }

    /**
     * 窗口相关配置
     */
    private void windowConfig() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            if (TintType.PURECOLOR == mTintType) {
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                mActivity.getWindow().setStatusBarColor(CommUtil.calculateColorWithAlpha(mStatusBarColor, mAlpha));//设置状态栏颜色
            } else if (TintType.GRADIENT == mTintType) {
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                StatusBarView.addStatusBarView(mActivity, CommUtil.calculateColorWithAlpha(mStatusBarColor, mAlpha));
            }
            setRootView(mActivity);
            //透明底部导航栏
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            StatusBarView.addStatusBarView(mActivity, CommUtil.calculateColorWithAlpha(mStatusBarColor, mAlpha));
            setRootView(mActivity);
        }
    }

    /**
     * 设置根布局参数
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private static void setRootView(Activity activity) {
        try {
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        } catch (Exception e) {
        }
    }

    /**
     * builder配置类
     */
    public static class builder {
        private static final int DEFAULT_ALPHA = 0;  //默认透明度数值
        private Activity activity;
        private TintType tintType = TintType.PURECOLOR;  //默认纯色效果
        private int alpha = DEFAULT_ALPHA;
        private int statusBarColor;

        /**
         * 构造方法
         *
         * @param activity
         */
        public builder(Activity activity) {
            this.activity = activity;
        }

        /**
         * 设置色彩类型
         *
         * @param tintType
         * @return
         */
        public builder setTintType(TintType tintType) {
            this.tintType = tintType;
            return this;
        }

        /**
         * 设置透明度
         *
         * @param alpha
         * @return
         */
        public builder setAlpha(int alpha) {
            if (alpha >= 0 & alpha <= 255)
                this.alpha = alpha;
            return this;
        }

        /**
         * 设置状态栏背景颜色
         *
         * @param StatusBarColor
         * @return
         */
        public builder setStatusBarColor(int StatusBarColor) {
            this.statusBarColor = StatusBarColor;
            return this;
        }

        /**
         * 创建SystemBarManager对象并返回
         *
         * @return
         */
        public SystemBarManager build() {
            return new SystemBarManager(activity, tintType, alpha, statusBarColor);
        }
    }

}

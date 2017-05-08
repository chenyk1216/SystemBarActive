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
 * 系统栏管理类，目前仅提供状态栏操作，后续增加导航栏
 */

public class SystemBarManager {
    private Activity mActivity;
    private TintType mTintType;
    private int mAlpha;
    private int mStatusBarColor;

    public enum TintType {
        GRADIENT, PURECOLOR  //渐变类型，纯色类型
    }

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
            if (TintType.PURECOLOR == mTintType) {
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                mActivity.getWindow().setStatusBarColor(CommUtil.calculateColorWithAlpha(mStatusBarColor, mAlpha));
            } else if (TintType.GRADIENT == mTintType) {
                mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                StatusBarView.addStatusBarView(mActivity, CommUtil.calculateColorWithAlpha(mStatusBarColor, mAlpha));
            }
            setRootView(mActivity);
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            StatusBarView.addStatusBarView(mActivity, CommUtil.calculateColorWithAlpha(mStatusBarColor, mAlpha));
            setRootView(mActivity);
        }
    }

    /**
     * rootview settings
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
     * 快速创建实例，保留原先设置选项
     *
     * @return
     */
    public builder newBuilder() {
        return new builder(this);
    }

    /**
     * builder class
     */
    public static class builder {
        private static final int DEFAULT_ALPHA = 0;  //default alpha
        private Activity activity;
        private TintType tintType = TintType.PURECOLOR;  //default tintType
        private int alpha = DEFAULT_ALPHA;
        private int statusBarColor;

        private builder(SystemBarManager manager) {
            activity = manager.mActivity;
            tintType = manager.mTintType;
            alpha = manager.mAlpha;
            statusBarColor = manager.mStatusBarColor;
        }

        public builder(Activity activity) {
            this.activity = activity;
        }

        public builder setTintType(TintType tintType) {
            this.tintType = tintType;
            return this;
        }

        public builder setAlpha(int alpha) {
            if (alpha >= 0 & alpha <= 255)
                this.alpha = alpha;
            return this;
        }

        public builder setStatusBarColor(int StatusBarColor) {
            this.statusBarColor = StatusBarColor;
            return this;
        }

        /**
         * 创建实例并返回
         *
         * @return a SystemBarManager instance
         */
        public SystemBarManager build() {
            return new SystemBarManager(activity, tintType, alpha, statusBarColor);
        }
    }

}

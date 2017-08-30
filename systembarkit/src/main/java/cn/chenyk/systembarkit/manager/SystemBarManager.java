package cn.chenyk.systembarkit.manager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import cn.chenyk.systembarkit.SystemBarTintType;
import cn.chenyk.systembarkit.utils.CommUtil;
import cn.chenyk.systembarkit.widget.StatusBarView;


/**
 * Created by chenyk on 2017/4/9.
 * 系统栏管理类，目前仅提供状态栏操作，后续增加导航栏
 * 默认操作：
 * 1、状态栏透明、导航栏不透明
 * 2、
 * <p>
 * 功能：
 * 1、
 */

public class SystemBarManager {
    private static final String TAG = SystemBarManager.class.getSimpleName();
    private static final int TAG_MOVE_DOWN_VIEW = 0x11;//下移视图tag

    private Activity mActivity;
    private Window mWindow;//当前窗口实例
    private SystemBarTintType mTintType;
    private int mColorAlpha;//颜色透明度
    private int mSystemBarColor;//系统栏颜色
    private boolean mIsEnableNavigationTranslucent;//是否使能导航栏为半透明状态

    private SystemBarManager(Activity activity, SystemBarTintType tintType, int colorAlpha, int systemBarColor, boolean isEnableNavigationTranslucent) {
        this.mActivity = activity;
        this.mWindow = mActivity.getWindow();
        this.mTintType = tintType;
        this.mColorAlpha = colorAlpha;
        this.mSystemBarColor = CommUtil.getColor(mActivity, systemBarColor);
        this.mIsEnableNavigationTranslucent = isEnableNavigationTranslucent;
    }

    /**
     * 为头部为ImageView的页面（兼容部分图片或者整个背景图片）设置相关窗口配置
     */
    private void windowConfigForImageView() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            mWindow.setStatusBarColor(Color.TRANSPARENT);
            if (mIsEnableNavigationTranslucent) {
                mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        StatusBarView.addStatusBarView(mActivity, Color.argb(mColorAlpha, 0, 0, 0));
    }

    /**
     * 为头部或背景为ImageView的页面设置状态栏或导航栏半透明效果
     *
     * @param moveDownView 为状态栏腾出空间的下移视图，如果不设置的话，页面的视图会在状态栏区域开始布局
     */
    public void setTranslucentForImageView(View moveDownView) {
        windowConfigForImageView();
        if (moveDownView != null) {
            Object moveDownViewTag = moveDownView.getTag(TAG_MOVE_DOWN_VIEW);
            if (moveDownViewTag != null && (Boolean) moveDownViewTag) return;
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) moveDownView.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, layoutParams.topMargin + CommUtil.getStatusBarHeight(mActivity),
                    layoutParams.rightMargin, layoutParams.bottomMargin);
            moveDownView.setTag(TAG_MOVE_DOWN_VIEW, true);
        }
    }

    /**
     * 用于普通情况的系统栏半透明效果
     */
    public void setTranslucentForNormal() {
        windowConfigForNormal();
        setRootView();
    }

    /**
     * 为普遍情况设置窗口相关配置
     */
    private void windowConfigForNormal() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        int resultColor = CommUtil.calculateColorWithAlpha(mSystemBarColor, mColorAlpha);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//安卓6.0及以上
            if (SystemBarTintType.PURECOLOR == mTintType) {
                mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                mWindow.setStatusBarColor(resultColor);
            } else if (SystemBarTintType.GRADIENT == mTintType) {
                mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                StatusBarView.addStatusBarView(mActivity, resultColor);
            }
            if (mIsEnableNavigationTranslucent) {
                mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                mWindow.setNavigationBarColor(resultColor);
            }
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//安卓4.4~6.0
            windowConfigForKitkat(resultColor);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void windowConfigForKitkat(int color) {
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        StatusBarView.addStatusBarView(mActivity, color);
    }


    /**
     * 根布局设置
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void setRootView() {
        try {
            ViewGroup rootView = (ViewGroup) ((ViewGroup) mActivity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        } catch (Exception e) {
            e.printStackTrace();
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
        private static final int DEFAULT_ALPHA = 0;  //默认全透明
        private Activity activity;
        private SystemBarTintType tintType = SystemBarTintType.PURECOLOR;  //默认纯色效果
        private int colorAlpha = DEFAULT_ALPHA;
        private int systemBarColor;
        private boolean isEnableNavigationTranslucent;

        private builder(SystemBarManager manager) {
            activity = manager.mActivity;
            tintType = manager.mTintType;
            colorAlpha = manager.mColorAlpha;
            systemBarColor = manager.mSystemBarColor;
        }

        public builder(Activity activity) {
            this.activity = activity;
        }

        public builder setSystemBarTintType(SystemBarTintType tintType) {
            this.tintType = tintType;
            return this;
        }

        public builder setAlpha(@IntRange(from = 0, to = 255) int colorAlpha) {
            this.colorAlpha = colorAlpha;
            return this;
        }

        public builder setSystemBarColor(int StatusBarColor) {
            this.systemBarColor = StatusBarColor;
            return this;
        }

        public builder setEnableNavigationTranslucent(boolean enableNavigationTranslucent) {
            this.isEnableNavigationTranslucent = enableNavigationTranslucent;
            return this;
        }

        /**
         * 创建实例并返回
         *
         * @return
         */
        public SystemBarManager build() {
            return new SystemBarManager(activity, tintType, colorAlpha, systemBarColor, isEnableNavigationTranslucent);
        }
    }

}

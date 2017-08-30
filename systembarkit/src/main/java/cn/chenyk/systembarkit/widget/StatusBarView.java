package cn.chenyk.systembarkit.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import cn.chenyk.systembarkit.utils.CommUtil;


/**
 * Created by chenyk on 2017/4/9.
 * the view of status bar
 */

public class StatusBarView extends View {

    public StatusBarView(Context context) {
        super(context);
    }

    public StatusBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * To generate an status bar with the same size of rectangle
     *
     * @return a statusbar view instance
     */
    private static StatusBarView createStatusBarView(Activity activity, int color) {
        StatusBarView statusBarView = new StatusBarView(activity);
        statusBarView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, CommUtil.getStatusBarHeight(activity)));
        statusBarView.setBackgroundColor(color);
        return statusBarView;
    }

    /**
     * add a statusBarView
     *
     * @param activity the context
     */
    public static void addStatusBarView(Activity activity) {
        addStatusBarView(activity, Color.TRANSPARENT);
    }

    /**
     * add a statusBarView
     *
     * @param activity the context
     * @param color    status bar color
     */
    public static void addStatusBarView(Activity activity, int color) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        StatusBarView statusBarView;
        if (decorView.getChildAt(0) instanceof StatusBarView) {
            statusBarView = (StatusBarView) decorView.getChildAt(0);
            if (statusBarView.getVisibility() == GONE) {
                statusBarView.setVisibility(VISIBLE);
            }
        } else {
            statusBarView = createStatusBarView(activity, color);
            decorView.addView(statusBarView);
        }
    }

    /**
     * hide the statusBarView
     *
     * @param activity
     */
    public static void hideStatusBarView(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        if (decorView.getChildAt(0) instanceof StatusBarView) {
            StatusBarView statusBarView = (StatusBarView) decorView.getChildAt(0);
            if (statusBarView.getVisibility() == VISIBLE) {
                statusBarView.setVisibility(GONE);
            }
        }
    }
}

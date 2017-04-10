package cn.chenyk.systembarkit.widget;

import android.app.Activity;
import android.content.Context;
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
     * @param color status bar color
     */
    public static void addStatusBarView(Activity activity, int color) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        if (decorView.getChildAt(0) instanceof StatusBarView) {
            decorView.getChildAt(0).setBackgroundColor(color);
        } else {
            StatusBarView statusView = createStatusBarView(activity, color);
            decorView.addView(statusView);
        }
    }
}

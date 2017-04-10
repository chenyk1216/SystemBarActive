package cn.chenyk.systembarkit.utils;

import android.app.Activity;
import android.graphics.Color;

/**
 * Created by chenyk on 2017/4/9.
 * provide some common utils
 */

public class CommUtil {

    public static int getColor(Activity activity, Object object) {
        if (object instanceof String) {// "#666666"
            return Color.parseColor((String) object);
        } else if (object instanceof Integer) {
            if ((Integer) object > 0)// R.string.app_color
                return activity.getResources().getColor((Integer) object);
            else return (Integer) object;// Color.BLUE
        } else throw new IllegalStateException("The current color is not found");
    }

    public static String getString(Activity activity, Object object) {
        if (object instanceof String)//"title"
            return (String) object;
        else if (object instanceof Integer && (Integer) object > 0) //R.string.app_name
            return activity.getResources().getString((Integer) object);
        else throw new IllegalStateException("The current string is not found");
    }

    /**
     * calculate color
     * @param color initial color
     * @param alpha initial alpha
     * @return final color
     */
    public static int calculateColorWithAlpha(int color, int alpha) {
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * get the height of the status bar
     * @param activity context
     * @return height of the status bar
     */
    public static int getStatusBarHeight(Activity activity) {
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return activity.getResources().getDimensionPixelSize(resourceId);
    }
}

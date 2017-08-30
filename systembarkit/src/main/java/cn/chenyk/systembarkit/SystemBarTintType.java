package cn.chenyk.systembarkit;

/**
 * Created by chenyk on 2017/8/29.
 * 系统栏类型枚举定义
 */

public enum SystemBarTintType {
    GRADIENT(1, "渐变"),
    PURECOLOR(2, "纯色");
    int code;
    String name;

    SystemBarTintType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    /**
     * 获取对应名字
     *
     * @param code
     * @return
     */
    public static String getName(int code) {
        String resultName = "";
        for (SystemBarTintType tintType : SystemBarTintType.values()) {
            if (tintType.code == code) {
                resultName = tintType.name;
                break;
            }
        }
        return resultName;
    }

}

package com.huasun.core.util;

/**
 * 点击事件处理
 */
public class ClickUtil {
    private static long lastClickTime;

    // 判断是否为快速点击事件
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 5000)
            return true;
        lastClickTime = time;
        return false;
    }
}
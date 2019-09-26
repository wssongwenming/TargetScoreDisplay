package com.huasun.core.util;

import android.content.Context;
import android.widget.Toast;

/**
 * author:songwenming
 * Date:2019/1/2
 * Description:
 */
public class ToastUtil {
    private static Toast toast;

    public static void showToast(Context context,
                                 String content) {
        if (toast == null) {
            toast = Toast.makeText(context,
                    content,
                    Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }
        toast.show();
    }


}

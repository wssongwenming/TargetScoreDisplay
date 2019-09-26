package com.huasun.core.util.view;

import android.content.Context;

/**
 * author:songwenming
 * Date:2018/10/13
 * Description:
 */
public class Alert {
    public static void alert_dialog(Context context, String msg){
        final PromptDialog promptDialog = new PromptDialog(context, msg , "确定");
        promptDialog.show();
        promptDialog.setClicklistener(new PromptDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                promptDialog.dismiss();
            }
        });
    }
}

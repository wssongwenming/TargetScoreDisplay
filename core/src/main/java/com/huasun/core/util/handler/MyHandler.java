package com.huasun.core.util.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * author:songwenming
 * Date:2018/10/14
 * Description:
 */
public class MyHandler {
    public static Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mListener.handleMessage(msg);
            System.out.println("mListener 地址值==== " + mListener);
        }
    };

    private static HandlerListener mListener;
    public static void setOnHandlerListener(HandlerListener listener) {
        mListener = listener;
    }
    public  static HandlerListener getListener(){
        return mListener;
    }

    public  interface HandlerListener {
        void handleMessage(Message msg);
    }
}

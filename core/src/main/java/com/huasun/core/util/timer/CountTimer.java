package com.huasun.core.util.timer;

import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;

/**
 * author:shikun,songwenming
 * Date:2018/10/13
 * Description:
 */
public  class CountTimer  extends CountDownTimer {
    Button btn=null;
    public CountTimer(long millisInFuture, long countDownInterval,Button btn) {
        super(millisInFuture, countDownInterval);
        this.btn=btn;
    }

    /**
     * 倒计时过程中调用
     *
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {
        Log.e("Tag", "倒计时=" + (millisUntilFinished/1000));
        btn.setText(millisUntilFinished / 1000 + "s后重新发送");
        //设置倒计时中的按钮外观
        btn.setClickable(false);//倒计时过程中将按钮设置为不可点击

    }

    /**
     * 倒计时完成后调用
     */
    @Override
    public void onFinish() {
        Log.e("Tag", "倒计时完成");
        //设置倒计时结束之后的按钮样式
        btn.setText("重新发送");
        btn.setClickable(true);
    }
}

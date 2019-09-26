package com.huasun.core.util.timer;

import java.util.TimerTask;

public class BaseTimerTask extends TimerTask{

    ITimerListener mITimerListener=null;
    public BaseTimerTask(ITimerListener timerListener){
        this.mITimerListener=timerListener;
    }
    //TimerTask实现了Runnable接口，所以要实现run方法
    @Override
    public void run() {
        if(mITimerListener!=null){
            mITimerListener.onTimer();
        }
    }
}

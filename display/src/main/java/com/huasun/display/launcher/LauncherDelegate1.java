package com.huasun.display.launcher;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.huasun.core.delegates.bottom.BottomItemDelegate;
import com.huasun.core.ui.launcher.ILauncherListener;
import com.huasun.core.ui.launcher.OnLauncherFinishTag;
import com.huasun.core.util.timer.BaseTimerTask;
import com.huasun.core.util.timer.ITimerListener;
import com.huasun.display.R;
import com.huasun.display.R2;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;

/**
 * author:songwenming
 * Date:2018/9/24
 * Description:
 */
public class LauncherDelegate1 extends BottomItemDelegate implements ITimerListener {
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer=null;
    //private int mCount=5;
    private int mCount=0;
    private Timer mTimer=null;
    private ILauncherListener mILauncherListener=null;
    //这里不允许单击，只能等待
/*    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView(){
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
            checkIsShowScroll();
        }

    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener){
            mILauncherListener=(ILauncherListener) activity;

        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    private  void initTimer(){
        mTimer=new Timer();
        final BaseTimerTask task=new BaseTimerTask(this);
        mTimer.schedule(task,0,1000);
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }
    //判断是否显示滑动气动页


    private void checkSignIn(OnLauncherFinishTag onLauncherFinishTag){
        mILauncherListener.onLauncherFinish(onLauncherFinishTag);
    }
    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mTvTimer!=null){
                    if(mILauncherListener.getStatus()==2){
                        mTvTimer.setText(MessageFormat.format("请稍候\n{0}s",mCount));
                        mCount++;
                    }else if(mILauncherListener.getStatus()==0){
                        if(mTimer!=null){
                            mTimer.cancel();
                            mTimer=null;
                            checkSignIn(OnLauncherFinishTag.SIGNIN_BY_PASS);
                        }
                    }else if(mILauncherListener.getStatus()==1){
                        if(mTimer!=null){
                            mTimer.cancel();
                            mTimer=null;
                            checkSignIn(OnLauncherFinishTag.SIGNIN_BY_FACE);
                        }
                    }
                }
            }
        });
    }



}

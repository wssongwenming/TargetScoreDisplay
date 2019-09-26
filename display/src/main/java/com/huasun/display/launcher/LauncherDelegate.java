package com.huasun.display.launcher;



import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.huasun.core.app.AccountManager;
import com.huasun.core.app.IUserChecker;
import com.huasun.display.R;

import com.huasun.core.delegates.LatteDelegate;
import com.huasun.core.ui.launcher.ILauncherListener;
import com.huasun.core.ui.launcher.OnLauncherFinishTag;
import com.huasun.core.ui.launcher.ScrollLauncherTag;
import com.huasun.core.util.storage.LattePreference;
import com.huasun.core.util.timer.BaseTimerTask;
import com.huasun.core.util.timer.ITimerListener;
import com.huasun.display.R2;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:songwenming
 * Date:2018/9/24
 * Description:
 */
public class LauncherDelegate extends LatteDelegate implements ITimerListener {
    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView mTvTimer=null;
    private int mCount=5;
    private Timer mTimer=null;
    private ILauncherListener mILauncherListener=null;
    @OnClick(R2.id.tv_launcher_timer)
    void onClickTimerView(){
        if(mTimer!=null){
            mTimer.cancel();
            mTimer=null;
            checkIsShowScroll();
        }

    }

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
    private void checkIsShowScroll(){
        //如果是第一次启动，则启动滑动页
        //if(!LattePreference.getAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name())){
            //start(new LauncherScrollDelegate(),SINGLETASK);
        //}
       // else
            //{
            //检查用户是否登陆了
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onSignIn() {
                    if(mILauncherListener!=null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }
                @Override
                public void onNotSignIn() {
                    if(mILauncherListener!=null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }

                }
            });
       // }
    }

    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mTvTimer!=null)
                {
                    mTvTimer.setText(MessageFormat.format("跳过\n{0}s",mCount));
                    mCount--;
                    if(mCount<0){
                        if(mTimer!=null){
                            mTimer.cancel();
                            mTimer=null;
                            checkIsShowScroll();
                        }
                    }
                }
            }
        });
    }

}

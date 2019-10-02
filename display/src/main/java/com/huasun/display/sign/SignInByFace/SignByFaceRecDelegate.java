package com.huasun.display.sign.SignInByFace;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.huasun.core.delegates.LatteDelegate;
import com.huasun.core.delegates.bottom.BottomItemDelegate;
import com.huasun.core.ui.launcher.ILauncherListener;
import com.huasun.core.ui.launcher.OnLauncherFinishTag;
import com.huasun.core.util.timer.BaseTimerTask;
import com.huasun.core.util.timer.ITimerListener;
import com.huasun.display.R;
import com.huasun.display.sign.ISignListener;

import java.util.Timer;

/**
 * author:songwenming
 * Date:2019/9/26
 * Description:
 */
public class SignByFaceRecDelegate extends BottomItemDelegate {

    private ISignListener mISignListener=null;
    private ILauncherListener mILauncherListener=null;
    private Timer mTimer=null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ISignListener)
        {
            mISignListener=(ISignListener) activity;
        }
        if(activity instanceof ILauncherListener){
            mILauncherListener=(ILauncherListener) activity;

        }
    }
    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in_by_face;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        //initTimer();
    }



    private void checkSignIn(OnLauncherFinishTag onLauncherFinishTag ){
        mILauncherListener.onLauncherFinish(onLauncherFinishTag);
    }
}

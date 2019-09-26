package com.huasun.targetscore.display;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.huasun.core.activities.ProxyActivity;
import com.huasun.core.app.Latte;
import com.huasun.core.delegates.LatteDelegate;
import com.huasun.core.ui.launcher.ILauncherListener;
import com.huasun.core.ui.launcher.OnLauncherFinishTag;
import com.huasun.core.util.log.LatteLogger;
import com.huasun.display.launcher.LauncherDelegate;
import com.huasun.display.launcher.LauncherScrollDelegate;
import com.huasun.display.main.BcsbBottomDelegate;
import com.huasun.display.sign.ISignListener;
import com.huasun.display.sign.SignInDelegate;

public class MainActivity extends ProxyActivity implements ISignListener,ILauncherListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
    }

    @Override
    public LatteDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignInSuccess() {
        LatteLogger.e("denglu"," dengluchengong");
        Toast.makeText(this,"登陆成功",Toast.LENGTH_LONG).show();
        startWithPop(new BcsbBottomDelegate());
    }

    @Override
    public void onSignUpSuccess() {
        LatteLogger.e("denglu"," dengluchengong");
        Toast.makeText(this,"登陆成功",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSignInError(String msg){

    }

    @Override
    public void onSignUpError(String msg) {

    }

    @Override
    public void onSignInFailure(String msg) {

    }

    @Override
    public void onSignUpFailure(String msg) {

    }

    @Override
    public void onLauncherFinish(OnLauncherFinishTag tag) {
        switch (tag){
            case SIGNED:
                Toast.makeText(this,"启动结束，用户登陆",Toast.LENGTH_LONG).show();
                startWithPop(new BcsbBottomDelegate());
                break;
            case NOT_SIGNED:
                Toast.makeText(this,"启动结束，用户未登陆",Toast.LENGTH_LONG).show();
                startWithPop(new SignInDelegate());
                break;
        }
    }
}

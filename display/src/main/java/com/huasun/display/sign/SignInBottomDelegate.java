package com.huasun.display.sign;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.huasun.core.app.ConfigKeys;
import com.huasun.core.app.Latte;
import com.huasun.core.delegates.bottom.BaseBottomDelegate;
import com.huasun.core.delegates.bottom.BottomItemDelegate;
import com.huasun.core.delegates.bottom.BottomTabBean;
import com.huasun.core.delegates.bottom.ItemBuilder;
import com.huasun.display.launcher.LauncherDelegate;
import com.huasun.display.sign.SignInByFace.SignByFaceRecDelegate;
import com.huasun.display.sign.SignInByPassword.SignInByPassDelegate;

import java.util.LinkedHashMap;

/**
 * author:songwenming
 * Date:2019/9/26
 * Description:
 */
public class SignInBottomDelegate extends BaseBottomDelegate {
    private static final String SIGNIN_WAY = "SIGNIN_WAY";
    private static int signInWay =0;

/*    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            signInWay = args.getInt(SIGNIN_WAY);
            Log.i("siginway1",signInWay+"");
        }
    }*/
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean,BottomItemDelegate>items=new LinkedHashMap<>();
        items.put(new BottomTabBean("{icon-wait}","等候中"), new LauncherDelegate());
        items.put(new BottomTabBean("{icon-signin-by-pass}","密码登陆"),new SignInByPassDelegate());
        items.put(new BottomTabBean("{icon-signin-by-face}","人脸识别登陆"),new SignByFaceRecDelegate());

        return builder.addItems(items).build();
    }
/*    public static SignInBottomDelegate newInstance(int signInWay){
        final Bundle args = new Bundle();
        args.putInt(SIGNIN_WAY,signInWay);
        final SignInBottomDelegate delegate = new SignInBottomDelegate();
        delegate.setArguments(args);
        return delegate;
    }*/
    @Override
    public int setIndexDelegate() {
        return Latte.getConfiguration(ConfigKeys.COMMAND);
    }

    @Override
    public int setClickedColor() {
        return 0;
    }
}

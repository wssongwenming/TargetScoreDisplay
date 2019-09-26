package com.huasun.core.app;


import com.huasun.core.util.storage.LattePreference;

/*管理用户信息*/
public class AccountManager {
    private enum SignTag{
        SIGN_TAG
    }
    //登录后调用的方法,保存用户登录状态
    public static void setSignState(boolean state){
        LattePreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }
    //判断是不是已经登录
    private static boolean isSignIn(){
        return LattePreference.getAppFlag(SignTag.SIGN_TAG.name());
    }
    public static void checkAccount(IUserChecker checker){
        if(isSignIn())
        {//如果已经登录了，
            checker.onSignIn();
        }else {//执行没有登录的回调
            checker.onNotSignIn();
        }
    }
}

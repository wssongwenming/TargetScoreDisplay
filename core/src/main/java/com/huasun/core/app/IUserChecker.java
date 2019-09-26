package com.huasun.core.app;
/*登录注册流程中，需要回调比如登陆注册成功的回调，以及是否有用户状态的回调*/
public interface IUserChecker {
    //有用户信息
    void onSignIn() ;
    //没有用户信息
    void onNotSignIn();
}

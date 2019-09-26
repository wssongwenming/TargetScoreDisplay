package com.huasun.display.sign;

public interface ISignListener {
    //分别为登录和注册成功的回调
    void onSignInSuccess();
    void onSignUpSuccess();
    void onSignInError(String msg);
    void onSignUpError(String msg);
    void onSignInFailure(String msg);
    void onSignUpFailure(String msg);
}

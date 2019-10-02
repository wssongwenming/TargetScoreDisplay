package com.huasun.display.sign.SignInByPassword;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.huasun.core.delegates.LatteDelegate;
import com.huasun.core.delegates.bottom.BottomItemDelegate;
import com.huasun.core.net.RestClient;
import com.huasun.core.net.callback.ISuccess;
import com.huasun.core.util.timer.ITimerListener;
import com.huasun.display.R;
import com.huasun.display.R2;
import com.huasun.display.sign.ISignListener;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author:songwenming
 * Date:2019/9/23
 * Description:
 */
public class SignInByPassDelegate extends BottomItemDelegate {

    @BindView(R2.id.edit_sign_in_id)
    EditText mId=null;
    @BindView(R2.id.edit_sign_in_name)
    EditText mName=null;
    @BindView(R2.id.edit_sign_in_department)
    EditText mDepartment=null;
    @BindView(R2.id.edit_sign_in_password)
    EditText mPassword=null;
    private ISignListener mISignListener=null;
    @OnClick(R2.id.btn_sign_in)
    void onClickSignIn(){
        if(checkForm()){
            RestClient.builder()
                    .url("http://192.168.1.3:8081/Web01_exec/UserLogin")
                    .params("id",mId.getText().toString())
                    .params("password",mPassword.getText().toString())
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            SignHandler.onSignIn(response,mISignListener);
                        }
                    })
                    .build()
                    .post();
        }
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ISignListener)
        {
            mISignListener=(ISignListener) activity;
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in_by_password;
    }


    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
    private boolean checkForm(){
        final String id=mId.getText().toString();
        final String name=mName.getText().toString();
        final String department=mDepartment.getText().toString();
        final String password=mPassword.getText().toString();
        boolean isPass=true;
        if(id.isEmpty()){
            mId.setError("请输入编码");
            isPass=false;
        }else {
            mId.setError(null);
        }
        /*if(name.isEmpty()){
            mName.setError("请输入姓名");
            isPass=false;
        }else {
          mName.setError(null);
        }
        if(department.isEmpty()){
            mDepartment.setError("请输入部职别/所属单位");
            isPass=false;
        }else {
            mDepartment.setError(null);
        }*/
        if(password.isEmpty()||password.length()<6){
            mPassword.setError("请填写至少6位数密码");
            isPass=false;
        }else{
            mPassword.setError(null);
        }
        return isPass;
    }


}

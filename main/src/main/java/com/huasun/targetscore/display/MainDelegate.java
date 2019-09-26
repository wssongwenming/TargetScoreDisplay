package com.huasun.targetscore.display;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.huasun.core.delegates.LatteDelegate;
import com.huasun.core.net.RestClient;
import com.huasun.core.net.callback.IError;
import com.huasun.core.net.callback.IFailure;
import com.huasun.core.net.callback.ISuccess;

/**
 * author:songwenming
 * Date:2019/9/22
 * Description:
 */
public class MainDelegate extends LatteDelegate{
    @Override
    public Object setLayout() {
        return R.layout.delegate_main;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        testRestClient();
    }
    private void testRestClient(){
        RestClient.builder()
                .url("http://www.baidu.com")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                        //Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}

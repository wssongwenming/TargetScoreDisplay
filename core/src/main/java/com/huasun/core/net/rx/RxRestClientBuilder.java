package com.huasun.core.net.rx;

import android.content.Context;


import com.huasun.core.net.RestCreator;
import com.huasun.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RxRestClientBuilder {
    private  String mUrl=null;
    private static final Map<String,Object>PARAMS= RestCreator.getParams();
    private RequestBody mBody=null;
    private Context mContext=null;
    private LoaderStyle mLoaderStyle=null;
    private File mFile=null;
    RxRestClientBuilder(){
    }
    public final RxRestClientBuilder url(String url){
        this.mUrl=url;
        return this;
    }
    public final RxRestClientBuilder params(WeakHashMap<String,Object>params){
        PARAMS.putAll(params);
        return this;
    }
    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key,value);
        return this;
    }
    //传入类似JSON
    public final RxRestClientBuilder raw(String raw){
        this.mBody= RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }
    public final RxRestClientBuilder loader(Context context, LoaderStyle style)
    {
        this.mContext=context;
        this.mLoaderStyle=style;
        return this;
    }
    public final RxRestClientBuilder loader(Context context)
    {
        this.mContext=context;
        this.mLoaderStyle=LoaderStyle.BallSpinFadeLoaderIndicator;
        return this;
    }
    public final RxRestClientBuilder file(File file){
        this.mFile=file;
        return this;

    }
    public final RxRestClientBuilder file(String file){
        this.mFile=new File(file);
        return this;

    }
    public final RxRestClient build(){
        return new RxRestClient(mUrl,PARAMS,mBody,mFile,mLoaderStyle,mContext);

    }
}

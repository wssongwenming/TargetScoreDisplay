package com.huasun.core.net;

import android.content.Context;


import com.huasun.core.net.callback.IError;
import com.huasun.core.net.callback.IFailure;
import com.huasun.core.net.callback.IRequest;
import com.huasun.core.net.callback.ISuccess;
import com.huasun.core.net.callback.RequestCallbacks;
import com.huasun.core.net.download.DownloadHandler;
import com.huasun.core.ui.loader.LatteLoader;
import com.huasun.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * author:songwenming
 * Date:2018/9/22
 * Description:进行网络请求的具体实现类
 */
public class RestClient {
    //网络框架，要灵活使用什么样的框架呢？既然是传入参数也没有顺序的要求，传入什么用什么的模式的话？
    //建造者模式最好不过，可以用android简化版的建造者模式
    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS=RestCreator.getParams();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;
    private final RequestBody BODY;

    private final LoaderStyle LOADER_STYLE;
    //loaderstyle为dialog，显示时须传入一个context
    private final Context CONTEXT;

    private final File FILE;

    public RestClient(String url,
                      Map<String, Object> params,
                      String downloadDir,
                      String extension,
                      String name,
                      IRequest request,
                      ISuccess success,
                      IFailure failure,
                      IError error,
                      RequestBody body,
                      LoaderStyle loaderStyle,
                      Context context,
                      File file
                      ) {
        this.URL = url;
        this.PARAMS.putAll(params);
        this.REQUEST = request;
        this.DOWNLOAD_DIR=downloadDir;
        this.EXTENSION=extension;
        this.NAME=name;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.LOADER_STYLE=loaderStyle;

        this.CONTEXT=context;
        this.FILE=file;
    }
    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }
    private void request(HttpMethod method){
        final RestService service=RestCreator.getRestService();
        Call<String> call=null;
        //请求开始
        if(REQUEST!=null)
        {
            REQUEST.onRequestStart();
        }
        if(LOADER_STYLE!=null){
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }
        switch (method){
            case GET:
                call=service.get(URL,PARAMS);
                break;
            case POST:
                call=service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call=service.postRaw(URL,BODY);
                break;
            case PUT_RAW:
                call=service.putRaw(URL,BODY);
                break;
            case PUT:
                call=service.put(URL,PARAMS);
                break;
            case DELETE:
                call=service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                RequestBody requestBody=
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                MultipartBody.Part body=
                        MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                call=RestCreator.getRestService().upload(URL,body);
                break;
            default:
                break;

        }
        if(call!=null){
            //enqueue(Callback<T> callback)
            call.enqueue(getRequestCallback());

        }
    }
    private Callback<String> getRequestCallback(){
        return new RequestCallbacks(REQUEST,SUCCESS,FAILURE,ERROR,LOADER_STYLE);
    }
    public final void get(){
        request(HttpMethod.GET);
    }
    public final void post(){
        if(BODY==null){
            request(HttpMethod.POST);
        }else{
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            //上传原始数据params一定要为空
            request(HttpMethod.POST_RAW);
        }

    }
    public final void put(){
        if(BODY==null){
            request(HttpMethod.PUT);
        }else{
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null");
            }
            request(HttpMethod.PUT_RAW);
        }
    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }
    public final void upload(){
        request(HttpMethod.UPLOAD);
    }
    public final void download(){
        new DownloadHandler(URL,REQUEST,DOWNLOAD_DIR,EXTENSION,NAME,SUCCESS,FAILURE,ERROR).handleDownload();

    }

}

package com.huasun.core.net.rx;

import android.content.Context;
import com.huasun.core.net.HttpMethod;
import com.huasun.core.net.RestCreator;
import com.huasun.core.ui.loader.LatteLoader;
import com.huasun.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;


import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class RxRestClient {
    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS= RestCreator.getParams();
    private final RequestBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final File FILE;
    private final Context CONTEXT;
    public RxRestClient(String url,
                        Map<String, Object> params,
                        RequestBody body,
                        File file,
                        LoaderStyle loaderStyle,
                        Context context) {
        URL = url;
        PARAMS.putAll(params);
        BODY = body;
        FILE=file;
        LOADER_STYLE=loaderStyle;
        CONTEXT=context;

    }
    //RestClient的builder实现模式，没有把不作为RestClient的内部类，
    public static RxRestClientBuilder builder(){
        return new RxRestClientBuilder();
    }
    private Observable<String> request(HttpMethod method){
        final RxRestService rxRestService=RestCreator.getRxRestService();
        Observable<String>  observable=null;
        if(LOADER_STYLE!=null)
        {
            LatteLoader.showLoading(CONTEXT,LOADER_STYLE);
        }
        switch (method){
            case GET:
                observable=rxRestService.get(URL,PARAMS);
                break;
            case POST:
                observable=rxRestService.post(URL,PARAMS);
                break;
            case POST_RAW:
                observable=rxRestService.postRaw(URL,BODY);
                break;
            case PUT_RAW:
                observable=rxRestService.putRaw(URL,BODY);
            case PUT:
                observable=rxRestService.put(URL,PARAMS);
                break;
            case DELETE:
                observable=rxRestService.delete(URL,PARAMS);
                break;
            case UPLOAD:
                RequestBody requestBody=
                        RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                MultipartBody.Part body=
                        MultipartBody.Part.createFormData("file",FILE.getName(),requestBody);
                observable=RestCreator.getRxRestService().upload(URL,body);
                break;
            default:
                break;
        }
        return observable;
    }
    public final Observable<String> get(){
        return request(HttpMethod.GET);
    }
    public final Observable<String> post(){
        if(BODY==null) {
            return request(HttpMethod.POST);
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("param must be null");
            }
            return request(HttpMethod.POST_RAW);
        }
    }
    public Observable<String>  put(){
        if(BODY==null) {
           return request(HttpMethod.PUT);
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("param must be null");
            }
           return request(HttpMethod.PUT_RAW);
        }
    }
    public final Observable<String> delete(){
       return request(HttpMethod.DELETE);
    }
    public final Observable<String> upload(){
       return request(HttpMethod.UPLOAD);
    }

    public final Observable<ResponseBody> download()

    {
        final Observable<ResponseBody> responseBodyObservable=RestCreator.getRxRestService().download(URL,PARAMS);
        return responseBodyObservable;
    }
}

package com.huasun.core.net.download;

import android.os.AsyncTask;

import com.huasun.core.net.RestCreator;
import com.huasun.core.net.callback.IError;
import com.huasun.core.net.callback.IFailure;
import com.huasun.core.net.callback.IRequest;
import com.huasun.core.net.callback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DownloadHandler {
    private final String URL;
    private static final WeakHashMap<String,Object> PARAMS=new WeakHashMap<>();
    private final IRequest REQUEST;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private final IError ERROR;

    public DownloadHandler(String url,
                           IRequest request,
                           String dwonload_dir,
                           String extension,
                           String name,
                           ISuccess success,
                           IFailure failure,
                           IError error) {
        URL = url;
        REQUEST = request;
        DOWNLOAD_DIR = dwonload_dir;
        EXTENSION = extension;
        NAME = name;
        SUCCESS = success;
        FAILURE = failure;
        ERROR = error;
    }
    public final void handleDownload(){
        if(REQUEST!=null)
        {
            REQUEST.onRequestStart();
        }
        //RestCreator.getRestService().download(URL,PARAMS)为一个call调用call.enqueue(callback)
        RestCreator.getRestService().download(URL,PARAMS)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful())
                        {
                            if(call.isExecuted())
                            {
                                final ResponseBody responseBody = response.body();
                                final SaveFileTask task = new SaveFileTask(REQUEST, SUCCESS);
                                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, DOWNLOAD_DIR, EXTENSION, responseBody, NAME);
                                //先判断task是否执行完毕，否则文件下载不全
                                if (task.isCancelled()) {
                                    if (REQUEST != null) {
                                        REQUEST.onRequestEnd();
                                    }
                                }
                            }
                        }else {
                            if(ERROR!=null){
                                ERROR.onError(response.code(),response.message());
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable throwable) {
                        if(FAILURE!=null)
                        {
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}

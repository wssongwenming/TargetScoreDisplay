package com.huasun.core.net;



import com.huasun.core.app.ConfigKeys;
import com.huasun.core.app.Latte;
import com.huasun.core.net.rx.RxRestService;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RestCreator {
    private static final class ParamsHolder
    {
        private static final WeakHashMap<String,Object>PARAMS=new WeakHashMap<>();
    }
    public static WeakHashMap<String,Object>getParams()
    {
        return ParamsHolder.PARAMS;
    }

    private static final class RetrofitHolder{

        private static final String BASE_URL= (String) Latte.getConfigurations().get(ConfigKeys.API_HOST);
        private static final Retrofit RETROFIT_CLIENT= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKhttpHolder.OK_HTTP_CLIENT)
                //增加返回原始的的json
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    private static final class OKhttpHolder{
        private static final int TIME_OUT=60;
        private static final OkHttpClient.Builder BUILDER=new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS=Latte.getConfiguration(ConfigKeys.INTERCEPTOR);
        private static OkHttpClient.Builder addInterceptor(){
            if(INTERCEPTORS!=null&&!INTERCEPTORS.isEmpty())
            {
                for (Interceptor interceptor:INTERCEPTORS) {
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }
        private static final OkHttpClient OK_HTTP_CLIENT=addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }

    public static RestService getRestService()
    {
        return RestServiceHolder.REST_SERVICE;
    }
    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE=
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    public static RxRestService getRxRestService()
    {
        return RxRestServiceHolder.REST_SERVICE;
    }
    private static final class RxRestServiceHolder{
        private static final RxRestService REST_SERVICE=
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }
}


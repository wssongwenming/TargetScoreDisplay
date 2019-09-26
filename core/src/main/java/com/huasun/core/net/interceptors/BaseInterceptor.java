package com.huasun.core.net.interceptors;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/*要模拟服务器的话，肯定是要获取传入的参数，get方法的话就要从传入的url获取，如果是post的话就要从请求体
* 里获取*/
public abstract class BaseInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
    //LinkedHashMap是有序的，HashMap是无序的,所以用了LinkedHashMap，get方法的话就要从传入的url获取
    protected LinkedHashMap<String,String>getUrlParameters(Chain chain){
        final HttpUrl url=chain.request().url();
        //取出参数的个数
        int size=url.querySize();
        //把取出的参数放入LinkedHashMap
        final LinkedHashMap<String,String>params=new LinkedHashMap<>();
        for (int i = 0; i <size ; i++) {
            //获取参数的名字和值
            params.put(url.queryParameterName(i),url.queryParameterValue(i));
        }
        return params;
    }
    //重载，通过key获取value
    protected String getUrlParameters(Chain chain,String key){
        final Request request=chain.request();
        return request.url().queryParameter(key);
    }
    protected LinkedHashMap<String,String>getBodyparameters(Chain chain){
        //FormBody为OKhttp3特有
        final FormBody formBody= (FormBody) chain.request().body();
        final LinkedHashMap<String,String>params=new LinkedHashMap<>();
        int size=formBody.size();
        for (int i = 0; i <size ; i++) {
            params.put(formBody.name(i),formBody.value(i));
        }
        return params;
    }
    protected String getBodyparameters(Chain chain,String key){
        return getBodyparameters(chain).get(key);
    }
}

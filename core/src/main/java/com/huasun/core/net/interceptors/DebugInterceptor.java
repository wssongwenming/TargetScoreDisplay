package com.huasun.core.net.interceptors;

import android.support.annotation.RawRes;

import com.huasun.core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DebugInterceptor extends BaseInterceptor {
    //需要Debug的url即模拟URL
   private final String DEBUG_URL;
   //本例中模拟访问raw文件下的一个文本资源，为其资源ID,任何资源都会转化为int型资源ID
   private final int DEBUG_RAW_ID;

    public DebugInterceptor(String debugUrl, int rawId) {

        DEBUG_URL = debugUrl;
        DEBUG_RAW_ID = rawId;
    }
    //获取测试文件内容
    private Response getResponse(Interceptor.Chain chain, String json){
        return new Response.Builder()
                .code(200)
                //调试数据为用的是JSON
                .addHeader("Content-type","application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"),json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }
    // @RawRes表示rawId必须为系统生成的资源Id
    private Response debugResponse(Interceptor.Chain chain, @RawRes int rawId){
        final String json= FileUtil.getRawFile(rawId);
        return getResponse(chain,json);
    }
    //系统回调方法
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        final String url=chain.request().url().toString();
        //如果包含我们验证的URL则返回资源
        if(url.contains(DEBUG_URL))
        {
            return debugResponse(chain,DEBUG_RAW_ID);
        }
        return chain.proceed(chain.request());
    }
}

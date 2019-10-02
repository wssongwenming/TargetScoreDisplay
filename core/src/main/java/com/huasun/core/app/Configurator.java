package com.huasun.core.app;

import android.app.Activity;
import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.WeakHashMap;

import okhttp3.Interceptor;

/**
 * author:songwenming
 * Date:2019/9/21
 * Description:
 */
public class Configurator {
    private static final HashMap<Object,Object> LATTE_CONFIGS=new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS= new ArrayList<>();
    private static  int SignInWay=-1;
    private static final ArrayList<Interceptor> INTERCEPTORS=new ArrayList<>();
    private static final Handler HANDLER=new Handler();

    private Configurator() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY,false);
        LATTE_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }
    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }
    final HashMap<Object,Object> getLatteConfigs(){
        return LATTE_CONFIGS;
    }
    //静态内部类实现单例模式
    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure(){
        initIcons();
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY,true);
        //init  Utils in the function of onCreate in  Application
        //Utils.init(Latte.getApplicationContext());

    }
    //微信拉取他的回调Activity时会需要一个Activity的上下文,这时用全局的congtext是不合适的
    public final Configurator withActivity(Activity activity)
    {
        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY,activity);
        return this;
    }

    public final Configurator withCommand(int command){
        LATTE_CONFIGS.put(ConfigKeys.COMMAND,command);
        return this;
    }

    public final Configurator withApiHost(String host){
        LATTE_CONFIGS.put(ConfigKeys.API_HOST,host);
        return this;
    }
    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }
    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    private final Configurator withInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;

    }
    private void initIcons(){
        if(ICONS.size()>0){
            //with(params)params=The IconDescriptor holding the ttf file reference and its mappings
            final Iconify.IconifyInitializer initializer=Iconify.with(ICONS.get(0));
            for (int i = 1; i <ICONS.size() ; i++) {
                initializer.with(ICONS.get(i));

            }
        }
    }
    private void checkConfiguration(){
        final boolean isReady=(boolean)LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if(!isReady){
            throw new RuntimeException("Configuration is not ready ,call configure");
        }
    }
    //T为泛型，表示该函数在返回时已经用返回类型做了转型
    final <T> T getConfiguration(Object key) {
        checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            //不能随便抛出异常，这样会导致APP
            // throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }

}

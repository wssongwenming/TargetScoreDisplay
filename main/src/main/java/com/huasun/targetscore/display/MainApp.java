package com.huasun.targetscore.display;

import android.app.Application;

import com.bcsb.rabbitmq.entity.Command;
import com.huasun.core.app.Latte;
import com.huasun.core.net.interceptors.DebugInterceptor;
import com.huasun.display.database.DatabaseManager;
import com.huasun.display.icon.FontBcModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * author:songwenming
 * Date:2019/9/21
 * Description:
 */
public class MainApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontBcModule())
                .withCommand(Command.waiting.getIndex())
                .withApiHost("http://127.0.0.1")
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .configure();
        DatabaseManager.getInstance().init(this);

    }
}

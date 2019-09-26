package com.huasun.core.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * author:songwenming
 * Date:2018/9/26
 * Description:主要为实现ScrollerBanner服务
 */
public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {


    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}

package com.huasun.display.launcher;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import com.huasun.core.app.AccountManager;
import com.huasun.core.app.ConfigKeys;
import com.huasun.core.app.IUserChecker;
import com.huasun.core.app.Latte;
import com.huasun.core.delegates.LatteDelegate;
import com.huasun.core.ui.launcher.ILauncherListener;
import com.huasun.core.ui.launcher.LauncherHolderCreator;
import com.huasun.core.ui.launcher.OnLauncherFinishTag;
import com.huasun.core.ui.launcher.ScrollLauncherTag;
import com.huasun.core.util.storage.LattePreference;
import com.huasun.display.R;

import java.util.ArrayList;

/**
 * author:songwenming
 * Date:2018/9/26
 * Description:
 */
public class LauncherScrollDelegate extends LatteDelegate implements ViewPager.OnPageChangeListener, OnItemClickListener {
    //ConvenientBanner<T> 泛型T，这里表示资源ID
    private ConvenientBanner<Integer> mConvenientBanner=null;
    private static final ArrayList<Integer> INTEGERS=new ArrayList<>();
    private ILauncherListener mILauncherListener=null;
    private void initBanner(){
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);

        mConvenientBanner
                .setPages(new LauncherHolderCreator(),INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setOnPageChangeListener(this)
                .setCanLoop(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener)
        {
            mILauncherListener=(ILauncherListener)activity;
        }
    }
    @Override
    public Object setLayout() {
        mConvenientBanner=new ConvenientBanner<>((Context) Latte.getConfiguration(ConfigKeys.ACTIVITY));
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initBanner();
    }

    @Override
    public void onItemClick(int position) {
        Log.d("scroll", "onPageScrolled1: ");
        //如果点击的是最后一个
        if(position==INTEGERS.size()-1){
            //是第一次进入
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
            //检查用户是否已经登录
            AccountManager.checkAccount(new IUserChecker(){
                @Override
                public void onSignIn() {
                    if(mILauncherListener!=null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }
                @Override
                public void onNotSignIn() {
                    if(mILauncherListener!=null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }

                }
            });

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if(position==INTEGERS.size()-1){
            LattePreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
            //检查用户是否已经登录
            AccountManager.checkAccount(new IUserChecker(){
                @Override
                public void onSignIn(){
                    if(mILauncherListener!=null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
                    }
                }
                @Override
                public void onNotSignIn(){
                    if(mILauncherListener!=null){
                        mILauncherListener.onLauncherFinish(OnLauncherFinishTag.NOT_SIGNED);
                    }

                }
            });

        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

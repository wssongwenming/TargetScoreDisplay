package com.huasun.core.ui.launcher;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;

/**
 * author:songwenming
 * Date:2018/9/26
 * Description:
 */
public class LauncherHolder implements Holder<Integer> {

    private AppCompatImageView mImageView=null;

    @Override
    public View createView(Context context) {
        mImageView=new AppCompatImageView(context);
        return mImageView;
    }

    //每次滑动banner时需要更新的
    @Override
    public void UpdateUI(Context context, int position, Integer data) {
        mImageView.setBackgroundResource(data);
    }
}

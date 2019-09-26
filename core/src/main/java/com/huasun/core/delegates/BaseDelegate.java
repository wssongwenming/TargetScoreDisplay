package com.huasun.core.delegates;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huasun.core.activities.ProxyActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * author:songwenming
 * Date:2019/9/21
 * Description:
 */
public abstract class BaseDelegate extends SwipeBackFragment {
    private Unbinder mUnbinder=null;
    //提供一个方法来让子类传入它的布局，可以是一个view，也可以是一个Layout 的Id
    public abstract Object setLayout();
    public abstract void onBindView(@Nullable Bundle savedInstanceState,View rootView);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView;
        if(setLayout()instanceof Integer){
            rootView=inflater.inflate((Integer) setLayout(),container,false);
        }else if(setLayout()instanceof View)
        {
            rootView=(View)setLayout();
        }else{
            throw new ClassCastException("setLayout() type must be int or View");
        }

        mUnbinder= ButterKnife.bind(this,rootView);
        onBindView(savedInstanceState,rootView);

        return rootView;
    }

    public final ProxyActivity getProxyActivity(){
        return (ProxyActivity) _mActivity;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mUnbinder!=null){
            mUnbinder.unbind();
        }

    }
}

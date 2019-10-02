package com.huasun.display.main.mark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.huasun.core.delegates.bottom.BottomItemDelegate;
import com.huasun.display.R;
import com.huasun.display.main.mark.data.TargetDataDelegate;
import com.huasun.display.main.mark.map.TargetMapDelegate;

/**
 * author:songwenming
 * Date:2019/9/26
 * Description:
 */
public class MarkDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_mark;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final TargetMapDelegate targetMapDelegate=new TargetMapDelegate();
        loadRootFragment(R.id.target_map_container,targetMapDelegate);
        replaceLoadRootFragment(R.id.target_data_container,new TargetDataDelegate(),false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final TargetMapDelegate targetMapDelegate=new TargetMapDelegate();
        loadRootFragment(R.id.target_map_container,targetMapDelegate);
        replaceLoadRootFragment(R.id.target_data_container,new TargetDataDelegate(),false);
    }
}

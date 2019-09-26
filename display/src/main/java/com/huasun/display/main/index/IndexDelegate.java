package com.huasun.display.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;

import com.huasun.core.delegates.bottom.BottomItemDelegate;
import com.huasun.display.R;

/**
 * author:songwenming
 * Date:2019/9/26
 * Description:
 */
public class IndexDelegate extends BottomItemDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}

package com.huasun.core.delegates.bottom;

import android.view.View;
import android.widget.Toast;

import com.huasun.core.R;
import com.huasun.core.app.Latte;
import com.huasun.core.delegates.LatteDelegate;

/**
 * author:songwenming
 * Date:2019/9/25
 * Description:
 */
public abstract class BottomItemDelegate extends LatteDelegate {
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, "再单击一次退出" + Latte.getApplicationContext().getString(R.string.app_name), Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}

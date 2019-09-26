package com.huasun.display.main;

import android.graphics.Color;

import com.huasun.core.delegates.bottom.BaseBottomDelegate;
import com.huasun.core.delegates.bottom.BottomItemDelegate;
import com.huasun.core.delegates.bottom.BottomTabBean;
import com.huasun.core.delegates.bottom.ItemBuilder;
import com.huasun.display.main.index.IndexDelegate;

import java.util.LinkedHashMap;

/**
 * author:songwenming
 * Date:2019/9/26
 * Description:
 */
public class BcsbBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean,BottomItemDelegate>items=new LinkedHashMap<>();
        items.put(new BottomTabBean("{icon-index}","首页"),new IndexDelegate());
        items.put(new BottomTabBean("{icon-medicinemanage}","药品管理"),new IndexDelegate());
        items.put(new BottomTabBean("{icon-mine}","我的"),new IndexDelegate());
        return builder.addItems(items).build();
    }


    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}

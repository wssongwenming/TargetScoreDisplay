package com.huasun.core.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * author:songwenming
 * Date:2019/9/8
 * Description:
 */
public final class ItemBuilder {
    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS=new LinkedHashMap<>();
    static ItemBuilder builder(){
        return new ItemBuilder();
    }
    public final ItemBuilder addItem(BottomTabBean bean,BottomItemDelegate delegate){
     ITEMS.put(bean,delegate);
     return new ItemBuilder();
    }
    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean,BottomItemDelegate> items){
        ITEMS.putAll(items);
        return this;
    }
    public final LinkedHashMap<BottomTabBean,BottomItemDelegate> build(){
        return ITEMS;
    }
}

package com.huasun.display.icon;

import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.IconFontDescriptor;

/**
 * author:songwenming
 * Date:2019/9/21
 * Description:
 */
public class FontBcModule implements IconFontDescriptor {
    @Override
    public String ttfFileName() {
        return "iconfont.ttf";
    }

    @Override
    public Icon[] characters() {
        return BcIcons.values();
    }
}

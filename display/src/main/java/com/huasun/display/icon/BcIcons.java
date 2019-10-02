package com.huasun.display.icon;

import com.joanzapata.iconify.Icon;

public enum BcIcons implements Icon {
    icon_wait('\ue67c'),
    icon_signin_by_pass('\ue618'),
    icon_signin_by_face('\ue60f');
    ;
    private char character;
    BcIcons(char character) {
        this.character = character;
    }
    @Override
    public String key() {
        return name().replace('_','-');
    }
    @Override
    public char character() {
        return character;
    }
}

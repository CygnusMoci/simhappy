package com.moci.simhappy.util;

import com.moci.simhappy.R;

public enum CardType {


    CR(R.drawable.r,R.drawable.r_img),
    SR(R.drawable.sr,R.drawable.sr_img),
    SSR(R.drawable.ssr,R.drawable.ssr_img);



    int icon;
    int icon_img;
    CardType(Integer icon, Integer icon_img) {
        this.icon = icon;
        this.icon_img = icon_img;
    }


    public int getIcon() {
        return icon;
    }

    public int getIcon_img() {
        return icon_img;
    }

    public static int getImg(Integer icon) {
        for (CardType ele : values()) {
            if(ele.getIcon() == icon) return ele.getIcon_img();
        }
        return R.drawable.r_img;
    }
}

package com.moci.simhappy.util;

import com.moci.simhappy.R;

public enum CardType {


    CR(R.drawable.r,R.drawable.r_img),
    SR(R.drawable.sr2,R.drawable.sr2_img),
    SSR(R.drawable.ssr2,R.drawable.ssr2_img),

    SR_sp(R.drawable.sr1,R.drawable.sr1_img),
    SSR_sp(R.drawable.ssr1,R.drawable.ssr1_img);



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

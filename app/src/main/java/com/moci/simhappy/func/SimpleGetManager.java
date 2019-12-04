package com.moci.simhappy.func;

import android.util.Log;

import com.moci.simhappy.util.CardType;
import com.moci.simhappy.util.Constant;

import java.util.Random;

public class SimpleGetManager {
    private int P_SSR;
    private int P_SR;
    private int P_R;

    public static SimpleGetManager getInstance() {
        return ManageHoder.INSTANCE;
    }

    private static final class ManageHoder {
        private static final SimpleGetManager INSTANCE = new SimpleGetManager();
    }

    private SimpleGetManager() {
        setPR(-1,-1,-1);
    }

    public void setPR(int ssr, int sr, int r){
        if(ssr < 0 || sr < 0 || r < 0){
            this.P_SSR = Constant.DEFAULT_SSR;
            this.P_SR = Constant.DEFAULT_SR;
            this.P_R = Constant.DEFAULT_R;
            return;
        }

        this.P_SSR = ssr;
        this.P_SR = sr;
        this.P_R = 1000-ssr-sr;
    }

    public CardType getType(){
        CardType[] pool = getCardPool();
        int num = randomNum();
        Log.w("moci", "getType: "+num);
        return pool[num];
    }

    private CardType[] getCardPool(){
        CardType[] pool = new CardType[1000];
        int tempSSR = this.P_SSR;
        int tempSR = this.P_SR;
        int tempR = this.P_R;
        int k = 0;
        for (int i = 0; i <tempR ; i++) {
            pool[k] = CardType.CR;
            k++;
        }
        for (int i = 0; i <tempSR ; i++) {
            pool[k] = CardType.SR;
            k++;
        }
        for (int i = 0; i <tempSSR ; i++) {
            pool[k] = CardType.SSR;
            k++;
        }
        return pool;
    }

    private int randomNum(){
        int num = (int) (1000*Math.random());
        return num;
    }
}

package com.moci.simhappy.func;

import android.util.Log;

import com.moci.simhappy.util.CardType;
import com.moci.simhappy.util.Constant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SimpleGetManager {
    private int P_SSR;
    private int P_SR;
    private int P_R;


    private int P_SP_SR;
    private int P_SP_SSR;

    public static SimpleGetManager getInstance() {
        return ManageHoder.INSTANCE;
    }

    private static final class ManageHoder {
        private static final SimpleGetManager INSTANCE = new SimpleGetManager();
    }

    private SimpleGetManager() {
        setPR(-1,-1,-1,-1,-1);
    }

    public void setPR(int ssr, int sr, int r, int sp_ssr, int sp_sr){
        if(ssr < 0 || sr < 0 || r < 0 || sp_ssr <0 || sp_sr < 0){
            this.P_SSR = Constant.DEFAULT_SSR;
            this.P_SR = Constant.DEFAULT_SR;
            this.P_R = Constant.DEFAULT_R;

            this.P_SP_SSR = Constant.DEFAULT_SP_SSR;
            this.P_SP_SR = Constant.DEFAULT_SP_SR;
            return;
        }

        this.P_SSR = ssr;
        this.P_SR = sr;
        this.P_R = (1000 - ssr - sr - sp_ssr - sp_sr) > 0 ? (1000 - ssr - sr - sp_ssr - sp_sr):0;

        this.P_SP_SSR = sp_ssr;
        this.P_SP_SR = sp_sr;
    }

    public CardType getType(){
        CardType[] pool = getCardPool();
        shuffle(pool);
        int num = randomNum();
        Log.w("moci", "getType: "+num);
        return pool[num];
    }

    private CardType[] getCardPool(){
        CardType[] pool = new CardType[1000];
        int tempSSR = this.P_SSR;
        int tempSR = this.P_SR;
        int tempR = this.P_R;

        int tempSPSSR = this.P_SP_SSR;
        int tempSPSR = this.P_SP_SR;
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
        for (int i = 0; i <tempSPSSR ; i++) {
            pool[k] = CardType.SSR_sp;
            k++;
        }
        for (int i = 0; i <tempSPSR ; i++) {
            pool[k] = CardType.SR_sp;
            k++;
        }
        return pool;
    }

    private int randomNum(){
        int num = new Random().nextInt(1000);
        return num;
    }

    private void shuffle(CardType[] array){
        if(array == null || array.length == 0) return;
        ArrayList<CardType> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        Collections.shuffle(list);
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return;
    }
}

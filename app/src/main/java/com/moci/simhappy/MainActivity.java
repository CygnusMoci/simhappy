package com.moci.simhappy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.moci.simhappy.func.SimpleGetManager;
import com.moci.simhappy.util.CardType;
import com.moci.simhappy.util.Constant;
import com.moci.simhappy.util.ImageAdapter;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_solo; // 单抽
    private Button btn_tenfold; // 十连

    private TextView safe_fifity; // 保底计数器
    private int sum_Safe_fifty;
    private TextView safe_newhun;
    private int sum_Safe_newhun;

    private TextView ssrNum; // 抽卡计数
    private TextView srNum;
    private TextView rNum;

    private int sumR;
    private int sumSR;
    private int sumSSR;

    private Button clear; // 清空数据

    private GridView gridView; // 抽卡结果布局
    private ImageView card_view; // 显示全图

    private Random mRandom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initWidget();
    }

    private void initData(){
        sumR = 0;
        sumSR = 0;
        sumSSR = 0;
        sum_Safe_fifty = 0;
        sum_Safe_newhun = 0;
        mRandom = new Random();
    }

    private void initWidget(){
        btn_solo = findViewById(R.id.btn_solo);
        btn_tenfold = findViewById(R.id.btn_tenfold);
        btn_solo.setOnClickListener(this);
        btn_tenfold.setOnClickListener(this);

        // test
        card_view = findViewById(R.id.card_view);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                card_view.setVisibility(View.INVISIBLE);
                card_view.setClickable(false);

                btn_solo.setVisibility(View.VISIBLE);
                btn_tenfold.setVisibility(View.VISIBLE);
            }
        });

        // 抽卡结果
        gridView = findViewById(R.id.result);

        // 计数器
        rNum = findViewById(R.id.rNum);
        srNum = findViewById(R.id.srNum);
        ssrNum = findViewById(R.id.ssrNum);

        safe_fifity = findViewById(R.id.safe_fifity);
        safe_newhun = findViewById(R.id.safe_newhun);
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(this);
    }

    private void soloGet(){
        CardType type = SimpleGetManager.getInstance().getType();
        if(type.equals(CardType.valueOf("SSR_sp"))){
            Constant.isNewHun = true;
        }else {
            sum_Safe_newhun++;
        }
        updateSum(type);
        Integer[] icon = new Integer[1];
        if(type.equals(CardType.valueOf("SSR")) || type.equals(CardType.valueOf("SSR_sp"))){
            sum_Safe_fifty = 0;
        }else {
            sum_Safe_fifty++;
        }
        icon[0] = type.getIcon();

        boolean ishun = false;
        if(!Constant.isNewHun && sum_Safe_newhun >= 100){
            Constant.isNewHun = true;
            ishun = true;
            icon[0] = R.drawable.ssr1;
            sum_Safe_fifty = 0;
            sumSSR++;
        }

        if(Constant.isFifty && sum_Safe_fifty >= 50){
            icon[0] = R.drawable.ssr2;
            int num = mRandom.nextInt(Constant.DEFAULT_SP_SSR + Constant.DEFAULT_SSR);
            if(num < Constant.DEFAULT_SP_SSR) {
                Constant.isNewHun = true;
                icon[0] = R.drawable.ssr1;
            }
            if(!ishun)sumSSR++;
            sum_Safe_fifty = 0;
        }

        setResult(icon);
    }

    private void tenfoldGet(){
        Integer[] icon = new Integer[10];
        boolean isR = false;
        int tempTen = 0; // 十连保底计数
        for (int i = 0; i < 10; i++) {
            CardType type = SimpleGetManager.getInstance().getType();

            if(type.equals(CardType.valueOf("SSR_sp"))){
                Constant.isNewHun = true;
            }else {
                sum_Safe_newhun++;
            }

            if(type.equals(CardType.valueOf("CR"))){
                tempTen++;
            }
            if(type.equals(CardType.valueOf("SSR")) || type.equals(CardType.valueOf("SSR_sp"))){
                sum_Safe_fifty = 0;
            }else {
                sum_Safe_fifty++;
            }
            updateSum(type);
            icon[i] = type.getIcon();

            if(Constant.isTen && tempTen == 10){
                icon[i] = R.drawable.sr2;
                int num = mRandom.nextInt(Constant.DEFAULT_SR+Constant.DEFAULT_SP_SR);
                if(num < Constant.DEFAULT_SP_SR) {
                    icon[i] = R.drawable.sr1;
                }
                sumSR++;
            }
            boolean ishun = false;
            if(!Constant.isNewHun && sum_Safe_newhun >= 100){
                Constant.isNewHun = true;
                ishun = true;
                icon[0] = R.drawable.ssr1;
                sum_Safe_fifty = 0;
                sumSSR++;
            }
            if(Constant.isFifty && sum_Safe_fifty >= 50){
                icon[i] = R.drawable.ssr2;
                int num = mRandom.nextInt(Constant.DEFAULT_SSR+Constant.DEFAULT_SP_SSR);
                if(num < Constant.DEFAULT_SP_SSR) {
                    Constant.isNewHun = true;
                    icon[i] = R.drawable.ssr1;
                }
                sumSR--;
                if(!ishun)sumSSR++;
                sum_Safe_fifty = 0;
            }
        }

        setResult(icon);
    }

    private void updateSum(CardType type){
        int tempNum;
        int tempNewNum;
        if(type == null){
            tempNum= (50-this.sum_Safe_fifty)>0? (50-this.sum_Safe_fifty):0;
            tempNewNum= (100-this.sum_Safe_newhun)>0? (100-this.sum_Safe_newhun):0;
            safe_fifity.setText(tempNum+"");
            if(Constant.isNewHun) {
                safe_newhun.setText("已获得");
            }else {
                safe_newhun.setText(tempNewNum+"");
            }
            rNum.setText(" "+this.sumR+" ");
            srNum.setText(" "+this.sumSR+" ");
            ssrNum.setText(" "+this.sumSSR+" ");
            return;
        }

        if(type.equals(CardType.valueOf("SSR")) || type.equals(CardType.valueOf("SSR_sp"))){
            this.sumSSR++;
        }
        if(type.equals(CardType.valueOf("SR")) || type.equals(CardType.valueOf("SR_sp"))){
            this.sumSR++;
        }
        if(type.equals(CardType.valueOf("CR"))){
            this.sumR++;
        }
        tempNum= (50-this.sum_Safe_fifty)>0? (50-this.sum_Safe_fifty):0;
        safe_fifity.setText(tempNum+"");
        tempNewNum= (100-this.sum_Safe_newhun)>0? (100-this.sum_Safe_newhun):0;
        if(Constant.isNewHun) {
            safe_newhun.setText("已获得");
        }else {
            safe_newhun.setText(tempNewNum+"");
        }
        rNum.setText(" "+this.sumR+" ");
        srNum.setText(" "+this.sumSR+" ");
        ssrNum.setText(" "+this.sumSSR+" ");

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_solo:
                soloGet();
                break;
            case R.id.btn_tenfold:
                tenfoldGet();
                break;
            case R.id.clear:
                clear();
                break;
        }
    }

    private void setResult(Integer[] icon){
        updateSum(null);
        gridView.setAdapter(new ImageAdapter(this,card_view,btn_solo,btn_tenfold,icon));
    }

    private void clear(){
        sumR = 0;
        sumSR = 0;
        sumSSR = 0;
        sum_Safe_fifty = 0;
        sum_Safe_newhun = 0;
        Constant.isNewHun = false;
        gridView.setAdapter(new ImageAdapter(this,card_view,btn_solo,btn_tenfold,new Integer[0]));
        updateSum(null);
    }
}

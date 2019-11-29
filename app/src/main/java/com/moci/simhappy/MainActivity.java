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

import com.moci.simhappy.util.Constant;
import com.moci.simhappy.util.ImageAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , CompoundButton.OnCheckedChangeListener{

    private EditText ed_P_ssr;
    private EditText ed_P_sr;
    private EditText ed_P_r;

    private Button btn_setPR;  // 设置概率

    private Button btn_solo; // 单抽
    private Button btn_tenfold; // 十连

    private CheckBox cb_Safe_ten; // 开启十连SR保底
    private CheckBox cb_Safe_fifty; // 开启五十连SSR保底

    private int ssrNum; // 抽卡计数
    private int srNum;
    private int rNum;

    private GridView gridView; // 抽卡结果布局
    private ImageView card_view; // 显示全图
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initWidget();
    }

    private void initData(){

    }

    private void initWidget(){
        ed_P_ssr = findViewById(R.id.ed_P_ssr);
        ed_P_sr = findViewById(R.id.ed_P_sr);
        ed_P_r = findViewById(R.id.ed_P_r);

        btn_setPR = findViewById(R.id.btn_setPR);
        btn_solo = findViewById(R.id.btn_solo);
        btn_tenfold = findViewById(R.id.btn_tenfold);
        btn_setPR.setOnClickListener(this);
        btn_solo.setOnClickListener(this);
        btn_tenfold.setOnClickListener(this);

        cb_Safe_ten = findViewById(R.id.cb_Safe_ten);
        cb_Safe_fifty = findViewById(R.id.cb_Safe_fifty);
        cb_Safe_ten.setOnCheckedChangeListener(this);
        cb_Safe_fifty.setOnCheckedChangeListener(this);


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

        gridView = findViewById(R.id.result);
        gridView.setAdapter(new ImageAdapter(this,card_view,btn_solo,btn_tenfold));



    }

    private void setPR(){

    }
    private void soloGet(){

    }

    private void tenfoldGet(){

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
            case R.id.btn_setPR:
                setPR();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        int id = compoundButton.getId();
        switch (id) {
            case R.id.cb_Safe_ten:
                Constant.isTen = cb_Safe_ten.isChecked();
                break;
            case R.id.cb_Safe_fifty:
                Constant.isFifty = cb_Safe_fifty.isChecked();
                break;
        }
    }
}

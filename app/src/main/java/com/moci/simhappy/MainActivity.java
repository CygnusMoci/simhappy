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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , CompoundButton.OnCheckedChangeListener{

    private EditText ed_P_ssr; // 自设概率
    private EditText ed_P_sr;
    private EditText ed_P_r;

    private Button btn_setPR;  // 设置概率

    private Button btn_solo; // 单抽
    private Button btn_tenfold; // 十连

    private CheckBox cb_Safe_ten; // 开启十连SR保底
    private CheckBox cb_Safe_fifty; // 开启五十连SSR保底

    private TextView safe_fifity; // 保底计数器
    private int sum_Safe_fifty;

    private TextView ssrNum; // 抽卡计数
    private TextView srNum;
    private TextView rNum;

    private int sumR;
    private int sumSR;
    private int sumSSR;

    private Button clear; // 清空数据

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
        sumR = 0;
        sumSR = 0;
        sumSSR = 0;
        sum_Safe_fifty = 0;
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

        // 抽卡结果
        gridView = findViewById(R.id.result);

        // 计数器
        rNum = findViewById(R.id.rNum);
        srNum = findViewById(R.id.srNum);
        ssrNum = findViewById(R.id.ssrNum);

        safe_fifity = findViewById(R.id.safe_fifity);
        clear = findViewById(R.id.clear);
        clear.setOnClickListener(this);
    }

    private void setPR(){
        if(ed_P_ssr.getText().toString().length() == 0 ||
                ed_P_sr.getText().toString().length() == 0 ||
                ed_P_r.getText().toString().length() == 0){
            Toast.makeText(this,"存在概率为空",Toast.LENGTH_SHORT).show();
            return;
        }
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        String ssrStr = ed_P_ssr.getText().toString();
        String srStr = ed_P_sr.getText().toString();
        String rStr = ed_P_r.getText().toString();
        if(pattern.matcher(ssrStr).matches() &&
                pattern.matcher(ssrStr).matches() &&
                pattern.matcher(ssrStr).matches() ){
            int P_SSR = Integer.parseInt(ssrStr);
            int P_SR = Integer.parseInt(srStr);
            int P_R = Integer.parseInt(rStr);
            SimpleGetManager.getInstance().setPR(P_SSR,P_SR,P_R);
            Toast.makeText(this,"设置概率成功！",Toast.LENGTH_SHORT).show();
            clear();
            return;
        }
        Toast.makeText(this,"概率不为数字",Toast.LENGTH_SHORT).show();
        return;
    }
    private void soloGet(){
        CardType type = SimpleGetManager.getInstance().getType();
        updateSum(type);
        Integer[] icon = new Integer[1];
        if(!type.equals(CardType.valueOf("SSR")) ){
            sum_Safe_fifty++;
        }else {
            sum_Safe_fifty = 0;
        }
        icon[0] = type.getIcon();
        if(Constant.isFifty && sum_Safe_fifty > 50){
            icon[0] = R.drawable.ssr;
            sumSSR++;
            sum_Safe_fifty = 0;
        }
        updateSum(null);
        gridView.setAdapter(new ImageAdapter(this,card_view,btn_solo,btn_tenfold,icon));
    }

    private void tenfoldGet(){
        Integer[] icon = new Integer[10];
        boolean isR = false;
        int tempTen = 0; // 十连保底计数
        for (int i = 0; i < 10; i++) {
            CardType type = SimpleGetManager.getInstance().getType();
            if(type.equals(CardType.valueOf("CR"))){
                tempTen++;
            }
            if(!type.equals(CardType.valueOf("SSR")) ){
                sum_Safe_fifty++;
            }else {
                sum_Safe_fifty = 0;
            }
            updateSum(type);
            icon[i] = type.getIcon();
        }
        if(Constant.isTen && tempTen == 10){
            icon[9] = R.drawable.sr;
            sumSR++;
        }
        if(Constant.isFifty && sum_Safe_fifty > 50){
            icon[9] = R.drawable.ssr;
            sumSR--;
            sumSSR++;
            sum_Safe_fifty = 0;
        }
        updateSum(null);
        gridView.setAdapter(new ImageAdapter(this,card_view,btn_solo,btn_tenfold,icon));
    }

    private void updateSum(CardType type){
        if(type == null){
            int tempNum= (50-this.sum_Safe_fifty)>0? (50-this.sum_Safe_fifty):0;
            safe_fifity.setText(tempNum+"");
            rNum.setText(" "+this.sumR+" ");
            srNum.setText(" "+this.sumSR+" ");
            ssrNum.setText(" "+this.sumSSR+" ");
            return;
        }

        if(type.equals(CardType.valueOf("SSR"))){
            this.sumSSR++;
        }
        if(type.equals(CardType.valueOf("SR"))){
            this.sumSR++;
        }
        if(type.equals(CardType.valueOf("CR"))){
            this.sumR++;
        }
        int tempNum= (50-this.sum_Safe_fifty)>0? (50-this.sum_Safe_fifty):0;
        safe_fifity.setText(tempNum+"");
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
            case R.id.btn_setPR:
                setPR();
                break;
            case R.id.clear:
                clear();
                break;
        }
    }

    private void clear(){
        sumR = 0;
        sumSR = 0;
        sumSSR = 0;
        sum_Safe_fifty = 0;
        updateSum(null);
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

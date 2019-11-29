package com.moci.simhappy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.moci.simhappy.util.Constant;

public class LoadingActivity extends AppCompatActivity {

    private Handler mHandler;
    private TextView version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        mHandler = new Handler();
        enterNextPage();

        version = findViewById(R.id.version);
        version.setText(Constant.VERSION);

    }

    private void enterNextPage() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                finish();
            }
        }, 2400);
    }
}

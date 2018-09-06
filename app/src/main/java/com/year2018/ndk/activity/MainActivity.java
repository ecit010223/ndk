package com.year2018.ndk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.year2018.ndk.R;

/**
 * Author: zyh
 * Date: 2018/9/4 8:57
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI(){
        findViewById(R.id.btn_main_imooc).setOnClickListener(this);
        findViewById(R.id.btn_main_thread).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_main_imooc:
                ImoocActivity.entry(this);
                break;
            case R.id.btn_main_thread:
                ThreadActivity.entry(this);
                break;
        }
    }
}

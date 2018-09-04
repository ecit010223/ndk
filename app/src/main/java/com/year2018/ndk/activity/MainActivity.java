package com.year2018.ndk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.year2018.ndk.R;

/**
 * Author: zyh
 * Date: 2018/9/4 8:57
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI(){
        ((Button)findViewById(R.id.btn_main_imooc)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_main_imooc:
                ImoocActivity.entry(this);
                break;
        }
    }
}

package com.custom.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.custom.view.SwitchView;

public class MainActivity extends AppCompatActivity {
    SwitchView switchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchView=(SwitchView)findViewById(R.id.my_switch);
    }
}

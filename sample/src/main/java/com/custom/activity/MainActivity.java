package com.custom.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.custom.switches.SwitchView;


public class MainActivity extends AppCompatActivity {
    SwitchView switchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        switchView=(SwitchView)findViewById(R.id.my_switch);
        switchView.setStatus(new String[]{"备用","备用","正常"});
        switchView.setOnClickStateChange(new SwitchView.OnClickStateChange() {
            @Override
            public void onStateChange(int state, String statusText) {
                Toast.makeText(getApplicationContext(),statusText,Toast.LENGTH_LONG).show();
            }
        });
    }
}

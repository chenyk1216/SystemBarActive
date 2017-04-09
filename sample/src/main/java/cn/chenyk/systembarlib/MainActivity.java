package cn.chenyk.systembarlib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.chenyk.systembarkit.manager.SystemBarManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new SystemBarManager.builder(this).setStatusBarColor(R.color.colorAccent).build();
    }
}

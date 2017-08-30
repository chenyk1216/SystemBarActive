package cn.chenyk.systembarlib;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import cn.chenyk.systembarkit.manager.SystemBarManager;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button btnActiveResult, btnFullBgIv, btnPartIv;
    private RadioGroup tintTypeGroup, colorGroup;
    private String tintTypeStr, colorStr;
    private TextView tvAlpha;
    private SeekBar seekbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new SystemBarManager.builder(this)
                .setSystemBarColor(Color.parseColor("#ff5645"))//状态栏颜色
                .build();

        seekbar = (SeekBar) findViewById(R.id.seekbar);
        tintTypeGroup = (RadioGroup) findViewById(R.id.tintType_group);
        colorGroup = (RadioGroup) findViewById(R.id.color_group);
        tvAlpha = (TextView) findViewById(R.id.tv_alpha);
        tintTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) MainActivity.this.findViewById(radioButtonId);
                tintTypeStr = "0".equals(rb.getTag()) ? "0" : "1";
            }
        });
        colorGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int radioButtonId = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) MainActivity.this.findViewById(radioButtonId);
                colorStr = "0".equals(rb.getTag()) ? "red" : "1".equals(rb.getTag()) ? "green" : "blue";
            }
        });
        ((RadioButton) tintTypeGroup.getChildAt(0)).setChecked(true);//默认选中第一个选项
        ((RadioButton) colorGroup.getChildAt(0)).setChecked(true);//默认选中第一个选项
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvAlpha.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btnActiveResult = (Button) findViewById(R.id.btn_active_result);
        btnActiveResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResultActivity.startCurrentActivity(MainActivity.this, tintTypeStr,
                        tvAlpha.getText().toString(), colorStr);
            }
        });

        init();
    }

    /**
     * 初始化操作
     */
    private void init() {
        btnPartIv = (Button) findViewById(R.id.btn_part_iv);
        btnFullBgIv = (Button) findViewById(R.id.btn_full_bg_iv);

        btnPartIv.setOnClickListener(this);
        btnFullBgIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_part_iv:
                startActivity(new Intent(MainActivity.this, PartImageViewActivity.class));
                break;
            case R.id.btn_full_bg_iv:
                startActivity(new Intent(MainActivity.this, FullBgImageViewActivity.class));
                break;
            default:
                break;
        }
    }
}

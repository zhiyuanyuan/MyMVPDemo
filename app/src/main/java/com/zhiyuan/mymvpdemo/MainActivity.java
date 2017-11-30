package com.zhiyuan.mymvpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhiyuan.mymvpdemo.mvp.model.WeatherModel;
import com.zhiyuan.mymvpdemo.mvp.presenter.WeatherPresenter;
import com.zhiyuan.mymvpdemo.mvp.view.WheatherView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, WheatherView {
    private Button btn;
    private TextView tv_show;
    private EditText edt;
    private WeatherPresenter weatherpresenter = new WeatherPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        tv_show = findViewById(R.id.tv_show);
        btn = findViewById(R.id.btn);
        edt = findViewById(R.id.edt);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn:

                weatherpresenter.loadData(edt.getText().toString());

                break;
        }


    }

    @Override
    public void loadSuccess(WeatherModel weatherModel) {
        tv_show.setText("  "+weatherModel.getRetData().getWeather()+"  "+weatherModel.getRetData().getWD());

    }
}

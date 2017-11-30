package com.zhiyuan.mymvpdemo.mvp.view;

import com.zhiyuan.mymvpdemo.mvp.model.WeatherModel;

/**
 * Created by admin on 2017/11/30.
 */

public interface WheatherView {
    void loadSuccess(WeatherModel weatherModel);
}

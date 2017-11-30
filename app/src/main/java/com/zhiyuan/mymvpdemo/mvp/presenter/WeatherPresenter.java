package com.zhiyuan.mymvpdemo.mvp.presenter;

import com.zhiyuan.mymvpdemo.base.BasePresenter;
import com.zhiyuan.mymvpdemo.mvp.model.WeatherModel;
import com.zhiyuan.mymvpdemo.mvp.view.WheatherView;

import rx.Subscriber;

/**
 * Created by admin on 2017/11/30.
 */

public class WeatherPresenter extends BasePresenter<WheatherView> {


    public WeatherPresenter(WheatherView view) {
        attachView(view);
    }

    public void loadData(String city){
        addSubscription(mApiService.loadDataByRetrofitRxjava(city), new Subscriber<WeatherModel>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeatherModel weatherModel) {
                   mvpView.loadSuccess(weatherModel);
            }
        });
    }
}

package com.zhiyuan.mymvpdemo.retrofit;

import com.zhiyuan.mymvpdemo.mvp.model.WeatherModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2017/11/30.
 */

public interface ApiService {
    //baseUrl
    String API_SERVER_URL = "http://apistore.baidu.com/microservice/";

    @GET("weather")
    Observable<WeatherModel> loadDataByRetrofitRxjava(@Query("citypinyin") String cityId);

}

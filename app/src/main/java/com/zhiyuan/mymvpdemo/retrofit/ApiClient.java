package com.zhiyuan.mymvpdemo.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2017/11/30.
 */

public class ApiClient {
  public static Retrofit retrofit;
  public static Retrofit retrofit(){
      OkHttpClient.Builder builder=new OkHttpClient.Builder();
      builder.connectTimeout(5, TimeUnit.SECONDS);
      if(retrofit==null){
       retrofit=new Retrofit.Builder()
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
             //  .baseUrl(ApiService.API_SERVER_URL)
               .client(builder.build())
               .build();
      }

      return retrofit;
  }

}

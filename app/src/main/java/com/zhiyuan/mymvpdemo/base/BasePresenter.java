package com.zhiyuan.mymvpdemo.base;

import com.zhiyuan.mymvpdemo.retrofit.ApiClient;
import com.zhiyuan.mymvpdemo.retrofit.ApiService;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/11/30.
 */

public class BasePresenter<V>  {
    public V mvpView;
    protected ApiService mApiService;
    private CompositeSubscription mCompositeSubscription;
    public void attachView(V mvpView){
        this.mvpView=mvpView;
       mApiService= ApiClient.retrofit().create(ApiService.class);
    }

    public void detachView(){
        mvpView=null;
        unSubscibe();
    }

    private void unSubscibe() {
             if(mCompositeSubscription!=null&&mCompositeSubscription.hasSubscriptions()){
                 mCompositeSubscription.unsubscribe();
             }
    }


    public<T> void addSubscription(Observable<T> observable, Subscriber<T> subscriber){
             if(mCompositeSubscription==null){
                 mCompositeSubscription=new CompositeSubscription();
             }
             mCompositeSubscription.add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber));
    }
}

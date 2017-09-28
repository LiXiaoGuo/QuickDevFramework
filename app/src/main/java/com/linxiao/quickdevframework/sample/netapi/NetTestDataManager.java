package com.linxiao.quickdevframework.sample.netapi;

import com.linxiao.framework.manager.BaseDataManager;
import com.linxiao.framework.net.CookieMode;
import com.linxiao.framework.net.HttpInfoCatchInterceptor;
import com.linxiao.framework.net.HttpInfoCatchListener;
import com.linxiao.framework.net.HttpInfoEntity;
import com.linxiao.framework.net.RetrofitManager;
import com.linxiao.framework.net.interceptor.CacheInterceptor;
import com.linxiao.framework.net.interceptor.CaheInterceptor;
import com.linxiao.quickdevframework.SampleApplication;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Internal;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.cache.InternalCache;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 分层架构简单示例
 * Created by linxiao on 2017/7/13.
 */
public class NetTestDataManager extends BaseDataManager {
    
    private ClientApi clientApi;
    private Retrofit retrofit;

    public NetTestDataManager() {
        // 这些配置可以放在App工程的网络模块中，这里简要处理就不写了
        HttpInfoCatchInterceptor infoCatchInterceptor = new HttpInfoCatchInterceptor();
        infoCatchInterceptor.setCatchEnabled(true);
        infoCatchInterceptor.setHttpInfoCatchListener(new HttpInfoCatchListener() {
            @Override
            public void onInfoCaught(HttpInfoEntity entity) {
                entity.logOut();
                //do something......
            }
        });
        clientApi = RetrofitManager.createRetrofitBuilder("http://ysheng.lizardmind.com/")
                .setCookieMode(CookieMode.ADD_BY_ANNOTATION)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCustomInterceptor(infoCatchInterceptor)
                .addCustomInterceptor(new CacheInterceptor())
                .addCustomNetworkInterceptor(new CacheInterceptor())
                .build(ClientApi.class);
        retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/").addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder().cache(new Cache(new File(RetrofitManager.CACHE_PATH),100))
                        .addInterceptor(new CaheInterceptor()).addNetworkInterceptor(new CaheInterceptor())
                        .connectTimeout(5, TimeUnit.SECONDS).build())
                .build();
    }
    
    /**
     * 获取测试数据
     * */
    public Observable<String> getTestData() {
        return retrofit.create(ClientApi.class).getMeiZhi();
    }
}

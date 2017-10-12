package com.linxiao.quickdevframework.sample.netapi;

import com.linxiao.framework.manager.BaseDataManager;
import com.linxiao.framework.net.BaseAPI;
import com.linxiao.framework.net.CookieMode;
import com.linxiao.framework.net.HttpInfoCatchInterceptor;
import com.linxiao.framework.net.HttpInfoCatchListener;
import com.linxiao.framework.net.HttpInfoEntity;
import com.linxiao.framework.net.RetrofitManager;
import com.linxiao.framework.net.interceptor.CacheInterceptor;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 分层架构简单示例
 * Created by linxiao on 2017/7/13.
 */
public class NetTestDataManager extends BaseDataManager {
    
    private BaseAPI clientApi;
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
        clientApi = RetrofitManager.createRetrofitBuilder("http://api.juheapi.com/")
                .setCookieMode(CookieMode.ADD_BY_ANNOTATION)
                .addConvertFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCustomInterceptor(infoCatchInterceptor)
                .addCustomInterceptor(new CacheInterceptor())
                .build(BaseAPI.class);
//        retrofit = new Retrofit.Builder().baseUrl("http://gank.io/api/")
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .client(new OkHttpClient.Builder().cache(new Cache(new File(CacheManager.INSTANCE.getCACHE_PATH()),100))
//                        .addInterceptor(new CacheInterceptor())
////                        .addNetworkInterceptor(new CacheInterceptor())
//                        .connectTimeout(5, TimeUnit.SECONDS).build())
//                .build();
    }
    
    /**
     * 获取测试数据
     * */
    public Observable<String> getTestData() {
//        ClientApi ca =  new RxCache.Builder().persistence(new File(RetrofitManager.CACHE_PATH),new GsonSpeaker())
//                .using(ClientApi.class);
//        return retrofit.create(ClientApi.class).getMeiZhi();
//        return clientApi.getDefault(new HashMap<String, String>(){{
//            put("key","222222222222");
//            put("v","1.0");
//            put("month","11");
//            put("day","1");
//        }});
//        return clientApi.getDefault("http://api.juheapi.com/japi/toh?day=1&v=1.0&key=222222222222&month=11");
        return clientApi.postApi("japi/toh",new HashMap<String, String>(){{
            put("key","222222222222");
            put("v","1.0");
            put("month","11");
            put("day","1");
        }},60*1000);
//        return ca.getMeiZhi();
    }
}

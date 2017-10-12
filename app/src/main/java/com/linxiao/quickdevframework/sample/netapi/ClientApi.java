package com.linxiao.quickdevframework.sample.netapi;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.LifeCache;
import io.rx_cache2.ProviderKey;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

import static com.linxiao.framework.net.FrameworkNetConstants.ADD_COOKIE_HEADER_STRING;

/**
 *
 * Created by LinXiao on 2016-12-04.
 */
public interface ClientApi {

    @Streaming
    @Headers("Cache-Control:public,max-age=60")
    //@Headers(ADD_COOKIE_HEADER_STRING)
    @GET("/")
    Observable<String> getWeather(@Query("api") String cityId);

    @LifeCache(duration = 1,timeUnit = TimeUnit.MINUTES)
    @GET("random/data/福利/1")
    Observable<String> getMeiZhi();

    @FormUrlEncoded
    @POST("/japi/toh")
    Observable<String> getDefault(@FieldMap Map<String, String> options);

    @Streaming
    @GET
    Observable<String> getDefault(@Url String url);

    @Streaming
    @GET("/japi/toh")
    Observable<String> getDefault(@Query("day")String day, @Query("v")String v, @Query("key")String key, @Query("month")String month, @Header("time")Long time);
}

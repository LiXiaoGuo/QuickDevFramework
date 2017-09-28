package com.linxiao.quickdevframework.sample.netapi;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

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

    @GET("random/data/福利/1")
    Observable<String> getMeiZhi();
}

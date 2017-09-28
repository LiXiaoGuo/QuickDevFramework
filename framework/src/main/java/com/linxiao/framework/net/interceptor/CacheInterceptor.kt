package com.linxiao.framework.net.interceptor

import android.util.Log
import com.linxiao.framework.util.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 缓存拦截器
 * Created by Extends on 2017/9/28 10:52
 */
class CacheInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if(NetworkUtils.isAvailableByPing){
            val response = chain.proceed(request)
            val maxAge = 60*60
            val cacheControl = request.cacheControl().toString()
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control","public,max-age="+maxAge)
                    .build()
        }else{
            Log.e("----------","------------")
            request=request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            val response = chain.proceed(request)
            val maxStale = 60 * 60 * 24 * 3
            return return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control","public,only-if-cached,max-stale="+maxStale)
                    .build()
        }
    }
}
package com.linxiao.framework.net

import io.reactivex.Observable
import retrofit2.http.*

/**
 * 公用基础接口
 * Created by Extends on 2017/10/11 10:37
 */
interface BaseAPI {
    /**
     * 基础get请求
     * @param url 请求地址(绝对地址)
     * @param time 缓存过期时间,单位是毫秒,time>0才进行缓存
     *              传时间间隔,eg:3000,表示3s后过期
     */
    @Streaming
    @GET
    fun getApi(@Url url: String,@Header("time") time:Long): Observable<String>

    /**
     * 基础get请求
     * @param path 请求地址(相对地址)
     * @param options 键值对参数
     * @param time 缓存过期时间,单位是毫秒,time>0才进行缓存
     *              传时间间隔,eg:3000,表示3s后过期
     */
    @Streaming
    @GET("/{path}")
    fun getApi(@Path(value = "path", encoded = true)path:String,@QueryMap options: Map<String, String>,@Header("time") time:Long): Observable<String>

    /**
     * 基础post请求
     * @param options 键值对参数
     * @param time 缓存过期时间,单位是毫秒,time>0才进行缓存
     *              传时间间隔,eg:3000,表示3s后过期
     */
    @FormUrlEncoded
    @POST("/")
    fun postApi(@FieldMap options: Map<String, String>,@Header("time") time:Long): Observable<String>


    /**
     * 基础post请求
     * @param path 请求地址(相对地址)
     * @param options 键值对参数
     * @param time 缓存过期时间,单位是毫秒,time>0才进行缓存
     *              传时间间隔,eg:3000,表示3s后过期
     */
    @FormUrlEncoded
    @POST("/{path}")
    fun postApi(@Path(value = "path",encoded = true)path: String,@FieldMap options: Map<String, String>,@Header("time") time:Long): Observable<String>


    //TODO:上传文件 or 上传图片 接口
}
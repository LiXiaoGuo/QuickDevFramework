package com.linxiao.framework.net.interceptor

import android.util.Log
import com.linxiao.framework.net.cache.CacheManager
import okhttp3.*
import okio.Buffer
import okio.BufferedSource
import java.io.ByteArrayInputStream
import java.io.EOFException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException


/**
 * 缓存拦截器
 * Created by Extends on 2017/9/28 10:52
 */
class CacheInterceptor:Interceptor {
    private val TAG = "CacheInterceptor"
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val time = try {
            request.header("time").toLong()
        }catch (e:Exception){
            -1L
        }
        //移除time响应头,以免出现未知bug
        request = request.newBuilder().removeHeader("time").build()
        //获取缓存文件名
        val key = getRequestKey(request)
        val isExist = CacheManager.isExist(key)
        if (isExist){
            Log.i(TAG,"using the cache")
            val s =CacheManager.getData(key)!!

            //返回自己构造的Response
            return Response.Builder().body(object : ResponseBody(){
                override fun contentLength(): Long = s.length.toLong()
                override fun contentType(): MediaType? = MediaType.parse("application/json; charset=utf-8")
                override fun source(): BufferedSource= Buffer().readFrom(ByteArrayInputStream(s.toByteArray()))

            })
                    .request(request)
                    .protocol(Protocol.HTTP_1_1)
                    .code(200)
                    .message("OK")
                    .build()
        }
        Log.i(TAG,"not use the cache")
        val response = chain.proceed(request)

        if(time>0){
            val value = getResponBodyString(response)
            if (value!=null){
                CacheManager.saveData(key,value,time)
            }
        }
        return response
    }

    private val UTF8 = Charset.forName("UTF-8")

    private fun bodyEncoded(headers: Headers): Boolean {
        val contentEncoding = headers.get("Content-Encoding")
        return contentEncoding != null && !contentEncoding!!.equals("identity", ignoreCase = true)
    }

    @Throws(EOFException::class)
    fun isPlaintext(buffer: Buffer): Boolean {
        try {
            val prefix = Buffer()
            val byteCount = (if (buffer.size() < 64) buffer.size() else 64).toLong()
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false
                }
            }
            return true
        } catch (e: EOFException) {
            return false // Truncated UTF-8 sequence.
        }
    }

    /**
     * 获取request的参数,用来作为缓存文件的name
     */
    fun getRequestKey(request: Request):String{
        when(request.method()){
            "GET"->{
                return request.url().toString()
            }
            "POST"->{
                val requestBody = request.body()
                var value = ""
                if(requestBody is FormBody){
                    for (i in 0..requestBody.size()-1){
                        value+="&"+requestBody.encodedName(i)+"="+requestBody.encodedValue(i)
                    }
                    return request.url().toString()+"?"+value.substring(1)
                }else{
                    //TODO:其他类型的body暂不考虑
                }
                return "null"
            }
            else->{
                return "null"
            }
        }
    }

    /**
     * 获取respon的数据
     */
    fun getResponBodyString(response: Response):String?{
        val responseBody = response.body()
        if(responseBody!=null){
            val contentLength = responseBody.contentLength()
            if (!bodyEncoded(response.headers())) {
                val source = responseBody.source()
                source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
                val buffer = source.buffer()

                var charset = UTF8
                val contentType = responseBody.contentType()
                if (contentType != null) {
                    try {
                        charset = contentType.charset(UTF8)
                    } catch (e: UnsupportedCharsetException) {
                        return null
                    }
                }

                if (!isPlaintext(buffer)) {
                    return null
                }

                if (contentLength != 0L) {
                    val result = buffer.clone().readString(charset)
                    return result
                }
            }
        }
        return null
    }
}
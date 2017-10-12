package com.linxiao.quickdevframework.sample.frameworkapi

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableField
import android.util.Log
import android.view.View
import com.linxiao.framework.viewModel.LifecycleViewModel
import com.linxiao.quickdevframework.BR
import com.linxiao.quickdevframework.SampleApplication

/**
 *
 * Created by Extends on 2017/9/27 14:12
 */
class ApplicationApiVM:LifecycleViewModel() {
    //第一种方式监听数据
    var appName : ObservableField<String> = ObservableField<String>().apply{set("")}
    //第二种方式监听数据
    var aap_data=AAP_data()

    /**
     * 获取app名称
     */
    fun getAppName(view: View){
        appName.set(SampleApplication.getApplicationName())
    }

    /**
     * 获取app版本号
     */
    fun getAppVersion(view: View){
        aap_data.appVersion= SampleApplication.getApplicationVersionName()!!
    }

    /**
     * 当触发RESUME生命周期时触发该方法
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun start(){
        Log.e("0000","ViewModel 触发 ON_RESUME")
    }

    /**
     * 当触发PAUSE时触发该方法
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun start1(){
        Log.e("0000","ViewModel 触发 ON_PAUSE")
    }

}
class AAP_data: BaseObservable() {
    var appVersion:String=""
        @Bindable get()=field
        set(value) {
            field =value
            notifyPropertyChanged(BR.appVersion)
        }
}
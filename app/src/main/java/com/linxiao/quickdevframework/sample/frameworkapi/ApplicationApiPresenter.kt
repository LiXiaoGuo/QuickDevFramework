package com.linxiao.quickdevframework.sample.frameworkapi

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.databinding.ObservableField
import android.view.View
import com.linxiao.framework.databinding.BasePresenter
import com.linxiao.framework.databinding.LifecyclePresenter
import com.linxiao.quickdevframework.BR
import com.linxiao.quickdevframework.SampleApplication

/**
 *
 * Created by Extends on 2017/9/25 18:12
 */
class ApplicationApiPresenter: LifecyclePresenter() {
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
    fun getAppVersion(view:View){
        aap_data.appVersion= SampleApplication.getApplicationVersionName()!!
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
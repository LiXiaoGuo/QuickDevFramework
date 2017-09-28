package com.linxiao.quickdevframework.sample.frameworkapi

import android.app.Activity
import android.databinding.ObservableField
import android.util.Log
import android.view.View
import com.linxiao.framework.viewModel.LifecycleViewModel
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
}
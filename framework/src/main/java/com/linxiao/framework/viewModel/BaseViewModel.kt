package com.linxiao.framework.viewModel

import android.app.Activity
import android.arch.lifecycle.ViewModel
import java.lang.ref.WeakReference

/**
 * 持有activity的viewmodel
 * 主要用来显示加载框之类的
 * 注意:activity是弱引用,只要gc必然回收
 * Created by Extends on 2017/9/27 11:03
 */
open class BaseViewModel :ViewModel() {
    lateinit var activity:WeakReference<Activity>
}
package com.linxiao.framework.viewModel

import android.app.Activity
import android.arch.lifecycle.ViewModel
import android.support.v4.app.Fragment
import java.lang.ref.WeakReference

/**
 * 持有activity的viewmodel
 * 主要用来显示加载框之类的
 * 继承于ViewModel的作用是可以在转动屏幕等情况时持久化数据
 * TODO:activity...是弱引用,只要gc必然回收
 * Created by Extends on 2017/9/27 11:03
 */
open class BaseViewModel :ViewModel() {
    lateinit var activity:WeakReference<Activity>
    lateinit var fragment:WeakReference<Fragment>
}
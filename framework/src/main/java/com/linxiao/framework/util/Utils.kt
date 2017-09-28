package com.linxiao.framework.util

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle

import java.lang.ref.WeakReference
import java.util.LinkedList


/**
 * <pre>
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 16/12/08
 * desc  : Utils初始化相关
</pre> *
 */
@SuppressLint("StaticFieldLeak")
object Utils{
    @SuppressLint("StaticFieldLeak")
    private var sApplication: Application?=null

    internal var sTopActivityWeakRef: WeakReference<Activity>?=null
    internal var sActivityList: MutableList<Activity> = ArrayList()

    private val mCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            sActivityList.add(activity)
            setTopActivityWeakRef(activity)
        }

        override fun onActivityStarted(activity: Activity) {
            setTopActivityWeakRef(activity)
        }

        override fun onActivityResumed(activity: Activity) {
            setTopActivityWeakRef(activity)
        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            sActivityList.remove(activity)
        }
    }

    /**
     * 初始化工具类

     * @param app 应用
     */
    @JvmStatic fun init(app: Application) {
        Utils.sApplication = app
        app.registerActivityLifecycleCallbacks(mCallbacks)
    }

    /**
     * 获取Application

     * @return Application
     */
    @JvmStatic val app: Application
        get() {
            if (sApplication != null) return sApplication!!
            throw NullPointerException("u should init first")
        }

    private fun setTopActivityWeakRef(activity: Activity) {
        if (sTopActivityWeakRef == null || activity != sTopActivityWeakRef!!.get()) {
            sTopActivityWeakRef = WeakReference(activity)
        }
    }
}

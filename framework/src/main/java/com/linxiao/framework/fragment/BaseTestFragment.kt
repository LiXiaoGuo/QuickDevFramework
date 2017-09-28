package com.linxiao.framework.fragment

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable

/**
 *
 * Created by Extends on 2017/9/25 17:20
 */
abstract class BaseTestFragment : Fragment(),LifecycleRegistryOwner {
    var rootView: View? = null

    private val mCompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(rootView==null){
            rootView = onCreatRootView(inflater, container, savedInstanceState)
        }
        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onInitView(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mCompositeDisposable.clear()
    }



    protected abstract fun onCreatRootView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?):View
    protected abstract fun onInitView(savedInstanceState: Bundle?)


    /*****************      生命周期感知     ****************************/
    val lifecycleRegistry by lazy { LifecycleRegistry(this) }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}
package com.linxiao.quickdevframework.sample.frameworkapi

import android.annotation.SuppressLint
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linxiao.framework.fragment.BaseTestFragment
import com.linxiao.framework.viewModel.LifecycleViewModel
import com.linxiao.quickdevframework.R
import com.linxiao.quickdevframework.SampleApplication
import com.linxiao.quickdevframework.databinding.FragmentApplicationApiBinding
import kotlinx.android.synthetic.main.fragment_application_api.view.*
import java.lang.ref.WeakReference

/**
 * Application类提供API示例
 * Created by linxiao on 2017/2/17.
 */
class ApplicationApiFragment : BaseTestFragment() {
    private val binding by lazy { FragmentApplicationApiBinding.inflate(getLayoutInflater(null)) }

    override fun onCreatRootView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onInitView(savedInstanceState: Bundle?) {

        binding.apply {
            presenter = ViewModelProviders.of(this@ApplicationApiFragment).get(ApplicationApiVM::class.java) //ApplicationApiPresenter()
            presenter?.activity = WeakReference(activity)
        }
        //生命周期感知
        lifecycle.addObserver(binding.presenter)
        rootView!!.apply {
            ivAppIcon.setImageDrawable(SampleApplication.getApplicationIcon())
            tvIsAppRunning.text = getString(R.string.is_app_running) + ": " + SampleApplication.isAppForeground()
            tvIsAppForeground.text = getString(R.string.is_app_foreground) + ": " + SampleApplication.isAppForeground()
        }
    }
}

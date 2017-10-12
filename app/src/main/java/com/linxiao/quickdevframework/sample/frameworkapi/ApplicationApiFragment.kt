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
            //拿到viewModel对象并赋值
            presenter = ViewModelProviders.of(this@ApplicationApiFragment).get(ApplicationApiVM::class.java) //ApplicationApiPresenter()
            //赋值activity给ViewModel,主要用在显示对话框等场景,无需要可不设置
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

package com.linxiao.quickdevframework.sample.frameworkapi

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linxiao.framework.util.getDataBinding
import com.linxiao.framework.util.ke
import com.linxiao.framework.fragment.BaseTestFragment
import com.linxiao.quickdevframework.R
import com.linxiao.quickdevframework.databinding.FragmentDialogApiBinding
import java.lang.ref.WeakReference

class DialogApiFragment : BaseTestFragment() {
    private val binding by lazy { getDataBinding<FragmentDialogApiBinding>(R.layout.fragment_dialog_api) }

    override fun onCreatRootView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onInitView(savedInstanceState: Bundle?) {
        binding.apply {
            vm = ViewModelProviders.of(this@DialogApiFragment).get(DialogApiVM::class.java)
            vm?.fragment = WeakReference(this@DialogApiFragment)
            ke(null,"1")
        }
        lifecycle.addObserver(binding.vm)
    }
}

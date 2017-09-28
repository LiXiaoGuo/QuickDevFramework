package com.linxiao.quickdevframework.sample.frameworkapi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.linxiao.framework.fragment.BaseFragment
import com.linxiao.quickdevframework.R
import kotlinx.android.synthetic.main.fragment_toast_api.view.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.toast

class ToastApiFragment : BaseFragment() {

    internal var toastNum = 1

    override fun onCreateContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) {
        setContentView(R.layout.fragment_toast_api, container!!)
        contentView.btnShowToast.onClick {
            toast("toast " + toastNum++)
        }
    }
}

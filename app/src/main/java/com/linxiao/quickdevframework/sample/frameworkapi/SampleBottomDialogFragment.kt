package com.linxiao.quickdevframework.sample.frameworkapi

import android.app.Dialog
import android.view.View
import com.linxiao.framework.dialog.BaseBottomDialogFragment
import com.linxiao.quickdevframework.R
import com.linxiao.quickdevframework.databinding.DialogBottomSampleBinding

/**

 * Created by LinXiao on 2016-12-12.
 */
class SampleBottomDialogFragment : BaseBottomDialogFragment<DialogBottomSampleBinding>()
{
    override fun onCreatRootView(): Int = R.layout.dialog_bottom_sample

    override fun onInitView(dialog: Dialog, contentView: View) {
        contentView.setOnClickListener { dialog.dismiss() }
    }
}

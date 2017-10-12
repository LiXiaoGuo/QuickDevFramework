package com.linxiao.quickdevframework.sample.frameworkapi

import android.content.Intent
import android.databinding.ObservableField
import android.support.v4.content.ContextCompat
import android.view.View
import com.linxiao.framework.BaseApplication
import com.linxiao.framework.dialog.AlertDialogManager
import com.linxiao.framework.toast.ToastAlert
import com.linxiao.framework.viewModel.LifecycleViewModel
import com.linxiao.quickdevframework.R
import org.jetbrains.anko.support.v4.startService
import org.jetbrains.anko.support.v4.toast

/**
 *
 * Created by Extends on 2017/10/11 16:41
 */
class DialogApiVM: LifecycleViewModel() {
    var showTitle : ObservableField<Boolean> = ObservableField<Boolean>().apply{set(false)}
    var showIcon : ObservableField<Boolean> = ObservableField<Boolean>().apply{set(false)}
    var showPositive : ObservableField<Boolean> = ObservableField<Boolean>().apply{set(false)}
    var showNegative : ObservableField<Boolean> = ObservableField<Boolean>().apply{set(false)}
    var showCancelable : ObservableField<Boolean> = ObservableField<Boolean>().apply{set(true)}

    /**
     * 显示自定义的对话框
     */
    fun showAlertDialog(view:View){
        val builder = AlertDialogManager.createAlertDialogBuilder()
        builder.setMessage(BaseApplication.getResString(R.string.sample_dialog_message))
        if (showTitle.get()) {
            builder.setTitle(BaseApplication.getResString(R.string.sample_dialog_title))
        }
        if (showIcon.get()) {
            builder.setIcon(ContextCompat.getDrawable(fragment.get()?.context, R.drawable.ic_notify))
        }
        if (showPositive.get()) {
            builder.setPositiveText(BaseApplication.getResString(R.string.sample_positive))
            builder.setPositiveButton { dialogInterface, _ ->
                fragment.get()?.toast(BaseApplication.getResString(R.string.positive_click))
                dialogInterface.dismiss()
            }
        }
        if (showNegative.get()) {
            builder.setNegativeText(BaseApplication.getResString(R.string.sample_negative))
            builder.setNegativeButton { dialogInterface, _ ->
                fragment.get()?.toast(BaseApplication.getResString(R.string.negative_click))
                dialogInterface.dismiss()
            }
        }

        builder.setCancelable(showCancelable.get())

        builder.show()
    }

    /**
     * 简单的对话框
     */
    fun btnShowSimpleDialog(view:View){
        AlertDialogManager.showAlertDialog(BaseApplication.getAppContext().getString(R.string.sample_dialog_message))
    }

    /**
     * 打开一个Activity再显示一个对话框
     */
    fun btnShowOnStartActivity(view: View){
        fragment.get()?.apply {
            startActivity(Intent(this.context, NotificationTargetActivity::class.java))
        }
        AlertDialogManager.showAlertDialog("dialog after start activity")
    }

    /**
     * 通过启动服务的方式弹出一个对话框
     */
    fun btnShowTopDialog(view: View){
        fragment.get()?.apply {
            startService<BackgroundService>()
        }
    }

    /**
     * 从底部弹出一个对话框
     */
    fun btnShowBottomDialog(view: View){
        val dialogFragment = SampleBottomDialogFragment()
        dialogFragment.show(fragment.get()?.fragmentManager, "SampleDialog")
    }
}



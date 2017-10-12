package com.linxiao.framework.dialog

import android.app.Dialog
import android.content.res.Resources
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.util.DisplayMetrics
import android.view.*

import com.linxiao.framework.R

/**
 * 从底部显示的全屏Dialog基类
 * Created by LinXiao on 2016-12-12.
 */
abstract class BaseBottomDialogFragment<out T:ViewDataBinding> : BaseDialogFragment() {
    private var mDialogHeight = 0
    private var mThemeRes = R.style.FrameworkBottomDialogStyle
    val binding : T by lazy { DataBindingUtil.inflate<T>(LayoutInflater.from(context),onCreatRootView(),null,false) }

    /**
     * return fragment content view layout id
     */
    @LayoutRes
    protected abstract fun onCreatRootView(): Int

    /**
     * 在这里配置Dialog的各项属性和自定义的ContentView
     */
    protected abstract fun onInitView(dialog: Dialog, contentView: View)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mDialog = Dialog(context, mThemeRes)
        mDialog.setContentView(binding.root)
        onInitView(mDialog, binding.root)
        return mDialog
    }

    override fun onStart() {
        super.onStart()
        val win = dialog.window ?: return
        win.decorView.setPadding(0, 0, 0, 0)
        win.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL) //可设置dialog的位置
        val resources = context.resources
        val dm = resources.displayMetrics

        val params = win.attributes
        if (mDialogHeight > 0) {
            win.setLayout(dm.widthPixels, mDialogHeight)
        } else {
            win.setLayout(dm.widthPixels, params.height)
        }
    }

    /**
     * 设置底部Dialog高度
     */
    fun setDialogHeight(height: Int) {
        mDialogHeight = height
    }

    /**
     * 如果不需要使用框架默认样式，可以在这里自定义样式
     */
    fun setCustomStyle(@StyleRes styleRes: Int) {
        mThemeRes = styleRes
    }

}

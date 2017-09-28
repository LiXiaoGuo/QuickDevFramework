package com.linxiao.framework.fragment

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.linxiao.framework.log.Logger

import io.reactivex.disposables.CompositeDisposable

/**
 * base Fragment of entire project
 *
 * template for Fragments in the project, used to define common methods
 */
abstract class BaseFragment : Fragment() {
    protected var TAG: String=""

    private var rootView: View? = null

    private val mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TAG = this.javaClass.simpleName
        //        this.setRetainInstance(true);
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (rootView == null) {
            onCreateContentView(inflater, container, savedInstanceState)
        }
        return rootView
    }

    /**
     * used to add data binding in mvvm.
     *
     * subscribe to the data source provided in ViewModel here
     */
    protected fun onCreateDataBinding() {
        //add data binding
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mCompositeDisposable.clear()
    }

    /**
     * set the content view of this fragment by layout resource id
     *
     * if the content view has been set before, this method will not work again
     * @param resId
     * * layout resource id of content view
     * *
     * @param container
     * * parent ViewGroup of content view, get it in
     * * [.onCreateContentView]
     * *
     */
    protected fun setContentView(@LayoutRes resId: Int, container: ViewGroup) {
        if (rootView != null) {
            Logger.w(TAG, "contentView has set already")
            return
        }
        rootView = LayoutInflater.from(activity).inflate(resId, container, false)
    }

    /**
     * set the content view of this fragment by layout resource id
     *
     * if the content view has been set before, this method will not work again

     * @param contentView content view of this fragment
     * *
     */
    protected val contentView: View
        get() = rootView!!

    /**
     * execute on method onCreateView(), put your code here which you want to do in onCreateView()<br></br>
     * **execute [.setContentView] or [.setContentView] to
     * set the root view of this fragment like activity**
     */
    protected abstract fun onCreateContentView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?)


    /**
     * use this method instead of findViewById() to simplify view initialization <br></br>
     * it's not unchecked because of T extends View
     */
    protected fun <T : View> findView(layoutView: View, @IdRes resId: Int): T {
        return layoutView.findViewById(resId) as T
    }

}

package com.linxiao.framework.adapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.linxiao.framework.BR


/**
 * 通用Adapter
 * Created by Extends on 2017/3/23.
 */

open class BaseAdapter<T1,T2:ViewDataBinding>(@LayoutRes layoutId:Int,dataList:ArrayList<T1>,isStandard:Boolean=true) : BaseRecyclerAdapter<T1, BaseAdapter.Holder>() {
    private var layoutId:Int = 0
    private var isStandard : Boolean = true
    private var if_onBind : OnBind<T1, T2>?=null
    private var mPresenter: Presenter? = null

    override fun onCreate(parent: ViewGroup?, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent?.context).inflate(layoutId, parent, false))
    }

    override fun onBind(viewHolder: Holder, realPosition: Int, data: T1) {
        if(isStandard){
            (viewHolder.getBinding() as T2).apply {
                setVariable(BR.item,data)
                setVariable(BR.presenter, getPresenter())
            }
        }
        if_onBind?.onBind(viewHolder.getBinding() as T2,realPosition,data)
        //不加这一行，刷新时会闪烁
        //当变量的值更新的时候，binding 对象将在下个更新周期中更新。这样就会有一点时间间隔，
        // 如果你像立刻更新，则可以使用 executePendingBindings 函数。
        viewHolder.getBinding().executePendingBindings()
    }

    fun setOnBind(if_onBind: OnBind<T1, T2>){
        this.if_onBind = if_onBind
    }

    fun onBind(l:(itemBingding: T2, position: Int, data: T1)->Unit){
        setOnBind(object : OnBind<T1, T2> {
            override fun onBind(itemBingding: T2, position: Int, data: T1) {
                l(itemBingding,position,data)
            }
        })
    }

    fun setPresenter(presenter: Presenter) {
        mPresenter = presenter
    }

    protected fun getPresenter(): Presenter? {
        return mPresenter
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view){
        private val b: ViewDataBinding by lazy {
            DataBindingUtil.bind<ViewDataBinding>(itemView)
        }

        fun getBinding(): ViewDataBinding {
            return b
        }
    }

//    class Holder(view: View) : RecyclerView.ViewHolder(view)

    init {
        this.layoutId = layoutId
        this.isStandard = isStandard
        setDatas(dataList)
    }

    interface OnBind<in T1,T2>{
        fun onBind(itemBingding:T2,position:Int,data:T1)
    }

    interface Presenter
}

package com.linxiao.framework.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 *委托扩展
 * Created by Extends on 2017/3/17.
 */
/**
 * 使用委托，可以保存基本类型到sp
 */
class Preference<T>(val context: Context, val name: String, val default: T) : ReadWriteProperty<Any?, T> {
    val prefs by lazy {
        context.getSharedPreferences("kotlinDefault", Context.MODE_PRIVATE)
    }
    override fun getValue(thisRef: Any?,property: KProperty<*>) :T{
        return findPreference(name,default)
    }
    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }
    private fun <T> findPreference(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name, default)
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
//            is List<*> -> getList(name,default)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }

        res as T
    }

    private fun <U> putPreference(name: String, value: U)= with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
//            is List<*> -> putList(name,value)
            else -> throw IllegalArgumentException("This type can be saved into Preferences")
        }.apply()
    }
}

/**
 * 返回颜色
 */
fun Context.color(id:Int):Int = resources.getColor(id)

/**
 * 获取RadioGroup中，选中状态的button是第几个
 * 如果没有则返回-1
 */
fun RadioGroup.getCheckOrder():Int=(0..childCount).firstOrNull { (getChildAt(it) as RadioButton).isChecked }?: -1

/**
 * 给ImageView设置图片
 */
fun ImageView.setImageUrl(url:String?){
//    Util.showImg(url,this)
    //TODO:待完成
}

/**
 * 简化Gson转化
 */
inline fun <reified T> Gson.fromJson(s:String): T = fromJson<T>(s,object: TypeToken<T>(){}.type)

/**
 * 简化Gson转化
 */
inline fun <reified T> Gson.toJson(s: T): String = toJson(s,object: TypeToken<T>(){}.type)

/**
 * 判断字符串是否包含html代码
 * 其实只是简单判断是否有</font>表情
 * 主要用来判断文字是否有变颜色的font代码
 */
fun String.isHtml() = indexOf("</font>") != -1

/**
 * kotlin类调用的error打印方法
 * java直接调用L.je()方法
 * @param msg
 */
fun ke(vararg msg: String?,index:Int=3){
    val targetElement = Thread.currentThread().stackTrace[index]
    val s = msg?.reduce { a, b -> a+"\n"+b } ?:"value is Null"
    Log.e("ke","("+targetElement.fileName+":"+targetElement.lineNumber
            +")\tMethodName:"+targetElement.methodName
            +"\nFileName:"+targetElement.fileName
            +"\nThread："+Thread.currentThread().name
            +"\n"+s)
}

inline fun <reified T:Any> Fragment.intent(): Intent = Intent(activity, T::class.java)

fun SharedPreferences.Editor.putList(name: String,list:List<*>): SharedPreferences.Editor {
    val s = list.map{"\"${it.toString()}\""}.reduce { acc, s -> acc+","+s }
    return putString(name,"[$s]")
}

fun SharedPreferences.getList(name: String,default: List<*>):List<String>{
    val s = getString(name,"")
    return s.split(",").toList()
}

/**
 * 默认的获取FragmentStatePagerAdapter
 */
fun getBaseFragmentStatePagerAdapter(fm: FragmentManager, list:List<Fragment>): FragmentStatePagerAdapter {
    return object: FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            return list[position]
        }
        override fun getCount(): Int {
            return list.size
        }
        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) {
        }
    }
}

/**
 * 通过LayoutId便捷获取ViewDataBinding的方法
 * 主要还是为了在 Fragment 里面写R.layout.xxxxxx,方便点击跳转
 */
 fun < T : ViewDataBinding> Fragment.getDataBinding(@LayoutRes id:Int):T =  DataBindingUtil.inflate<T>(LayoutInflater.from(context),id,null,false)

/**
 * 通过LayoutId便捷获取ViewDataBinding的方法
 * 主要还是为了在 Activity 里面写R.layout.xxxxxx,方便点击跳转
 */
 fun < T : ViewDataBinding> Activity.getDataBinding(@LayoutRes id:Int):T =  DataBindingUtil.inflate<T>(layoutInflater,id,null,false)


object DelegatesExt{
    /**
     * 保存基本类型到SP
     */
    fun <T:Any> preference(context: Context, name:String, default:T)= Preference(context,name,default)
}
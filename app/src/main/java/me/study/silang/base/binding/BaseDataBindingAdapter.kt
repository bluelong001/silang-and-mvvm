package me.study.silang.base.binding

import android.text.TextUtils
import android.view.View
import androidx.databinding.BindingAdapter
import me.study.silang.component.HeadIconView
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import javax.xml.datatype.DatatypeConstants.SECONDS



object BaseDataBindingAdapter {
    @BindingAdapter("imgUrl")
    @JvmStatic
    fun setImgUrl(view: HeadIconView, url: String?) {
        if (!TextUtils.isEmpty(url)) view.setImageURL(url)
    }

//    var lastClickTime: Long = 0L
//    @BindingAdapter("android:onClick")
//    @JvmStatic
//    fun onClick(view: View, listener: View.OnClickListener?) {
//        if (System.currentTimeMillis() - lastClickTime <= 2000) {
//            lastClickTime = System.currentTimeMillis()
//            listener?.onClick(view)
//        }
//    }
}
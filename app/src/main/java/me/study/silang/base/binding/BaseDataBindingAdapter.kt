package me.study.silang.base.binding

import android.text.TextUtils
import android.view.View
import androidx.databinding.BindingAdapter
import me.study.silang.component.HeadIconView
import me.study.silang.component.InternetImageView
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import javax.xml.datatype.DatatypeConstants.SECONDS


object BaseDataBindingAdapter {
    @BindingAdapter("imgUrl")
    @JvmStatic
    fun setImgUrl(view: InternetImageView, url: String?) {
        if (!TextUtils.isEmpty(url)) view.setImageURL(url)
    }

    @BindingAdapter("adminVisibility", "openStatus")
    @JvmStatic
    fun adminVisibility(view: View, role: Int, visibility: Boolean?) {
        when (role) {
            1 -> view.visibility = View.INVISIBLE
            2 -> {
                if (visibility != null)
                    if (visibility)
                        view.visibility = View.GONE
                    else view.visibility = View.VISIBLE
            }
        }

    }

    @BindingAdapter("openStatus")
    @JvmStatic
    fun noAdminVisibility(view: View, visibility: Boolean?) {
        if (visibility != null) {
            if (visibility)
                view.visibility = View.GONE
            else view.visibility = View.VISIBLE

        }
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
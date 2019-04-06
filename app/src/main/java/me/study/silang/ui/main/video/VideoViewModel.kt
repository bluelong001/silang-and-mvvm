package me.study.silang.ui.main.video

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.core.net.toFile
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer.util.UriUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.http.RetrofitCallback
import me.study.silang.http.RetrofitManager
import me.study.silang.repository.MainRepository
import me.study.silang.utils.PathUtils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class VideoViewModel(val context: Context) : BaseViewModel() {
    val addStatus = ObservableField<Boolean>()
    val videoUri = ObservableField<Uri>()
    val label = ObservableField<String>()

    private var service:MainRepository = (RetrofitManager.getInstance<MainRepository>(context, MainRepository::class.java).service as MainRepository?)!!

    fun insertVideo() {
        val out = File(com.zhihu.matisse.internal.utils.PathUtils.getPath(context,videoUri.get()!!))
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), out)
        val file = MultipartBody.Part.createFormData("file", out.name, requestFile)
        val map:HashMap<String,String> = HashMap()
        map["type"] = "mp4"
        val files = ArrayList<MultipartBody.Part>()
        files.add(file)
        service.upload(map, files)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(model: Any?) {
                    addStatus.set(false)
                    Toast.makeText(context,"success",Toast.LENGTH_SHORT).show()

                }

                override fun onFailure(msg: String?) {
                    Log.d(TAG,msg)
                    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
                }
            })


    }

    init {
        addStatus.set(false)
    }

    companion object {
        const val TAG="VideoViewModel"
    }

}

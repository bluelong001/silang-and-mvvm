package me.study.silang.ui.main.video

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableList
import androidx.databinding.ObservableMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.bean.Page
import me.study.silang.bean.Param
import me.study.silang.entity.Video
import me.study.silang.http.RetrofitCallback
import me.study.silang.http.RetrofitManager
import me.study.silang.repository.VideoRepository
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class VideoViewModel(val context: Context) : BaseViewModel() {
    val addStatus = ObservableField<Boolean>()
    val videoUri = ObservableField<Uri>()
    val label = ObservableField<String>()
    val page = ObservableField<Page>()

    lateinit var videoAdapter: VideoAdapter

    var service: VideoRepository =
        (RetrofitManager.getInstance<VideoRepository>(
            context,
            VideoRepository::class.java
        ).service as VideoRepository?)!!

    fun initVideo() {
        service.list(Param().page(0).pageSize(10))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(model: Any?) {
                    page.set(Page())
                    videoAdapter.init(model as List<VideoModel>)
                    Page(page.get()!!.page + page.get()!!.pageSize, page.get()!!.pageSize + 10)
                        .also { p -> page.set(p) }
                }

                override fun onFailure(msg: String?) {

                }
            })
    }

    fun selectVideo() {
        service.list(Param().page(page.get()!!.page).pageSize(page.get()!!.pageSize))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(model: Any?) {

                    videoAdapter.addAll(model as List<VideoModel>)
                    Page(page.get()!!.page + page.get()!!.pageSize, page.get()!!.pageSize + 10)
                        .also { p -> page.set(p) }
                }

                override fun onFailure(msg: String?) {

                }
            })
    }

    fun insertVideo() {
        val out = File(com.zhihu.matisse.internal.utils.PathUtils.getPath(context, videoUri.get()!!))
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), out)
        val file = MultipartBody.Part.createFormData("file", out.name, requestFile)
        val map: HashMap<String, String> = HashMap()
        map["type"] = "mp4"
        val files = ArrayList<MultipartBody.Part>()
        files.add(file)
        service.upload(map, files)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(model: Any?) {
                    Video().also { video ->
                        run {
                            video.title = label.get()
                            video.fileId = Integer.parseInt(model as String)
                            service.add(Param().putObj(video))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : RetrofitCallback<Any>() {
                                    override fun onSuccess(model: Any?) {
                                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                                        addStatus.set(false)
                                        initVideo()
                                    }

                                    override fun onFailure(msg: String?) {
                                        Log.d(TAG, msg)
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                    }
                                })
                        }
                    }

                }

                override fun onFailure(msg: String?) {

                }
            })

    }

    init {
        addStatus.set(false)

    }

    companion object {
        const val TAG = "VideoViewModel"
    }

}

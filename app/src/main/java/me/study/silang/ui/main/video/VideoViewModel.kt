package me.study.silang.ui.main.video

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.bean.Page
import me.study.silang.bean.Param
import me.study.silang.entity.Video
import me.study.silang.http.RetrofitCallback
import me.study.silang.http.RetrofitManager
import me.study.silang.model.VideoModel
import me.study.silang.repository.VideoRepository
import me.study.silang.utils.AnyCallback
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
    val content = ObservableField<String>()
    var page: Page = Page()
    var isTotal: Boolean = false

    lateinit var videoListAdapter: VideoListAdapter

    var service: VideoRepository =
        (RetrofitManager.getInstance<VideoRepository>(
            context,
            VideoRepository::class.java
        ).service as VideoRepository?)!!

    fun initVideo(callback: AnyCallback?) {
        page = Page()

        service.list(Param().page(page.page).pageSize(page.pageSize))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(model: Any?) {
                    videoListAdapter.items.clear()
                    videoListAdapter.items.addAll(model as List<VideoModel>)
                    callback?.callback()
                }

                override fun onFailure(msg: String?) {
                    page.back()
                }
            })
        page.next()
    }

    fun selectVideo(callback: AnyCallback?) {

        service.list(Param().page(page.page).pageSize(page.pageSize))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(model: Any?) {
                    videoListAdapter.items.addAll(model as List<VideoModel>)
                    if (model.size <= 0) isTotal = true
                    callback?.callback()
                }

                override fun onFailure(msg: String?) {
                    page.back()
                }
            })
        page.next()
    }

    fun insertVideo(callback: AnyCallback?) {
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
                            video.content = content.get()
                            service.add(Param().putObj(video))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(object : RetrofitCallback<Any>() {
                                    override fun onSuccess(model: Any?) {
                                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                                        addStatus.set(false)
                                        initVideo(null)
                                        callback?.callback()
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

    fun query(queryField: String) {

        ArrayList<VideoModel>().also { arr ->
            run {
                videoListAdapter.items.forEach { model ->
                    if (model.title.contains(queryField))
                        arr.add(model)
                }
                videoListAdapter.items.clear()
                videoListAdapter.items.addAll(arr)
            }
//        videoListAdapter.items.removeIf { videomodel-> videomodel.title.contentEquals(queryField)}

        }
    }

    init {
        addStatus.set(false)
    }

    companion object {
        const val TAG = "VideoViewModel"
    }

}

package me.study.silang.ui.main.video

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.google.gson.Gson
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.android.synthetic.main.activity_video_detail.*
import me.study.silang.base.activity.BaseActivity
import me.study.silang.databinding.ActivityVideoDetailBinding
import me.study.silang.model.VideoModel
import java.util.*
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.internal.FlowLayout
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_post_new.*
import me.study.silang.BuildConfig
import me.study.silang.R
import me.study.silang.component.MyGridNoScrollLayoutManager
import me.study.silang.component.MyLinearNoScrollLayoutManager
import me.study.silang.room.RoomHelper
import me.study.silang.room.VideoCache
import me.study.silang.utils.DownloadAsyncTask
import me.study.silang.utils.MediaUtils
import java.io.File
import kotlin.collections.ArrayList


class VideoDetailActivity : BaseActivity<ActivityVideoDetailBinding>() {
    override val layoutId = me.study.silang.R.layout.activity_video_detail
    private var orientationUtils: OrientationUtils? = null
    private var isPlay: Boolean = false
    private var isPause: Boolean = false
    lateinit var model: VideoModel

    companion object {
        fun launch(videoModel: VideoModel, context: Context) {
            var videoInfo: VideoModel = videoModel
            Intent(context, VideoDetailActivity::class.java).also { intent ->
                intent.putExtra("data", Gson().toJson(videoInfo))
                context.startActivity(intent)
            }
        }

    }

    lateinit var imgListAdapter: VideoImgListAdapter
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_video_opt_nav, menu)
//        return super.onCreateOptionsMenu(menu)
//    }

    fun download() {
        val fileName = UUID.randomUUID().toString().replace("-", "") + ".mp4"
//        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//        val request = DownloadManager.Request(Uri.parse(model.fileUrl))
//        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//        request.setTitle(fileName)
//        request.setMimeType("application/vnd.android.package-archive")
//        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName)
//        if (file.exists()) {
//            file.delete()
//        }
//        request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, fileName)
//        val downloadId = downloadManager.enqueue(request)
//        //文件下载完成会发送完成广播，可注册广播进行监听
//        val intentFilter = IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
//        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED)
//        intentFilter.addAction(DownloadManager.ACTION_VIEW_DOWNLOADS)
//        mDownloadBroadcast = DownloadBroadcast(file)
//        registerReceiver(mDownloadBroadcast, intentFilter)

        DownloadAsyncTask(
            this,
            ProgressDialog(this),
            fileName,
            model.fileUrl,
            DownloadAsyncTask.DownloadSuccess { url ->
                run {
                    VideoCache().also { videoCache ->
                        videoCache.userId = userId
                        videoCache.title = model.title
                        videoCache.content = model.content
                        videoCache.localUrl = url
                        RoomHelper.getInstance(this@VideoDetailActivity)!!.download(videoCache)
                    }

                }
            }).also { task -> task.execute() }


    }

    var userId: Int = 0
    @SuppressLint("WrongConstant")
    override fun initView() {
        model = Gson().fromJson(intent.getStringExtra("data"), VideoModel::class.java)
        userId = getSharedPreferences("silang_user_info", Context.MODE_PRIVATE).getInt("userId", 0)


        imgListAdapter = VideoImgListAdapter(this)
        //初始化图片墙
        var manager = MyLinearNoScrollLayoutManager(this)
//        var manager = FlexboxLayoutManager(this)
//        manager.flexDirection= FlexDirection.COLUMN
//        manager.justifyContent=JustifyContent.FLEX_END
        imgList.layoutManager = manager
        imgList.adapter = imgListAdapter
        var type = object : TypeToken<ArrayList<String>>() {}.type
        var list = Gson().fromJson(model.imgList, type) as ArrayList<String>?
        if (null != list) imgListAdapter.items.addAll(list)


        //增加封面
        val imageView = ImageView(this)
        imageView.setImageBitmap(
            MediaUtils.createVideoThumbnail(
                model.fileUrl!!,
                MediaStore.Images.Thumbnails.MINI_KIND
            )
        )

        //增加title
        detailPlayer.titleTextView.visibility = View.GONE
        detailPlayer.backButton.visibility = View.GONE

        orientationUtils = OrientationUtils(this, detailPlayer)
//初始化不打开外部的旋转
        orientationUtils!!.isEnable = false

        val gsyVideoOption = GSYVideoOptionBuilder()
        gsyVideoOption.setThumbImageView(imageView)
            .setIsTouchWiget(true)
            .setLooping(true)
            .setRotateViewAuto(false)
            .setLockLand(false)
            .setAutoFullWithSize(true)
            .setShowFullAnimation(false)
            .setNeedLockFull(true)
            .setUrl(model.fileUrl)
            .setCacheWithPlay(false)
            .setVideoTitle(model.title)
            .setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String?, vararg objects: Any) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    orientationUtils!!.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String?, vararg objects: Any) {
                    super.onQuitFullscreen(url, *objects)
                    Debuger.printfError("***** onQuitFullscreen **** " + objects[0])//title
                    Debuger.printfError("***** onQuitFullscreen **** " + objects[1])//当前非全屏player
                    if (orientationUtils != null) {
                        orientationUtils!!.backToProtVideo()
                    }
                }
            }).setLockClickListener { _, lock ->
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils!!.isEnable = !lock
                }
            }.build(detailPlayer)

        detailPlayer.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils!!.resolveByClick()

            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            detailPlayer.startWindowFullscreen(this@VideoDetailActivity, true, true)
        }

    }

    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils!!.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }


    override fun onPause() {
        detailPlayer.currentPlayer.onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        detailPlayer.currentPlayer.onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            detailPlayer.currentPlayer.release()
        }
        if (orientationUtils != null)
            orientationUtils!!.releaseListener()

    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }
}
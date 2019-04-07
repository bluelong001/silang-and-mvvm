package me.study.silang.ui.main.video

import android.content.Intent
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_video_detail.*
import me.study.silang.R
import me.study.silang.base.activity.BaseActivity
import me.study.silang.databinding.ActivityVideoDetailBinding
import com.shuyu.gsyvideoplayer.listener.LockClickListener
import com.shuyu.gsyvideoplayer.utils.Debuger
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import com.shuyu.gsyvideoplayer.GSYVideoManager
import android.view.View
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.widget.ImageView
import java.util.*
import android.content.res.Configuration

class VideoDetailActivity : BaseActivity<ActivityVideoDetailBinding>() {
    override val layoutId = me.study.silang.R.layout.activity_video_detail
    private lateinit var orientationUtils: OrientationUtils
    private var isPlay: Boolean = false
    private var isPause: Boolean = false
    lateinit var model: VideoModel

    fun getImg(url: String): Bitmap {
        val media = MediaMetadataRetriever()
        media.setDataSource(url, Hashtable<String, String>())
        return media.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
    }

    override fun initView() {
        model = Gson().fromJson(intent.getStringExtra("data"), VideoModel::class.java)

        //增加封面
        val imageView = ImageView(this)
        imageView.setImageBitmap(getImg(model.fileUrl!!))

        //增加title
        detailPlayer.titleTextView.visibility = View.GONE
        detailPlayer.backButton.visibility = View.GONE

        orientationUtils= OrientationUtils(this, detailPlayer)
//初始化不打开外部的旋转
        orientationUtils.isEnable = false

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
                    orientationUtils.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String?, vararg objects: Any) {
                    super.onQuitFullscreen(url, *objects)
                    Debuger.printfError("***** onQuitFullscreen **** " + objects[0])//title
                    Debuger.printfError("***** onQuitFullscreen **** " + objects[1])//当前非全屏player
                    if (orientationUtils != null) {
                        orientationUtils.backToProtVideo()
                    }
                }
            }).setLockClickListener { _, lock ->
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.isEnable = !lock
                }
            }.build(detailPlayer)

        detailPlayer.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils.resolveByClick()

            //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
            detailPlayer.startWindowFullscreen(this@VideoDetailActivity, true, true)
        }

    }

    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }
        super.onBackPressed()
    }


    override fun onPause() {
        detailPlayer.getCurrentPlayer().onVideoPause()
        super.onPause()
        isPause = true
    }

    override fun onResume() {
        detailPlayer.getCurrentPlayer().onVideoResume(false)
        super.onResume()
        isPause = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isPlay) {
            detailPlayer.getCurrentPlayer().release()
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true)
        }
    }
}
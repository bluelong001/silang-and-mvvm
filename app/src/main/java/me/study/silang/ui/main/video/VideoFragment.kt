package me.study.silang.ui.main.video

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import com.google.gson.Gson
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.utils.PathUtils
import kotlinx.android.synthetic.main.fragment_video.*
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.component.Glide4Engine
import me.study.silang.databinding.FragmentVideoBinding
import me.study.silang.model.VideoModel
import me.study.silang.utils.AnyCallback
import java.io.File
import java.util.*


class VideoFragment : BaseFragment<FragmentVideoBinding>(), VideoAdapter.Callback {
    override fun click(v: View) {
        var videoInfo: VideoModel = v.tag as VideoModel
        Intent(context, VideoDetailActivity::class.java).also { intent ->
            intent.putExtra("data", Gson().toJson(videoInfo))
            startActivity(intent)
        }
    }

    companion object {
        private const val TAG = "VideoFragment"
        const val REQUEST_CODE_CHOOSE: Int = 1
        const val REQUEST_CODE_MAKE: Int = 2
    }

    override val layoutId: Int = R.layout.fragment_video

    lateinit var vm: VideoViewModel

    override fun initView() {
        vm = VideoViewModel(mContext)
        vm.videoAdapter = VideoAdapter(mContext, this)
        vm.initVideo()
        gv_video.adapter = vm.videoAdapter
        // 监听listview滚到最底部
        gv_video.setOnScrollListener(object : AbsListView.OnScrollListener {
            override fun onScroll(
                view: AbsListView?,
                firstVisibleItem: Int,
                visibleItemCount: Int,
                totalItemCount: Int
            ) {

            }

            override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
                when (scrollState) {
                    // 当不滚动时
                    AbsListView.OnScrollListener.SCROLL_STATE_IDLE ->
                        // 判断滚动到底部
                        if (view!!.lastVisiblePosition === view!!.count - 1&&!vm.isTotal) {
                            vm.selectVideo()
                        }
                }
            }
        })
    }


    fun upload(){
        vm.insertVideo(object : AnyCallback() {
            override fun callback() {
                findVideo.setImageBitmap(resources.getDrawable(R.drawable.ic_add_black_48dp).toBitmap())
                vm.videoUri.set(null)
                vm.label.set(null)
            }

        })
    }
    fun closeAddDialog() {
        vm.addStatus.set(false)
    }

    fun showAddDialog() {
        vm.addStatus.set(true)
    }

    fun addVideo() {
        AlertDialog.Builder(this.activity!!)
            .setPositiveButton("make") { _, _ -> makeVideo() }
            .setNegativeButton("find") { _, _ -> findVideo() }
            .show()
    }

    private fun findVideo() {
        Matisse.from(this@VideoFragment)
            .choose(MimeType.ofVideo())
            .countable(true)
            .maxSelectable(1)
            .showSingleMediaType(true)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(Glide4Engine())
            .forResult(REQUEST_CODE_CHOOSE)
    }

    private fun makeVideo() {
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { takeVideoIntent ->
            run {
                takeVideoIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//                    .putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20)
//                        .putExtra(MediaStore.EXTRA_OUTPUT,getOutputMediaFileUri(MEDIA_TYPE_VIDEO))
                takeVideoIntent.resolveActivity(this.activity!!.packageManager)?.also {
                    startActivityForResult(takeVideoIntent, REQUEST_CODE_MAKE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if(requestCode== RESULT_OK) {
            if (requestCode == REQUEST_CODE_CHOOSE) {
                vm.videoUri.set(Matisse.obtainResult(intent!!)[0])
            } else if (requestCode == REQUEST_CODE_MAKE) {
                vm.videoUri.set(intent!!.data)
            }
            val media = MediaMetadataRetriever()
            val out = File(PathUtils.getPath(context, vm.videoUri.get()!!))
            media.setDataSource(out.path)
            findVideo.setImageBitmap(media.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC))
        }
        Log.d(TAG, vm.videoUri.toString())
    }
}

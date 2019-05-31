package me.study.silang.ui.main.video

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AbsListView
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.activity_video_detail.*
import kotlinx.android.synthetic.main.fragment_bbs.*
import me.study.silang.ui.main.UserViewModel
import me.study.silang.ui.main.bbs.PostListAdapter


class VideoFragment : BaseFragment<FragmentVideoBinding>(){


    companion object {
        private const val TAG = "VideoFragment"
        const val REQUEST_CODE_CHOOSE: Int = 1
        const val REQUEST_CODE_MAKE: Int = 2

    }

    override val layoutId: Int = R.layout.fragment_video

    lateinit var vm: VideoViewModel
    lateinit var userViewModel: UserViewModel

    @SuppressLint("WrongConstant")
    override fun initView() {
        vm = VideoViewModel(mContext)
        vm.videoListAdapter = VideoListAdapter(mContext)
        vm.initVideo(null)
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)



        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
// 绑定布局管理器
        gv_video.layoutManager = layoutManager
        gv_video.adapter = vm.videoListAdapter
        // 监听listview滚到最底部
        gv_video.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                vm.videoListAdapter = VideoListAdapter(mContext)
                vm.initVideo(object : AnyCallback() {
                    override fun callback() {
                        gv_video.adapter = vm.videoListAdapter
                        gv_video.refreshComplete()

                    }
                })
            }

            override fun onLoadMore() {
                vm.selectVideo(object : AnyCallback() {
                    override fun callback() {
                        gv_video.loadMoreComplete()
                    }
                })
            }
        })
        sv_video.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                vm.query(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }


    fun upload() {
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
        if (resultCode == RESULT_OK) {
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

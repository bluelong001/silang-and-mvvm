package me.study.silang.ui.main.video

import android.app.Activity.RESULT_OK
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.databinding.FragmentVideoBinding
import android.content.pm.ActivityInfo
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import android.content.Intent
import android.provider.MediaStore
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import android.util.Log
import me.study.silang.component.Glide4Engine


class VideoFragment : BaseFragment<FragmentVideoBinding>() {
    companion object {
        private const val TAG = "VideoFragment"
        const val REQUEST_CODE_CHOOSE: Int = 1
        const val REQUEST_CODE_MAKE: Int = 1
    }

    override val layoutId: Int = R.layout.fragment_video

    override val vm: VideoViewModel = VideoViewModel()

    override fun initView() {

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
                takeVideoIntent.addCategory(Intent.CATEGORY_DEFAULT)
                    .putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20)
                takeVideoIntent.resolveActivity(this.activity!!.packageManager)?.also {
                    startActivityForResult(takeVideoIntent, REQUEST_CODE_MAKE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            vm.videoUri.set(Matisse.obtainResult(intent!!)[0])
        } else if (requestCode == REQUEST_CODE_MAKE && resultCode == RESULT_OK) {
            vm.videoUri.set(intent!!.data)
        }
        Log.d(TAG, vm.videoUri.toString())
    }

    fun upload() {

    }
}

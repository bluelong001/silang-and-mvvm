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
import android.os.Environment
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
import android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO
import androidx.appcompat.app.AlertDialog
import android.util.Log
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.fragment_video.*
import me.study.silang.component.Glide4Engine
import java.io.File
import java.nio.file.Files.exists
import java.text.SimpleDateFormat
import java.util.*


class VideoFragment : BaseFragment<FragmentVideoBinding>() {
    companion object {
        private const val TAG = "VideoFragment"
        const val REQUEST_CODE_CHOOSE: Int = 1
        const val REQUEST_CODE_MAKE: Int = 2
    }

    override val layoutId: Int = R.layout.fragment_video

    lateinit var vm: VideoViewModel

    override fun initView() {
        vm = VideoViewModel(mContext)
        vm.selectVideo()
        gv_video.adapter=vm.videoAdapter
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
                    takeVideoIntent .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        .putExtra(MediaStore.EXTRA_DURATION_LIMIT, 20)
//                        .putExtra(MediaStore.EXTRA_OUTPUT,getOutputMediaFileUri(MEDIA_TYPE_VIDEO))
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
//
//    /** Create a file Uri for saving an image or video */
//    private fun getOutputMediaFileUri(type: Int): Uri {
//        return FileProvider.getUriForFile(context!!, "me.study.silang.fileprovider",getOutputMediaFile(type)!!)
//    }
//
//    /** Create a File for saving an image or video */
//    private fun getOutputMediaFile(type: Int): File? {
//        // To be safe, you should check that the SDCard is mounted
//        // using Environment.getExternalStorageState() before doing this.
//
//        val mediaStorageDir = File(
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path +File.separator+"Camera"
//        )
//        // This location works best if you want the created images to be shared
//        // between applications and persist after your app has been uninstalled.
//
//        // Create the storage directory if it does not exist
//        mediaStorageDir.apply {
//            if (!exists()) {
//                if (!mkdirs()) {
//                    Log.d("MyCameraApp", "failed to create directory")
//                    return null
//                }
//            }
//        }
//
//        // Create a media file name
//        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
//        return when (type) {
//            MEDIA_TYPE_VIDEO -> {
//                File("${mediaStorageDir.path}${File.separator}VID_$timeStamp.mp4")
//            }
//            else -> null
//        }
//    }
}

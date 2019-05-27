package me.study.silang.ui.main.me.cache

import android.annotation.SuppressLint
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.activity_me_cache.*
import kotlinx.android.synthetic.main.fragment_video.*
import me.study.silang.R
import me.study.silang.base.activity.BaseActivity
import me.study.silang.databinding.ActivityMeSetBinding
import me.study.silang.ui.main.video.VideoListAdapter
import me.study.silang.utils.AnyCallback


class MeCacheVideoActivity : BaseActivity<ActivityMeSetBinding>() {
    override val layoutId: Int = R.layout.activity_me_cache

    lateinit var vm:MeCacheVideoViewModel


    @SuppressLint("WrongConstant")
    override fun initView() {
        vm= MeCacheVideoViewModel(this,intent.getIntExtra("userId",0))

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
// 绑定布局管理器
        list_video_cache.layoutManager = layoutManager
        list_video_cache.adapter = vm.videoCacheAdapter
        vm.initVideoCache()


    }

    fun back(){
        finish()
    }
    companion object {

        fun launch(activity: androidx.fragment.app.FragmentActivity,userId:Int) =
            activity.apply {
                Intent(this, MeCacheVideoActivity::class.java).also {
                 intent ->
                    intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    intent.putExtra("userId",userId)
                    startActivity(intent)
                }
            }
    }

}

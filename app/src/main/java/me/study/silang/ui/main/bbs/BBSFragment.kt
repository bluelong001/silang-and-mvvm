package me.study.silang.ui.main.bbs

import android.app.Activity
import android.content.Intent
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_bbs.*
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.databinding.FragmentBbsBinding


class BBSFragment : BaseFragment<FragmentBbsBinding>() ,PostListAdapter.Callback{

    lateinit var vm: BBSViewModel
    override val layoutId: Int = R.layout.fragment_bbs

    override fun click(v: View) {
        var postInfo: PostModel = v.tag as PostModel
        Intent(context, PostDetailActivity::class.java).also { intent ->
            intent.putExtra("data", Gson().toJson(postInfo))
            startActivity(intent)
        }
    }

    override fun initView() {
        vm= BBSViewModel(mContext)
        vm.postListAdapter = PostListAdapter(mContext,this)
        rvPost.adapter = vm.postListAdapter
    }

    fun newPost() {
        Intent(activity, PostNewActivity::class.java).also { intent ->
            startActivityForResult(intent, REQUEST_CODE_NEW_POST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_CODE_NEW_POST && resultCode == Activity.RESULT_OK) {
        }

    }

    companion object {
        const val REQUEST_CODE_NEW_POST = 100
    }
}

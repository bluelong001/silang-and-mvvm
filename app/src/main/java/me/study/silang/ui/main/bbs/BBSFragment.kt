package me.study.silang.ui.main.bbs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.jcodecraeer.xrecyclerview.XRecyclerView
import kotlinx.android.synthetic.main.fragment_bbs.*
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.databinding.FragmentBbsBinding
import me.study.silang.model.PostModel
import me.study.silang.ui.main.UserViewModel
import me.study.silang.utils.AnyCallback


class BBSFragment : BaseFragment<FragmentBbsBinding>(), PostListAdapter.Callback {

    lateinit var vm: BBSViewModel
    override val layoutId: Int = R.layout.fragment_bbs
    lateinit var userViewModel:UserViewModel
    override fun click(v: View) {
        var postInfo: PostModel = v.tag as PostModel
        Intent(context, PostDetailActivity::class.java).also { intent ->
            intent.putExtra("data", Gson().toJson(postInfo))
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }
    }

    @SuppressLint("WrongConstant")
    override fun initView() {
        vm = BBSViewModel(mContext)
        vm.postListAdapter = PostListAdapter(mContext, this)
        vm.initPostList(null)
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        rvPost.layoutManager = layoutManager
        rvPost.adapter = vm.postListAdapter
        rvPost.setLoadingListener(object : XRecyclerView.LoadingListener {
            override fun onRefresh() {
                vm.postListAdapter = PostListAdapter(mContext, this@BBSFragment)
                vm.reset(object : AnyCallback() {
                    override fun callback() {
                        rvPost.adapter = vm.postListAdapter
                        rvPost.refreshComplete()

                    }
                })
            }

            override fun onLoadMore() {
                vm.listMorePostList(object : AnyCallback() {
                    override fun callback() {
                        rvPost.loadMoreComplete()
                    }
                })
            }
        })
    }

    fun newPost() {
        Intent(activity, PostNewActivity::class.java).also { intent ->
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivityForResult(intent, REQUEST_CODE_NEW_POST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_CODE_NEW_POST && resultCode == Activity.RESULT_OK) {
            vm.initPostList(null)
        }

    }

    companion object {
        const val REQUEST_CODE_NEW_POST = 100
    }
}

package me.study.silang.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import me.study.silang.BR
import me.study.silang.base.videomodel.BaseViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.support.closestKodein
import org.kodein.di.generic.kcontext

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    private var mRootView: View? = null

    protected lateinit var binding: B

    lateinit var mContext: Context
    abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        mRootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        mContext = context!!
        return mRootView!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding(view)
        initView()
    }

    open fun initView() {

    }

    private fun initBinding(rootView: View) {
        binding = DataBindingUtil.bind(rootView)!!
        with(binding) {
            setVariable(BR.fragment, this@BaseFragment)
            lifecycleOwner = this@BaseFragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRootView = null
        binding.unbind()
    }
}

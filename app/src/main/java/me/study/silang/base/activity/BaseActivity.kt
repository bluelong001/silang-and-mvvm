package me.study.silang.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B : ViewDataBinding> :   InjectionActivity() {

    protected lateinit var binding: B

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initView()
    }

    open fun initView() {

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
//        with(binding) {
//            setVariable(BR.activity, this@BaseActivity)
//            setLifecycleOwner(this@BaseActivity)
//        }
    }
}
package me.study.silang.ui.login

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import me.study.silang.R
import me.study.silang.ui.login.register.RegisterFragment
import me.study.silang.utils.PermissionUtils

open class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        PermissionUtils.Request(this)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.apply {
            findFragmentByTag(TAG_LOGIN) ?: beginTransaction()
                .add(R.id.fl_login, LoginFragment(), TAG_LOGIN)
                .commitAllowingStateLoss()
        }
    }
    companion object {
        private const val TAG_LOGIN = "LoginFragment"
    }

    /** 处理权限请求结果，若未授权，则继续请求  */
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionUtils.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }


    /** Activity执行结果  */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        PermissionUtils.onActivityResult(this, requestCode, resultCode, data)
    }


}
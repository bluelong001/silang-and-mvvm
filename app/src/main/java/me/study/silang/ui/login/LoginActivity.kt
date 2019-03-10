package me.study.silang.ui.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import me.study.silang.R

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initFragment()
    }

    private fun initFragment() {
        supportFragmentManager.apply {
            findFragmentByTag(TAG) ?: beginTransaction()
                .add(R.id.fl_login, LoginFragment(), TAG)
                .commitAllowingStateLoss()
        }
    }

    companion object {
        private const val TAG = "LoginFragment"
    }
}
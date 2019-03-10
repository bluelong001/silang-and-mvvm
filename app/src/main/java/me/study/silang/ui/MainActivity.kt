package me.study.silang.ui

import android.content.Intent
import androidx.navigation.findNavController
import me.study.silang.R
import me.study.silang.base.activity.BaseActivity
import me.study.silang.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId = R.layout.activity_main

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.navHostFragment).navigateUp()

    companion object {

        fun launch(activity: androidx.fragment.app.FragmentActivity) =
            activity.apply {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }
}
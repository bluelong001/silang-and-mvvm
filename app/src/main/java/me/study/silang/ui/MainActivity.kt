package me.study.silang.ui

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.github.nkzawa.emitter.Emitter
import me.study.silang.R
import me.study.silang.base.activity.BaseActivity
import me.study.silang.databinding.ActivityMainBinding
import me.study.silang.ui.main.UserViewModel
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import me.study.silang.config.BaseIPAdress
import java.net.URISyntaxException
import org.json.JSONObject


class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val layoutId = R.layout.activity_main

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.navHostFragment).navigateUp()

    override fun initView() {
        val userViewModel: UserViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        userViewModel.initService(this)
        userViewModel.initUser(null,this)

    }

    companion object {

        fun launch(activity: androidx.fragment.app.FragmentActivity) =
            activity.apply {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }


}
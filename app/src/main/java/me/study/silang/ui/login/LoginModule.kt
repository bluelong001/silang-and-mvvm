package me.study.silang.ui.login

import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_login.*
import org.kodein.di.Kodein.Module
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val LOGIN_MODULE_TAG = "LOGIN_MODULE_TAG"

const val LOGIN_LIST_FRAGMENT = "LOGIN_LIST_FRAGMENT"

val loginKodeinModule = Module(LOGIN_MODULE_TAG) {



    
}
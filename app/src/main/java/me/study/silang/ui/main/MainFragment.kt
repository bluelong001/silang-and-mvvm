package me.study.silang.ui.main

import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main.*
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.databinding.FragmentMainBinding
import me.study.silang.ui.main.bbs.BBSFragment
import me.study.silang.ui.main.me.MeFragment
import me.study.silang.ui.main.video.VideoFragment
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override val layoutId: Int = R.layout.fragment_main
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(mainKodeinModule)
//        bind<FragmentManager>() with instance(childFragmentManager)
    }
    val viewModel: MainViewModel by instance()

    override fun initView() {
        viewPager.adapter = MainPagerAdapter(
            fragmentManager!!,
            listOf(VideoFragment(), BBSFragment(), MeFragment())
        )
        viewPager.currentItem = 0
        viewPager.offscreenPageLimit = 3
        navigation.setOnNavigationItemSelectedListener(object :
            BottomNavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.nav_video ->
                        viewPager.currentItem = 0
                    R.id.nav_bbs ->
                        viewPager.currentItem = 1
                    R.id.nav_me ->
                        viewPager.currentItem = 2
                }
                return true
            }
        })
    }
}

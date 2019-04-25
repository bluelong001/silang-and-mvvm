package me.study.silang.ui.main

import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main.*
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.databinding.FragmentMainBinding
import me.study.silang.ui.main.bbs.BBSFragment
import me.study.silang.ui.main.me.MeFragment
import me.study.silang.ui.main.message.MessageFragment
import me.study.silang.ui.main.video.VideoFragment

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override val layoutId: Int = R.layout.fragment_main

    val vm:BaseViewModel = MainViewModel()
    override fun initView() {
        viewPager.adapter = MainPagerAdapter(
            fragmentManager!!,
            listOf(VideoFragment(), BBSFragment(),MessageFragment(), MeFragment())
        )
        viewPager.currentItem = 0
        viewPager.offscreenPageLimit = 4
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 ->
                        navigation.selectedItemId = R.id.nav_video
                    1 ->
                        navigation.selectedItemId = R.id.nav_bbs
                    2 ->
                        navigation.selectedItemId = R.id.nav_message
                    3 ->
                        navigation.selectedItemId = R.id.nav_me
                }
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }
            override fun onPageScrollStateChanged(state: Int) {
            }

        })
        navigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_video ->
                    viewPager.currentItem = 0
                R.id.nav_bbs ->
                    viewPager.currentItem = 1
                R.id.nav_message ->
                    viewPager.currentItem = 2
                R.id.nav_me ->
                    viewPager.currentItem = 3
            }
            true
        }
    }
}

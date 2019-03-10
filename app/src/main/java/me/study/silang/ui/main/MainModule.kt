package me.study.silang.ui.main

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_main.*
import me.study.silang.ui.main.bbs.BBSFragment
import me.study.silang.ui.main.me.MeFragment
import me.study.silang.ui.main.video.VideoFragment
import org.kodein.di.Kodein.Module
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

const val MAIN_MODULE_TAG = "MAIN_MODULE_TAG"

const val MAIN_LIST_FRAGMENT = "MAIN_LIST_FRAGMENT"

val mainKodeinModule = Module(MAIN_MODULE_TAG) {

    bind<VideoFragment>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        VideoFragment()
    }

    bind<BBSFragment>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        BBSFragment()
    }

    bind<MeFragment>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        MeFragment()
    }

    bind<MainViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        MainViewModel.instance(context)
    }

    bind<BottomNavigationView>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        (context as MainFragment).navigation
    }

    bind<ViewPager>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        (context as MainFragment).viewPager
    }

    bind<List<Fragment>>(MAIN_LIST_FRAGMENT) with scoped<Fragment>(AndroidLifecycleScope).singleton {
        listOf<Fragment>(instance<VideoFragment>(), instance<BBSFragment>(), instance<MeFragment>())
    }
}
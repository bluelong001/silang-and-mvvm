package me.study.silang.base.fragment

import androidx.fragment.app.Fragment
import me.study.silang.base.fragment.AutoDisposeFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.kcontext

abstract class InjectionFragment : AutoDisposeFragment(), KodeinAware {

    protected val parentKodein by closestKodein()

    override val kodeinContext = kcontext<Fragment>(this)
}
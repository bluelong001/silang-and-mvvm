package me.study.silang.base.activity

import androidx.appcompat.app.AppCompatActivity
import me.study.silang.base.activity.AutoDisposeActivity
import org.kodein.di.Copy
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.android.retainedKodein
import org.kodein.di.generic.kcontext

abstract class InjectionActivity : AutoDisposeActivity(), KodeinAware {

//    protected val parentKodein by closestKodein()
//
//    override val kodeinContext = kcontext<AppCompatActivity>(this)
//
//    override val kodein: Kodein by retainedKodein {
//        extend(parentKodein, copy = Copy.All)
//    }
}
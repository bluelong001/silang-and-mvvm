package me.study.silang.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDexApplication
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidModule
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

open class BaseApplication : MultiDexApplication() {
//    , KodeinAware
//    override val kodein: Kodein = Kodein.lazy {
//        bind<Context>() with singleton { this@BaseApplication }
//        import(androidModule(this@BaseApplication))
//        import(androidXModule(this@BaseApplication))
//
//
//    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

    }


    companion object {
        lateinit var INSTANCE: BaseApplication
    }
}
package android.learn.deezer

import android.app.Application
import android.learn.deezer.di.ApplicationComponent
import android.learn.deezer.di.DaggerApplicationComponent

class App : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}
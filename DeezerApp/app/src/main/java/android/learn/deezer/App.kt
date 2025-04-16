package android.learn.deezer

import android.app.Application
import android.learn.deezer.di.ApplicationComponent
import android.learn.deezer.di.DaggerApplicationComponent
import javax.inject.Inject

class App : Application() {
    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.create()
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}
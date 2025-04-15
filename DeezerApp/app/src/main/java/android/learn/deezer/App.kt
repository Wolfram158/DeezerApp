package android.learn.deezer

import android.app.Application
import android.learn.deezer.di.DaggerApplicationComponent
import javax.inject.Inject

class App : Application() {
    private val component: DaggerApplicationComponent by lazy {
        DaggerApplicationComponent.create()
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}
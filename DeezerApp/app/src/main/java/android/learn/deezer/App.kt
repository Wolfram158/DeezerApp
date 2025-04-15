package android.learn.deezer

import android.app.Application
import javax.inject.Inject

class App : Application() {
    //lateinit var component: DaggerApplicationComponent by lazy {
    //    DaggerApplicationComponent.create()
    //}

    override fun onCreate() {
        //component.inject(this)
        super.onCreate()
    }
}
package android.learn.di

import android.app.Application

class App : Application() {
    val component: ApplicationComponent by lazy { 
        DaggerApplicationComponent.create()
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}
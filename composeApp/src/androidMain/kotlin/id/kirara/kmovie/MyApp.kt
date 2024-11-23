package id.kirara.kmovie;

import android.app.Application

public class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Logger.init()
    }
}

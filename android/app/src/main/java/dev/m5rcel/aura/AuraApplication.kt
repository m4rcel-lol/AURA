package dev.m5rcel.aura

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.m5rcel.aura.notification.NotificationChannelBootstrapper
import javax.inject.Inject

@HiltAndroidApp
class AuraApplication : Application() {

    @Inject
    lateinit var channelBootstrapper: NotificationChannelBootstrapper

    override fun onCreate() {
        super.onCreate()
        channelBootstrapper.createAllChannels()
    }
}

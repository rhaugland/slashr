package com.slashphone.launcher.di

import android.content.Context
import com.slashphone.launcher.command.CommandParser
import com.slashphone.launcher.command.CommandRegistry
import com.slashphone.launcher.command.IntentLauncher
import com.slashphone.launcher.command.builtin.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCommandRegistry(
        parser: CommandParser,
        intentLauncher: IntentLauncher,
    ): CommandRegistry {
        val registry = CommandRegistry(parser)

        registry.register(CallCommand(
            contactLookup = { intentLauncher.lookupContact(it) },
            dialer = { intentLauncher.dial(it) },
        ))
        registry.register(TextCommand(
            contactLookup = { intentLauncher.lookupContact(it) },
            openSms = { intentLauncher.openSms(it) },
        ))
        registry.register(PeopleCommand(
            getInnerCircle = { emptyList() },
        ))
        registry.register(WeatherCommand(
            getWeather = { "Weather integration coming in next update." },
        ))
        registry.register(SearchCommand(
            openBrowser = { intentLauncher.openBrowser(it) },
        ))
        registry.register(TodayCommand(
            getCalendarEvents = { emptyList() },
        ))
        registry.register(TomorrowCommand(
            getCalendarEvents = { emptyList() },
        ))
        registry.register(GoCommand(
            openMaps = { intentLauncher.openMaps(it) },
        ))
        registry.register(NearCommand(
            openMapsSearch = { intentLauncher.openMapsSearch(it) },
        ))
        registry.register(CameraCommand(
            openCamera = { intentLauncher.openCamera() },
        ))
        registry.register(MusicCommand(
            openMusicPlayer = { intentLauncher.openMusicPlayer() },
        ))
        registry.register(InboxCommand())

        return registry
    }
}

package com.horizon.horizify.modules

import com.horizon.horizify.ui.bible.BibleModule
import com.horizon.horizify.ui.calendar.CalendarModule
import com.horizon.horizify.ui.home.HomeModule
import com.horizon.horizify.ui.podcast.PodcastModule
import com.horizon.horizify.ui.video.VideoModule

val appModules = listOf(
    serviceModules,
    sharedPreferenceModules,
    HomeModule.dependency,
    CalendarModule.dependency,
    PodcastModule.dependency,
    VideoModule.dependency,
    BibleModule.dependency
)



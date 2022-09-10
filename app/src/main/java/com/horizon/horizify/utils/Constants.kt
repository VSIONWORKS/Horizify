package com.horizon.horizify.utils

import com.horizon.horizify.ui.bible.model.BibleVersionNameModel

object Constants {
    const val YOUTUBE_API_KEY = "AIzaSyAUEbzSy4U6tVIZkSQeyCypvzpRbekvb0c"
    const val YOUTUBE_HORIZON_CHANNEL_ID = "UC5FebdXWfuDB-tKtIoa61Tg"
    const val YOUTUBE_PART = "snippet"

    const val BASE_URL = "https://youtube.googleapis.com/"

    const val CHECK_IN_URL = "https://checkin.thehorizonexp.org/events"
    const val GIVING_URL = "https://app.bux.ph/HorizonMinistries"

    const val PRIMARY = "primary"
    const val BANNER = "banner_"

    // Firebase Storage Location
    const val PRIMARY_BANNER_STORAGE = "/image/banner/primary/"
    const val BANNER_STORAGE = "/image/banner/carousel/"
    const val NETWORK_STORAGE = "/image/networks/"

    // Firebase Database Location
    const val PRIMARY_BANNER_DATABASE = "/data/banner/"
    const val BANNER_DATABASE= "/data/banner/carousel/"
    const val NETWORK_DATABASE = "/data/networks/"

    const val PREFS_NAME = "appPreference"
    const val SOURCE_FRAGMENT = "SOURCE_FRAGMENT"

    const val PLAYLIST_ITEMS = "playlistItems"
    const val WEB_URL = "webUrl"
    const val CURRENT_BIBLE = "currentBible"

    const val HORIZON_FACEBOOK_URL = "https://www.facebook.com/thehorizonexp/"
    const val HORIZON_URL = "https://checkin.thehorizonexp.org/"
    const val SUNDAY = "Sunday"

    const val ADMIN = "Admin"
    const val SETUP_BANNER = "Setup Banner"
    const val PRIMARY_BANNER = "Primary Banner"
    const val ADD_BANNER = "Add Banner"

    const val PODCAST_TRACKS = "https://thehorizonexp.org/podcast/rss.xml"

    // Map Latitude Longitude
    const val MAPS_API_KEY = "AIzaSyAFMwQ-xDa6rxhFmlqizykxcFBGDnwiIAQ"
    const val CUBAO_LOCATION_NAME = "Horizon Cubao"
    const val CUBAO_LATITUDE = 14.625748
    const val CUBAO_LONGITUDE = 121.060748

    const val MATIENZA_LOCATION_NAME = "Horizon Manila"
    const val MATIENZA_LATITUDE = 14.597270
    const val MATIENZA_LONGITUDE = 120.997357

    fun getBibleVersionName(version: BibleVersion): BibleVersionNameModel {
        return when (version) {
            BibleVersion.ANG_BAGONG_BIBLIA -> BibleVersionNameModel(longDescription = "Ang Bagong Ang Biblia", shortDescription = "ABAB", fileName = "bible_tagalog_abab.xml")
            BibleVersion.ANG_DATING_BIBLIA -> BibleVersionNameModel(longDescription = "Ang Dating Biblia", shortDescription = "ADB", fileName = "bible_tagalog_adb.xml")
            BibleVersion.THE_AMPLIFIED_BIBLE -> BibleVersionNameModel(longDescription = "The Amplified Bible", shortDescription = "AMP", fileName = "bible_english_amp")
            BibleVersion.CONTEMPORARY_ENGLISH_VERSION -> BibleVersionNameModel(longDescription = "Contemporary English Version", shortDescription = "CEV", fileName = "bible_english_cev")
            BibleVersion.ENGLISH_STANDARD_VERSION -> BibleVersionNameModel(longDescription = "English Standard Version", shortDescription = "ESV", fileName = "bible_english_esv")
            BibleVersion.KING_JAMES_VERSION -> BibleVersionNameModel(longDescription = "King James Version", shortDescription = "KJV", fileName = "bible_english_kjv")
            BibleVersion.MODERN_KING_JAMES_VERSION -> BibleVersionNameModel(longDescription = "Modern King James Version", shortDescription = "MKJV", fileName = "bible_english_mkjv.xml")
            BibleVersion.THE_MESSAGE -> BibleVersionNameModel(longDescription = "The Message", shortDescription = "MSG", fileName = "bible_english_msg")
            BibleVersion.NEW_AMERICAN_STANDARD_BIBLE -> BibleVersionNameModel(longDescription = "New American Standard Bible", shortDescription = "NASB", fileName = "bible_english_nasb")
            BibleVersion.NEW_INTERNATIONAL_VERSION -> BibleVersionNameModel(longDescription = "New International Version", shortDescription = "NIV", fileName = "bible_english_niv")
            BibleVersion.NEW_KING_JAMES_VERSION -> BibleVersionNameModel(longDescription = "New King James Version", shortDescription = "NKJV", fileName = "bible_english_nkjv")
            else -> BibleVersionNameModel(longDescription = "Revised Standard Version", shortDescription = "RSV", fileName = "bible_english_rsv.xml")
        }
    }
}
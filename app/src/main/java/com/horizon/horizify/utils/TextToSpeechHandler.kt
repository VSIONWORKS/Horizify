package com.horizon.horizify.utils

import android.app.Activity

object TextToSpeechHandler {

    var engine : TextToSpeech? = null

    fun setUpSpeechEngine(activity: Activity) : TextToSpeech {
        engine = TextToSpeech(activity)
        return engine as TextToSpeech
    }

    fun shutDownSpeechEngine(){
        engine?.shutDown()
    }
}
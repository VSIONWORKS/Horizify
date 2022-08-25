package com.horizon.horizify.utils

import android.app.Activity

object TextToSpeechHandler {

    var engine : TextToSpeech? = null

    var onComplete : ItemAction? = null

    fun setUpSpeechEngine(activity: Activity, onComplete : ItemAction) : TextToSpeech {
        engine = TextToSpeech(activity, onComplete)
        return engine as TextToSpeech
    }

    fun shutDownSpeechEngine(){
        engine?.shutDown()
    }

    fun speakComplete() {
        onComplete?.actionCallback?.invoke()
    }
}
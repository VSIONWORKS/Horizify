package com.horizon.horizify.utils

import android.app.Activity
import android.media.AudioManager
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.*

open class TextToSpeech(private val activity: Activity, isPH: Boolean = false) : TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {

    private val tts: TextToSpeech = TextToSpeech(activity, this)

    private var isReady: Boolean = false
    private var isTagalog: Boolean = isPH // to check
    private var messages: ArrayList<String> = arrayListOf()
    private var counter: Int = 1

    override fun onInit(i: Int) {
        if (i == TextToSpeech.SUCCESS) {

            val localeBR = Locale("pt", "BR")
            val localeUS = Locale.US

            val result: Int
            result = if (isTagalog) tts.setLanguage(localeBR) else tts.setLanguage(localeUS)

            tts.setOnUtteranceCompletedListener(this)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(activity, "This Language is not supported", Toast.LENGTH_SHORT).show()
            } else {
                isReady = true
            }

        } else {
            Toast.makeText(activity, "Initilization Failed!", Toast.LENGTH_SHORT).show()
        }
    }

    fun setLanguage(isEnglish:Boolean) {
        isTagalog = isEnglish
    }

    fun setMessages(scriptures:ArrayList<String>) {
        messages = scriptures
    }

    fun speak() {
        if (isReady) {
            counter = 1
            speakOut(messages.first())
        }
    }

    private fun speakOut(message: String) {
        val myHashAlarm = HashMap<String, String>()
        myHashAlarm[TextToSpeech.Engine.KEY_PARAM_STREAM] = AudioManager.STREAM_ALARM.toString()
        myHashAlarm[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "UTTERANCE_ID"
        tts.speak(message, TextToSpeech.QUEUE_FLUSH, myHashAlarm)
    }

    override fun onUtteranceCompleted(p0: String?) {
        if (counter != messages.size - 1) {
            speakOut(messages[counter])
            counter++
        }
    }

    fun stopEngine() {
        if (tts != null) {
            tts.stop()
            messages = arrayListOf()
        }
    }

    fun shutDown() {
        if (tts != null) {
            messages = arrayListOf()
            tts.stop()
            tts.shutdown()
        }
    }
}
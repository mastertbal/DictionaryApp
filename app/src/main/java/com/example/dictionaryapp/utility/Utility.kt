package com.example.dictionaryapp.utility

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer

fun playAudio(audioUrl: String) {
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setAudioAttributes(
        AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
            .build()
    )
    mediaPlayer.setDataSource(audioUrl)
    mediaPlayer.prepare()
    mediaPlayer.start()
}
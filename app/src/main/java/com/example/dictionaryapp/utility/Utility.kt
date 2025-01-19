package com.example.dictionaryapp.utility

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer

fun playAudio(audioUrl: String) {
    val mediaPlayer = MediaPlayer()
    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC)
    mediaPlayer.setDataSource(audioUrl)
    mediaPlayer.prepare()
    mediaPlayer.start()
}
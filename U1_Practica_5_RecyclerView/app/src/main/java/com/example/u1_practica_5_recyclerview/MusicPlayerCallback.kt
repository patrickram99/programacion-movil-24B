package com.example.u1_practica_5_recyclerview

interface MusicPlayerCallback {
    fun onSongSelected(song: Song)
    fun togglePlayPause()
    fun nextSong()
    fun previousSong()
    fun seekTo(progress: Int)
}
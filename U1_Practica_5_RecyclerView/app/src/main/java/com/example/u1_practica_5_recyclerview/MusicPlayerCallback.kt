/*
MusicPlayerCallback:
Interface used to comunicate both fragments for controls and RecyclerView

Autor                   : Patrick Ramirez
Fecha de creacion       : Martes 01 de octubre
Fecha de modificacion   : Jueves 03 de octubre

*/

package com.example.u1_practica_5_recyclerview

/**
 * A callback interface for handling music player actions.
 *
 * This interface is used to manage user interactions with a music player,
 * such as selecting a song, controlling playback, and seeking to a specific
 * position in the current song.
 */
interface MusicPlayerCallback {

    /**
     * Called when a [song] is selected by the user.
     *
     * @param song the song that was selected.
     */
    fun onSongSelected(song: Song)

    /**
     * Toggles between play and pause states of the music player.
     */
    fun togglePlayPause()

    /**
     * Skips to the next song in the playlist.
     */
    fun nextSong()

    /**
     * Goes back to the previous song in the playlist.
     */
    fun previousSong()

    /**
     * Seeks to a specific [progress] position in the current song.
     *
     * @param progress the position in milliseconds to seek to.
     */
    fun seekTo(progress: Int)
}
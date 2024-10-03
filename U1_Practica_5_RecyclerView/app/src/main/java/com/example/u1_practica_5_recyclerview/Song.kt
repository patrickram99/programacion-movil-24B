package com.example.u1_practica_5_recyclerview

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

/**
 * Data class representing a Song.
 *
 * @property name The name of the song.
 * @property artist The artist of the song.
 * @property duration The duration of the song.
 * @property isFavorite Indicates if the song is marked as favorite.
 * @property coverArt The resource ID for the cover art of the song.
 * @property songUrl The resource ID for the song file.
 */
class Song(
    var name: String,
    var artist: String,
    var duration: String,
    var isFavorite: Boolean = false,

    @DrawableRes
    var coverArt: Int,

    @RawRes
    var songUrl: Int
)

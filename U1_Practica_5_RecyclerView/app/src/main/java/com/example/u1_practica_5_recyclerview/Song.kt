package com.example.u1_practica_5_recyclerview

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

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

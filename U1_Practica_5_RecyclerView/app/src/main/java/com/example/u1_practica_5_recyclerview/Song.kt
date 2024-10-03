package com.example.u1_practica_5_recyclerview

import androidx.annotation.DrawableRes

class Song(
    var name: String,
    var artist: String,
    var duration: String,

    @DrawableRes
    var coverArt: Int,
    var songUrl: String
)

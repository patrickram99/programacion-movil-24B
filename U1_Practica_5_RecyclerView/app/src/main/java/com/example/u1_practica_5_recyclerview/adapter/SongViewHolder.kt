package com.example.u1_practica_5_recyclerview.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.u1_practica_5_recyclerview.R
import com.example.u1_practica_5_recyclerview.Song


class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val viewSongName = itemView.findViewById<TextView>(R.id.songName)
    private val viewSongArtist = itemView.findViewById<TextView>(R.id.artistName)
    private val viewSongDuration = itemView.findViewById<TextView>(R.id.songDuration)


    fun render(song: Song, onCLickListener: (Song) -> Unit) {
        viewSongName.text = song.name
        viewSongArtist.text = song.artist
        viewSongDuration.text = song.duration

        itemView.setOnClickListener() {
            onCLickListener(song)
        }
    }
}
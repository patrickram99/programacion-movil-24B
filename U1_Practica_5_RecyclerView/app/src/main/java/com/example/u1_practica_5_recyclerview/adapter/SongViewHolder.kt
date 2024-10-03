package com.example.u1_practica_5_recyclerview.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.u1_practica_5_recyclerview.R
import com.example.u1_practica_5_recyclerview.Song

class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val viewSongName = itemView.findViewById<TextView>(R.id.songName)
    private val viewSongArtist = itemView.findViewById<TextView>(R.id.artistName)
    private val viewSongDuration = itemView.findViewById<TextView>(R.id.songDuration)
    private val viewSongCover = itemView.findViewById<ImageView>(R.id.coverImage)
    private val heartButton = itemView.findViewById<ImageButton>(R.id.heartButton)

    fun render(
        song: Song,
        onItemSelected: (Song) -> Unit,
        onHeartClicked: (Song) -> Unit
    ) {
        viewSongName.text = song.name
        viewSongArtist.text = song.artist
        viewSongDuration.text = song.duration
        viewSongCover.setImageResource(song.coverArt)

        updateHeartIcon(song.isFavorite)

        itemView.setOnClickListener {
            onItemSelected(song)
        }

        heartButton.setOnClickListener {
            onHeartClicked(song)
            song.isFavorite = !song.isFavorite
            updateHeartIcon(song.isFavorite)
        }
    }

    private fun updateHeartIcon(isFavorite: Boolean) {
        if (isFavorite) {
            heartButton.setImageResource(R.drawable.ic_heart_filled)
        } else {
            heartButton.setImageResource(R.drawable.ic_heart_outline)
        }
    }
}
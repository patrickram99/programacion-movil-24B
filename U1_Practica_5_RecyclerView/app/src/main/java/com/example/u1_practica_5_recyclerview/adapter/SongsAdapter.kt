package com.example.u1_practica_5_recyclerview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.u1_practica_5_recyclerview.R
import com.example.u1_practica_5_recyclerview.Song

class SongsAdapter(
    var items: MutableList<Song>,
    private val onItemSelected: (Song) -> Unit
) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = items[position]
        holder.render(song)
    }

    override fun getItemCount(): Int = items.size

    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewSongName: TextView = itemView.findViewById(R.id.songName)
        private val viewSongArtist: TextView = itemView.findViewById(R.id.artistName)
        private val viewSongDuration: TextView = itemView.findViewById(R.id.songDuration)
        private val viewSongCover: ImageView = itemView.findViewById(R.id.coverImage)
        private val heartButton: ImageButton = itemView.findViewById(R.id.heartButton)

        fun render(song: Song) {
            viewSongName.text = song.name
            viewSongArtist.text = song.artist
            viewSongDuration.text = song.duration
            viewSongCover.setImageResource(song.coverArt)

            updateHeartIcon(song.isFavorite)

            itemView.setOnClickListener {
                onItemSelected(song)
            }

            heartButton.setOnClickListener {
                song.isFavorite = !song.isFavorite
                updateHeartIcon(song.isFavorite)
                // You can add any additional logic here for when the heart is clicked
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
}
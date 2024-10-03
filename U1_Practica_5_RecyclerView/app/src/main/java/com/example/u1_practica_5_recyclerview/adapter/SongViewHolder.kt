package com.example.u1_practica_5_recyclerview.adapter

import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.u1_practica_5_recyclerview.R
import com.example.u1_practica_5_recyclerview.Song

/**
 * ViewHolder for a [Song] item in the RecyclerView.
 *
 * This class holds references to the views in the item layout and provides methods to bind the data
 * to the views.
 *
 * @param itemView The root view of the item layout.
 */
class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val viewSongName = itemView.findViewById<TextView>(R.id.songName)
    private val viewSongArtist = itemView.findViewById<TextView>(R.id.artistName)
    private val viewSongDuration = itemView.findViewById<TextView>(R.id.songDuration)
    private val viewSongCover = itemView.findViewById<ImageView>(R.id.coverImage)
    private val heartButton = itemView.findViewById<ImageButton>(R.id.heartButton)

    /**
     * Binds the data of a [Song] to the views in the item layout.
     *
     * @param song The [Song] object to bind.
     * @param onItemSelected Callback invoked when the item is clicked.
     * @param onHeartClicked Callback invoked when the heart button is clicked.
     */
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

    /**
     * Updates the heart icon based on the favorite status of the song.
     *
     * @param isFavorite `true` if the song is marked as favorite, `false` otherwise.
     */
    private fun updateHeartIcon(isFavorite: Boolean) {
        if (isFavorite) {
            heartButton.setImageResource(R.drawable.ic_heart_filled)
        } else {
            heartButton.setImageResource(R.drawable.ic_heart_outline)
        }
    }
}
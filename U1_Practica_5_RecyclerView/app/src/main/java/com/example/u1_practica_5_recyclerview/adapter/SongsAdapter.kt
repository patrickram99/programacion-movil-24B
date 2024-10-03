/*
SongsAdapter:
Adapter for displaying a list of [Song] items in a RecyclerView.

Autor                   : Patrick Ramirez
Fecha de creacion       : Martes 01 de octubre
Fecha de modificacion   : Jueves 03 de octubre

*/

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

/**
 * Adapter for displaying a list of [Song] items in a RecyclerView.
 *
 * @param items The mutable list of [Song] items to be displayed.
 * @param onItemSelected Lambda function to be invoked when a song item is selected.
 */
class SongsAdapter(
    var items: MutableList<Song>,
    private val onItemSelected: (Song) -> Unit
) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    /**
     * Creates a new [SongViewHolder] inflating the item layout.
     *
     * @param parent The parent [ViewGroup] of the item view.
     * @param viewType The type of the view.
     * @return A new [SongViewHolder] instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    /**
     * Binds the data of a [Song] item to the [SongViewHolder] at the specified position.
     *
     * @param holder The [SongViewHolder] to bind the data to.
     * @param position The position of the item in the data set.
     */
    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = items[position]
        holder.render(song)
    }

    /**
     * Returns the total number of items in the data set.
     *
     * @return The number of items.
     */
    override fun getItemCount(): Int = items.size

    /**
     * ViewHolder class for a [Song] item in the RecyclerView.
     *
     * @property itemView The root view of the item layout.
     */
    inner class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val viewSongName: TextView = itemView.findViewById(R.id.songName)
        private val viewSongArtist: TextView = itemView.findViewById(R.id.artistName)
        private val viewSongDuration: TextView = itemView.findViewById(R.id.songDuration)
        private val viewSongCover: ImageView = itemView.findViewById(R.id.coverImage)
        private val heartButton: ImageButton = itemView.findViewById(R.id.heartButton)

        /**
         * Renders the data of a [Song] item in the corresponding views.
         *
         * @param song The [Song] item to be rendered.
         */
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

        /**
         * Updates the heart icon based on the favorite status of the song.
         *
         * @param isFavorite The favorite status of the song.
         */
        private fun updateHeartIcon(isFavorite: Boolean) {
            if (isFavorite) {
                heartButton.setImageResource(R.drawable.ic_heart_filled)
            } else {
                heartButton.setImageResource(R.drawable.ic_heart_outline)
            }
        }
    }
}
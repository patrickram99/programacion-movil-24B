package com.example.musicappfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class MusicInfoFragment : Fragment() {
    private lateinit var albumArt: ImageView
    private lateinit var songTitle: TextView
    private lateinit var artistName: TextView

    companion object {
        private const val ARG_SONG_INDEX = "song_index"

        fun newInstance(songIndex: Int): MusicInfoFragment {
            val fragment = MusicInfoFragment()
            val args = Bundle()
            args.putInt(ARG_SONG_INDEX, songIndex)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albumArt = view.findViewById(R.id.album_art)
        songTitle = view.findViewById(R.id.song_title)
        artistName = view.findViewById(R.id.artist_name)

        arguments?.getInt(ARG_SONG_INDEX)?.let { updateInfo(it) }
    }

    fun updateInfo(songIndex: Int) {
        val songs = resources.getStringArray(R.array.songs)
        val artists = resources.getStringArray(R.array.artists)
        val albumArtsArray = resources.obtainTypedArray(R.array.album_arts)

        songTitle.text = songs[songIndex]
        artistName.text = artists[songIndex]

        val albumArtResId = albumArtsArray.getResourceId(songIndex, 0)
        albumArt.setImageResource(albumArtResId)

        albumArtsArray.recycle()
    }
}
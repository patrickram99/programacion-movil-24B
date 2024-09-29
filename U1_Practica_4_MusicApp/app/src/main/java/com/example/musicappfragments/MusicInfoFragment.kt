package com.example.musicappfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * A fragment that displays music information.
 *
 * This fragment shows the album art, song title, and artist name for a selected song.
 */
class MusicInfoFragment : Fragment() {
    private lateinit var albumArt: ImageView
    private lateinit var songTitle: TextView
    private lateinit var artistName: TextView

    companion object {
        private const val ARG_SONG_INDEX = "song_index"

        /**
         * Creates a new instance of the [MusicInfoFragment].
         *
         * @param songIndex The index of the song to display information for.
         * @return A new instance of the [MusicInfoFragment].
         */
        fun newInstance(songIndex: Int): MusicInfoFragment {
            val fragment = MusicInfoFragment()
            val args = Bundle()
            args.putInt(ARG_SONG_INDEX, songIndex)
            fragment.arguments = args
            return fragment
        }
    }

    /**
     * Called to create the view hierarchy associated with the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState A Bundle containing previously saved state.
     * @return The View for the fragment's UI, or null.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music_info, container, false)
    }

    /**
     * Called immediately after [onCreateView] has returned, but before any saved state has been restored in to the view.
     *
     * @param view The View returned by [onCreateView].
     * @param savedInstanceState A Bundle containing previously saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        albumArt = view.findViewById(R.id.album_art)
        songTitle = view.findViewById(R.id.song_title)
        artistName = view.findViewById(R.id.artist_name)

        arguments?.getInt(ARG_SONG_INDEX)?.let { updateInfo(it) }
    }

    /**
     * Updates the music information displayed in the fragment.
     *
     * @param songIndex The index of the song to display information for.
     */
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
package com.example.musicappfragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * A fragment that represents a music player.
 *
 * This fragment handles the playback of music and manages the user interface
 * for controlling the music playback. It communicates with the [MusicInfoFragment]
 * and [MusicControlsFragment] to update the UI based on the current song and playback state.
 *
 * @property currentSong The index of the currently selected song.
 * @property music The [MediaPlayer] instance used for playing the music.
 * @property handler The [Handler] used for updating the seek bar progress.
 * @property updateSeekBar The [Runnable] that updates the seek bar progress.
 * @property isInitialPlay Indicates whether it is the initial play of the music.
 * @property isSeeking Indicates whether the user is currently seeking the music.
 */
class MusicPlayerFragment : Fragment() {
    private var currentSong: Int = 0
    private lateinit var music: MediaPlayer
    private lateinit var handler: Handler
    private lateinit var updateSeekBar: Runnable
    private var isInitialPlay = true
    private var isSeeking = false

    companion object {
        private const val ARG_SELECTED_INDEX = "selected_index"

        /**
         * Creates a new instance of [MusicPlayerFragment] with the specified selected index.
         *
         * @param selectedIndex The index of the selected song.
         * @return A new instance of [MusicPlayerFragment].
         */
        fun newInstance(selectedIndex: Int): MusicPlayerFragment {
            val fragment = MusicPlayerFragment()
            val args = Bundle()
            args.putInt(ARG_SELECTED_INDEX, selectedIndex)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            currentSong = it.getInt(ARG_SELECTED_INDEX)
        }
        handler = Handler(Looper.getMainLooper())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_music_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val infoFragment = MusicInfoFragment.newInstance(currentSong)
        val controlsFragment = MusicControlsFragment.newInstance()

        childFragmentManager.beginTransaction()
            .replace(R.id.info_container, infoFragment)
            .replace(R.id.controls_container, controlsFragment)
            .commit()

        setupMediaPlayer()
        setupSeekBarUpdater()

        // Start playing immediately
        music.start()
        isInitialPlay = false
        updatePlayPauseButton()
    }

    /**
     * Sets up the [MediaPlayer] instance for playing the music.
     */
    private fun setupMediaPlayer() {
        val audioArray = resources.obtainTypedArray(R.array.audio)
        val audioResId = audioArray.getResourceId(currentSong, 0)
        audioArray.recycle()

        music = MediaPlayer.create(context, audioResId)
        music.setVolume(0.5f, 0.5f)
    }

    /**
     * Sets up the [Runnable] for updating the seek bar progress.
     */
    private fun setupSeekBarUpdater() {
        updateSeekBar = object : Runnable {
            override fun run() {
                if (music.isPlaying && !isSeeking) {
                    val currentPosition = music.currentPosition
                    val duration = music.duration
                    val progress = (currentPosition.toFloat() / duration * 100).toInt()
                    (childFragmentManager.findFragmentById(R.id.controls_container) as? MusicControlsFragment)?.updateSeekBar(
                        progress,
                        100 // Set max to 100 for percentage
                    )
                }
                handler.postDelayed(this, 1000) // Update every second
            }
        }
        handler.post(updateSeekBar)
    }

    /**
     * Seeks the music to the specified progress position.
     *
     * @param progress The progress position to seek to, in percentage (0-100).
     */
    fun seekTo(progress: Int) {
        val duration = music.duration
        val newPosition = (progress.toFloat() / 100 * duration).toInt()
        music.seekTo(newPosition)

        // Update the UI immediately after seeking
        (childFragmentManager.findFragmentById(R.id.controls_container) as? MusicControlsFragment)?.updateSeekBar(
            progress,
            100
        )

        // If the music was playing before seeking, ensure it continues playing
        if (!music.isPlaying) {
            music.start()
            updatePlayPauseButton()
        }
    }

    /**
     * Called when the user starts tracking touch on the seek bar.
     */
    fun onStartTrackingTouch() {
        isSeeking = true
    }

    /**
     * Called when the user stops tracking touch on the seek bar.
     */
    fun onStopTrackingTouch() {
        isSeeking = false
    }

    /**
     * Toggles the play/pause state of the music.
     */
    fun togglePlayPause() {
        if (music.isPlaying) {
            music.pause()
        } else {
            music.start()
        }
        isInitialPlay = false
        updatePlayPauseButton()
    }

    /**
     * Plays the next song in the playlist.
     */
    fun nextSong() {
        if (currentSong < resources.getStringArray(R.array.songs).size - 1) {
            currentSong++
            updateSong()
        }
    }

    /**
     * Plays the previous song in the playlist.
     */
    fun previousSong() {
        if (currentSong > 0) {
            currentSong--
            updateSong()
        }
    }

    /**
     * Updates the currently playing song.
     */
    private fun updateSong() {
        music.stop()
        music.release()
        setupMediaPlayer()
        music.start()
        isInitialPlay = false
        updateUI()
    }

    /**
     * Updates the user interface based on the current song and playback state.
     */
    private fun updateUI() {
        (childFragmentManager.findFragmentById(R.id.info_container) as? MusicInfoFragment)?.updateInfo(currentSong)
        updatePlayPauseButton()
    }

    /**
     * Updates the play/pause button state based on the current playback state.
     */
    private fun updatePlayPauseButton() {
        (childFragmentManager.findFragmentById(R.id.controls_container) as? MusicControlsFragment)?.updatePlayPauseButton(!isInitialPlay && music.isPlaying)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(updateSeekBar)
    }

    override fun onDestroy() {
        super.onDestroy()
        music.release()
    }
}
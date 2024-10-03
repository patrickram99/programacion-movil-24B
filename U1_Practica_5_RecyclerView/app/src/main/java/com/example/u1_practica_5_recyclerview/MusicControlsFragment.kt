/*
The provided code is a Kotlin class named MusicControlsFragment that represents a fragment for displaying music playback controls. It includes a seek bar, play/pause button, next song button, previous song button, and a go back button. The fragment communicates with the parent MusicPlayerFragment through a callback interface to control music playback.

 */

package com.example.u1_practica_5_recyclerview


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.fragment.app.Fragment

/**
 * A fragment that displays music playback controls.
 *
 * This fragment includes a seek bar, play/pause button, next song button,
 * previous song button, and a go back button. It communicates with the parent
 * [MusicPlayerFragment] to control music playback.
 */
class MusicControlsFragment : Fragment() {
    private var musicPlayerCallback: MusicPlayerCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        musicPlayerCallback = context as? MusicPlayerCallback
    }
    companion object {
        /**
         * Creates a new instance of [MusicControlsFragment].
         *
         * @return a new instance of [MusicControlsFragment].
         */
        fun newInstance(): MusicControlsFragment {
            return MusicControlsFragment()
        }
    }

    private lateinit var seek: SeekBar
    private lateinit var playOrPauseButton: ImageButton
    private lateinit var nextSongButton: ImageButton
    private lateinit var previousSongButton: ImageButton
    private lateinit var goBackButton: Button

    /**
     * Called to create the view hierarchy associated with the fragment.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views in the fragment.
     * @param container The parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return the View for the fragment's UI, or null.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music_controls, container, false)
    }

    /**
     * Called immediately after [onCreateView] has returned, but before any saved state has been restored in to the view.
     *
     * @param view The View returned by [onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seek = view.findViewById(R.id.playback_seekbar)
        playOrPauseButton = view.findViewById(R.id.play_pause_button)
        nextSongButton = view.findViewById(R.id.next_button)
        previousSongButton = view.findViewById(R.id.previous_button)
        goBackButton = view.findViewById(R.id.go_back_button)

        setupClickListeners()

        // Set initial state to "Pause" icon
        updatePlayPauseButton(true)
    }

    /**
     * Sets up click listeners for the playback control buttons and seek bar.
     */
    private fun setupClickListeners() {
        playOrPauseButton.setOnClickListener { musicPlayerCallback?.togglePlayPause() }
        nextSongButton.setOnClickListener { musicPlayerCallback?.nextSong() }
        previousSongButton.setOnClickListener { musicPlayerCallback?.previousSong() }
        goBackButton.setOnClickListener { activity?.onBackPressed() }

        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicPlayerCallback?.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    /**
     * Updates the play/pause button icon based on the current playback state.
     *
     * @param isPlaying true if music is currently playing, false otherwise.
     */
    fun updatePlayPauseButton(isPlaying: Boolean) {
        playOrPauseButton.setImageResource(if (isPlaying) R.drawable.pause else R.drawable.play)
    }

    /**
     * Updates the seek bar progress and max values.
     *
     * @param progress the current progress value.
     * @param max the maximum value of the seek bar.
     */
    fun updateSeekBar(progress: Int, max: Int) {
        seek.max = max
        seek.progress = progress
    }

    /**
     * ELiminates the fragment from the lifecycle
     */
    override fun onDetach() {
        super.onDetach()
        musicPlayerCallback = null
    }
}
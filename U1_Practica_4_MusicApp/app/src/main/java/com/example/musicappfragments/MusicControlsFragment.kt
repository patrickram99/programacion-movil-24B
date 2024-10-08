/*
MusicControlsFragment:
A fragment that displays music playback controls.

Autor                   : Patrick Ramirez
Fecha de creacion       : Domingo 21 de septiembre
Fecha de modificacion   : Domingo 28 de septiembre

*/

package com.example.musicappfragments

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
        playOrPauseButton.setOnClickListener { (parentFragment as? MusicPlayerFragment)?.togglePlayPause() }
        nextSongButton.setOnClickListener { (parentFragment as? MusicPlayerFragment)?.nextSong() }
        previousSongButton.setOnClickListener { (parentFragment as? MusicPlayerFragment)?.previousSong() }
        goBackButton.setOnClickListener { activity?.onBackPressed() }

        seek.max = 100 // Set max to 100 for percentage
        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // Do nothing here, we'll handle seeking in onStopTrackingTouch
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                (parentFragment as? MusicPlayerFragment)?.onStartTrackingTouch()
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                seekBar?.let {
                    (parentFragment as? MusicPlayerFragment)?.seekTo(it.progress)
                }
                (parentFragment as? MusicPlayerFragment)?.onStopTrackingTouch()
            }
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
}
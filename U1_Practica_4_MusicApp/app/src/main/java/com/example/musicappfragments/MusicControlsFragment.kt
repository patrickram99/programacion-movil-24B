package com.example.musicappfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import androidx.fragment.app.Fragment

class MusicControlsFragment : Fragment() {
    companion object {
        fun newInstance(): MusicControlsFragment {
            return MusicControlsFragment()
        }
    }
    private lateinit var seek: SeekBar
    private lateinit var playOrPauseButton: ImageButton
    private lateinit var nextSongButton: ImageButton
    private lateinit var previousSongButton: ImageButton
    private lateinit var goBackButton: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_music_controls, container, false)
    }

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

    fun updatePlayPauseButton(isPlaying: Boolean) {
        playOrPauseButton.setImageResource(if (isPlaying) R.drawable.pause else R.drawable.play)
    }

    fun updateSeekBar(progress: Int, max: Int) {
        seek.max = max
        seek.progress = progress
    }

}
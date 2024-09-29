package com.example.musicappfragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class MusicPlayerFragment : Fragment() {
    private var currentSong: Int = 0
    private lateinit var music: MediaPlayer
    private lateinit var handler: Handler
    private lateinit var updateSeekBar: Runnable
    private var isInitialPlay = true
    private var isSeeking = false

    companion object {
        private const val ARG_SELECTED_INDEX = "selected_index"

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

    private fun setupMediaPlayer() {
        val audioArray = resources.obtainTypedArray(R.array.audio)
        val audioResId = audioArray.getResourceId(currentSong, 0)
        audioArray.recycle()

        music = MediaPlayer.create(context, audioResId)
        music.setVolume(0.5f, 0.5f)
    }

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

    fun onStartTrackingTouch() {
        isSeeking = true
    }

    fun onStopTrackingTouch() {
        isSeeking = false
    }


    fun togglePlayPause() {
        if (music.isPlaying) {
            music.pause()
        } else {
            music.start()
        }
        isInitialPlay = false
        updatePlayPauseButton()
    }

    fun nextSong() {
        if (currentSong < resources.getStringArray(R.array.songs).size - 1) {
            currentSong++
            updateSong()
        }
    }

    fun previousSong() {
        if (currentSong > 0) {
            currentSong--
            updateSong()
        }
    }

    private fun updateSong() {
        music.stop()
        music.release()
        setupMediaPlayer()
        music.start()
        isInitialPlay = false
        updateUI()
    }

    private fun updateUI() {
        (childFragmentManager.findFragmentById(R.id.info_container) as? MusicInfoFragment)?.updateInfo(currentSong)
        updatePlayPauseButton()
    }

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
package com.example.practica3.E2_MusicApp

import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.practica3.R

/**
 * MusicApp is an activity that provides a simple music player interface.
 * It allows users to play, pause, skip, and seek through a predefined list of songs.
 */
class MusicApp : AppCompatActivity() {

    private var music: MediaPlayer? = null
    private lateinit var seek: SeekBar
    private lateinit var goBackButton: Button
    private lateinit var playOrPauseButton: ImageButton
    private lateinit var nextSongButton: ImageButton
    private lateinit var previousSongButton: ImageButton
    private lateinit var songTitle: TextView
    private lateinit var artistName: TextView
    private lateinit var albumArt: ImageView
    private var currentSong: Int = 0

    private val songs: Array<String> by lazy { resources.getStringArray(R.array.songs) }
    private val artists: Array<String> by lazy { resources.getStringArray(R.array.artists) }
    private val albumArts = arrayOf(R.drawable.emotion, R.drawable.ts1989, R.drawable.lullaby, R.drawable.greedy, R.drawable.brat)
    private val audio = arrayOf(R.raw.run, R.raw.new_romantics, R.raw.wth, R.raw.greedy, R.raw.apple)

    private lateinit var updateSeekBar: Runnable
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_app)
        currentSong = intent.getIntExtra("selected_index", 0)  // Retrieve the index

        initializeViews()
        setupSeekBarUpdater()
        setupClickListeners()
        updateUI()
        setupGoBackButton()
    }

    /**
     * Initializes all view references.
     */
    private fun initializeViews() {
        seek = findViewById(R.id.playback_seekbar)
        playOrPauseButton = findViewById(R.id.play_pause_button)
        nextSongButton = findViewById(R.id.next_button)
        previousSongButton = findViewById(R.id.previous_button)
        songTitle = findViewById(R.id.song_title)
        artistName = findViewById(R.id.artist_name)
        albumArt = findViewById(R.id.album_art)
        goBackButton = findViewById(R.id.go_back_button)
    }

    /**
     * Sets up the Runnable for updating the SeekBar.
     */
    private fun setupSeekBarUpdater() {
        updateSeekBar = object : Runnable {
            override fun run() {
                music?.let {
                    val currentPosition = it.currentPosition
                    seek.progress = (currentPosition.toFloat() / it.duration * seek.max).toInt()
                }
                handler.postDelayed(this, 1000) // Update every second
            }
        }
    }

    /**
     * Sets up click listeners for all interactive elements.
     */
    private fun setupClickListeners() {
        setupSeekBarListener()
        setupNextButtonListener()
        setupPreviousButtonListener()
        setupPlayPauseButtonListener()
    }

    /**
     * Sets up the listener for the SeekBar.
     */
    private fun setupSeekBarListener() {
        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    music?.let {
                        val duration = it.duration
                        val newPosition = progress * duration / seek.max
                        it.seekTo(newPosition)
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    /**
     * Sets up the listener for the next button.
     */
    private fun setupNextButtonListener() {
        nextSongButton.setOnClickListener {
            if (currentSong < songs.size - 1) {
                currentSong += 1
                updateUI()
                music?.start()
            }
        }
    }

    /**
     * Sets up the listener for the previous button.
     */
    private fun setupPreviousButtonListener() {
        previousSongButton.setOnClickListener {
            if (currentSong > 0) {
                currentSong -= 1
                updateUI()
                music?.start()
            }
        }
    }

    /**
     * Sets up the listener for the play/pause button.
     */
    private fun setupPlayPauseButtonListener() {
        playOrPauseButton.setOnClickListener {
            music?.let {
                if (it.isPlaying) {
                    pauseMusic()
                } else {
                    playMusic()
                }
            }
        }
    }

    /**
     * Pauses the currently playing music.
     */
    private fun pauseMusic() {
        music?.pause()
        playOrPauseButton.setImageResource(R.drawable.play)
        handler.removeCallbacks(updateSeekBar)
    }

    /**
     * Starts or resumes playing the current music.
     */
    private fun playMusic() {
        music?.start()
        playOrPauseButton.setImageResource(R.drawable.pause)
        handler.post(updateSeekBar)
    }

    /**
     * Updates the UI with the current song information and starts playing it.
     */
    private fun updateUI() {
        songTitle.text = songs[currentSong]
        artistName.text = artists[currentSong]
        albumArt.setImageResource(albumArts[currentSong])
        setupNewMediaPlayer()
        playMusic()
        setupCompletionListener()
    }

    /**
     * Sets up a new MediaPlayer for the current song.
     */
    private fun setupNewMediaPlayer() {
        music?.release()
        music = MediaPlayer.create(this, audio[currentSong])
        music?.setVolume(0.5f, 0.5f)
    }

    /**
     * Sets up the completion listener for the current song.
     */
    private fun setupCompletionListener() {
        music?.setOnCompletionListener {
            if (currentSong < songs.size - 1) {
                currentSong += 1
                updateUI()
            } else {
                playOrPauseButton.setImageResource(R.drawable.play)
                handler.removeCallbacks(updateSeekBar)
            }
        }
    }

    /**
     * Retrieves the duration of an audio file from raw resources.
     *
     * @param rawResourceId The resource ID of the audio file.
     * @return The duration of the audio file in milliseconds.
     */
    private fun getAudioDuration(rawResourceId: Int): Long {
        val mmr = MediaMetadataRetriever()
        try {
            val afd = resources.openRawResourceFd(rawResourceId)
            mmr.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            val durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            return durationStr?.toLong() ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        } finally {
            mmr.release()
        }
    }
    /**
     * Sets up the go back button to finish the activity.
     */
    private fun setupGoBackButton() {
        goBackButton.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateSeekBar)
        music?.release()
        music = null
    }
}
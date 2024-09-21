package com.example.practica3.E2_MusicApp

import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.practica3.R

class MusicApp : AppCompatActivity() {
    private var music: MediaPlayer? = null
    private lateinit var seek: SeekBar
    private lateinit var playOrPauseButton: ImageButton
    private lateinit var nextSongButton: ImageButton
    private lateinit var previousSongButton: ImageButton
    private lateinit var songTitle: TextView
    private lateinit var artistName: TextView
    private lateinit var albumArt: ImageView
    private var currentSong: Int = 0

    private val songs: Array<String> by lazy { resources.getStringArray(R.array.songs) }
    private val artists: Array<String> by lazy { resources.getStringArray(R.array.artists) }

    private val albumArts = arrayOf(R.drawable.emotion, R.drawable.ts1989, R.drawable.lullaby)
    private val audio = arrayOf(R.raw.run, R.raw.new_romantics, R.raw.wth)

    private val durations = audio.map {
        getAudioDuration(it)
    }.toTypedArray()

    private lateinit var updateSeekBar: Runnable
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_app)

        seek = findViewById(R.id.playback_seekbar)
        playOrPauseButton = findViewById(R.id.play_pause_button)
        nextSongButton = findViewById(R.id.next_button)
        previousSongButton = findViewById(R.id.previous_button)
        songTitle = findViewById(R.id.song_title)
        artistName = findViewById(R.id.artist_name)
        albumArt = findViewById(R.id.album_art)

        updateSeekBar = object : Runnable {
            override fun run() {
                music?.let {
                    val currentPosition = it.currentPosition
                    seek.progress = (currentPosition.toFloat() / it.duration * seek.max).toInt()
                }
                handler.postDelayed(this, 1000) // Update every second
            }
        }

        updateUI()

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

        nextSongButton.setOnClickListener {
            if (currentSong < songs.size - 1) {
                currentSong += 1
                updateUI()
                music?.start()
            }
        }

        previousSongButton.setOnClickListener {
            if (currentSong > 0) {
                currentSong -= 1
                updateUI()
                music?.start()
            }
        }

        playOrPauseButton.setOnClickListener {
            music?.let {
                if (it.isPlaying) {
                    it.pause()
                    playOrPauseButton.setImageResource(R.drawable.play)
                    handler.removeCallbacks(updateSeekBar)
                } else {
                    it.start()
                    playOrPauseButton.setImageResource(R.drawable.pause)
                    handler.post(updateSeekBar)
                }
            }
        }
    }

    private fun updateUI() {
        songTitle.text = songs[currentSong]
        artistName.text = artists[currentSong]
        albumArt.setImageResource(albumArts[currentSong])
        music?.release()
        music = MediaPlayer.create(this, audio[currentSong])
        music?.setVolume(0.5f, 0.5f)
        music?.start()
        playOrPauseButton.setImageResource(R.drawable.pause)
        handler.post(updateSeekBar)
        music?.setOnCompletionListener {
            if (currentSong < songs.size - 1) {
                currentSong += 1
                updateUI()
                music?.start()
            } else {
                playOrPauseButton.setImageResource(R.drawable.play)
                handler.removeCallbacks(updateSeekBar)
            }
        }
    }

    fun getAudioDuration(rawResourceId: Int): Long {
        val mmr = MediaMetadataRetriever()
        try {
            val afd = resources.openRawResourceFd(rawResourceId)
            mmr.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            val durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            return durationStr!!.toLong()
        } catch (e: Exception) {
            e.printStackTrace()
            return 0
        } finally {
            mmr.release()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(updateSeekBar)
        music?.release()
        music = null
    }
}
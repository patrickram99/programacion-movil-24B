package com.example.practica3.E2_MusicApp

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.practica3.R

class MusicApp : AppCompatActivity() {
    private var music: MediaPlayer? = null
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
    private val audio = arrayOf(R.raw.emotion, R.raw.new_romantics, R.raw.wth)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_app)

        playOrPauseButton = findViewById(R.id.play_pause_button)
        nextSongButton = findViewById(R.id.next_button)
        previousSongButton = findViewById(R.id.previous_button)
        songTitle = findViewById(R.id.song_title)
        artistName = findViewById(R.id.artist_name)
        albumArt = findViewById(R.id.album_art)

        updateUI()

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
                } else {
                    it.start()
                    playOrPauseButton.setImageResource(R.drawable.pause)
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
        music?.setOnCompletionListener {
            if (currentSong < songs.size - 1) {
                currentSong += 1
                updateUI()
                music?.start()
            } else {
                playOrPauseButton.setImageResource(R.drawable.play)
            }
        }
    }

}
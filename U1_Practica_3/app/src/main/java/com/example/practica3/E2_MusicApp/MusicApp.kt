package com.example.practica3.E2_MusicApp

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.practica3.R

class MusicApp : AppCompatActivity() {
    private lateinit var music: MediaPlayer
    private lateinit var playOrPauseButton: ImageButton
    private lateinit var nextSongButton: ImageButton
    private lateinit var previousSongButton: ImageButton
    private lateinit var songTitle: TextView
    private lateinit var artistName: TextView
    private lateinit var albumArt: ImageView
    private var currentSong: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_app)

        playOrPauseButton = findViewById(R.id.play_pause_button)
        nextSongButton = findViewById(R.id.next_button)
        previousSongButton = findViewById(R.id.previous_button)
        songTitle = findViewById(R.id.song_title)
        artistName = findViewById(R.id.artist_name)
        albumArt = findViewById(R.id.album_art)

        val songs = resources.getStringArray(R.array.songs)
        val artists = resources.getStringArray(R.array.artists)
        val albumArts = arrayOf(R.drawable.emotion, R.drawable.ts1989, R.drawable.lullaby)

        updateUI(songs, artists, albumArts)

        nextSongButton.setOnClickListener {
            if (currentSong < songs.size - 1) {
                currentSong += 1
                updateUI(songs, artists, albumArts)
            }
        }

        previousSongButton.setOnClickListener {
            if (currentSong > 0) {
                currentSong -= 1
                updateUI(songs, artists, albumArts)
            }
        }
    }

    private fun updateUI(songs: Array<String>, artists: Array<String>, albumArts: Array<Int>) {
        songTitle.text = songs[currentSong]
        artistName.text = artists[currentSong]
        albumArt.setImageResource(albumArts[currentSong])
    }
}
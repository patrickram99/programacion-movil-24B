package com.example.u1_practica_5_recyclerview

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.u1_practica_5_recyclerview.fragments.SongListFragment

class MainActivity : AppCompatActivity(), MusicPlayerCallback {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var songProvider: SongProvider
    private var currentSongIndex = 0
    private lateinit var currentSong: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.song_list_container, SongListFragment())
                .replace(R.id.music_controls_container, MusicControlsFragment.newInstance())
                .commit()
        }

        mediaPlayer = MediaPlayer()
        songProvider = SongProvider.getInstance(applicationContext)

        // Add a completion listener to automatically play the next song
        mediaPlayer.setOnCompletionListener {
            nextSong()
        }

        // Start updating the seekbar
        updateSeekBar()
    }

    override fun onSongSelected(song: Song) {
        currentSongIndex = songProvider.listaSongs.indexOf(song)
        playSong(song)
    }

    private fun playSong(song: Song) {
        currentSong = findViewById(R.id.current_song)
        currentSong.text = getString(R.string.Reproduciendo, song.name)
        mediaPlayer.reset()
        val songUri = Uri.parse("android.resource://${packageName}/${song.songUrl}")
        mediaPlayer.setDataSource(this, songUri)
        mediaPlayer.prepare()
        mediaPlayer.start()
        updateMusicControls()
    }

    override fun togglePlayPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
        updateMusicControls()
    }

    override fun nextSong() {
        currentSongIndex = (currentSongIndex + 1) % songProvider.listaSongs.size
        playSong(songProvider.listaSongs[currentSongIndex])
    }

    override fun previousSong() {
        currentSongIndex = if (currentSongIndex > 0) currentSongIndex - 1 else songProvider.listaSongs.size - 1
        playSong(songProvider.listaSongs[currentSongIndex])
    }

    override fun seekTo(progress: Int) {
        val duration = mediaPlayer.duration
        val newPosition = (progress.toFloat() / 100 * duration).toInt()
        mediaPlayer.seekTo(newPosition)
    }

    private fun updateMusicControls() {
        val musicControlsFragment = supportFragmentManager.findFragmentById(R.id.music_controls_container) as? MusicControlsFragment
        musicControlsFragment?.updatePlayPauseButton(mediaPlayer.isPlaying)
        musicControlsFragment?.updateSeekBar((mediaPlayer.currentPosition * 100 / mediaPlayer.duration), 100)
    }

    private fun updateSeekBar() {
        runOnUiThread(object : Runnable {
            override fun run() {
                if (mediaPlayer.isPlaying) {
                    val musicControlsFragment = supportFragmentManager.findFragmentById(R.id.music_controls_container) as? MusicControlsFragment
                    musicControlsFragment?.updateSeekBar((mediaPlayer.currentPosition * 100 / mediaPlayer.duration), 100)
                }
                handler.postDelayed(this, 1000)
            }
        })
    }

    private val handler = android.os.Handler()

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacksAndMessages(null)
    }
}
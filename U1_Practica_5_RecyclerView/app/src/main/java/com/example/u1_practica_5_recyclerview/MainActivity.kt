package com.example.u1_practica_5_recyclerview

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.u1_practica_5_recyclerview.fragments.SongListFragment

/**
 * The main activity of the music player application.
 *
 * This activity handles the playback of songs and manages the fragments for displaying the song list and music controls.
 *
 * @property mediaPlayer the MediaPlayer instance used for playing songs.
 * @property songProvider the SongProvider instance used for accessing the list of songs.
 * @property currentSongIndex the index of the currently playing song in the song list.
 * @property currentSong the TextView displaying the name of the currently playing song.
 * @constructor Creates the main activity.
 */
class MainActivity : AppCompatActivity(), MusicPlayerCallback {
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var songProvider: SongProvider
    private var currentSongIndex = 0
    private lateinit var currentSong: TextView

    /**
     * Called when the activity is starting.
     * @param savedInstanceState the saved instance state bundle.
     */
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

    /**
     * Called when a song is selected from the song list.
     * @param song the selected song.
     */
    override fun onSongSelected(song: Song) {
        currentSongIndex = songProvider.listaSongs.indexOf(song)
        playSong(song)
    }

    /**
     * Plays the specified song.
     * @param song the song to play.
     */
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

    /**
     * Toggles the play/pause state of the media player.
     */
    override fun togglePlayPause() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.start()
        }
        updateMusicControls()
    }

    /**
     * Plays the next song in the song list.
     */
    override fun nextSong() {
        currentSongIndex = (currentSongIndex + 1) % songProvider.listaSongs.size
        playSong(songProvider.listaSongs[currentSongIndex])
    }

    /**
     * Plays the previous song in the song list.
     */
    override fun previousSong() {
        currentSongIndex = if (currentSongIndex > 0) currentSongIndex - 1 else songProvider.listaSongs.size - 1
        playSong(songProvider.listaSongs[currentSongIndex])
    }

    /**
     * Seeks to the specified progress position in the currently playing song.
     * @param progress the progress position as a percentage (0-100).
     */
    override fun seekTo(progress: Int) {
        val duration = mediaPlayer.duration
        val newPosition = (progress.toFloat() / 100 * duration).toInt()
        mediaPlayer.seekTo(newPosition)
    }

    /**
     * Updates the music controls fragment with the current playback state.
     */
    private fun updateMusicControls() {
        val musicControlsFragment = supportFragmentManager.findFragmentById(R.id.music_controls_container) as? MusicControlsFragment
        musicControlsFragment?.updatePlayPauseButton(mediaPlayer.isPlaying)
        musicControlsFragment?.updateSeekBar((mediaPlayer.currentPosition * 100 / mediaPlayer.duration), 100)
    }

    /**
     * Updates the seek bar position periodically.
     */
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

    /**
     * Called when the activity is being destroyed.
     */
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        handler.removeCallbacksAndMessages(null)
    }
}
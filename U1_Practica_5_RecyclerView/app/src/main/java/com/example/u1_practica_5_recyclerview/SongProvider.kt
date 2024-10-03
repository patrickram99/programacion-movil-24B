package com.example.u1_practica_5_recyclerview

import android.content.Context
import android.media.MediaMetadataRetriever
import java.util.concurrent.TimeUnit

class SongProvider private constructor(context: Context) {
    companion object {
        var instance: SongProvider? = null

        fun getInstance(context: Context): SongProvider {
            if (instance == null) {
                instance = SongProvider(context)
            }
            return instance!!
        }
    }

    val listaSongs: ArrayList<Song>

    init {
        val songTitles = context.resources.getStringArray(R.array.songs)
        val artists = context.resources.getStringArray(R.array.artists)
        val audioResources = context.resources.obtainTypedArray(R.array.audio)
        val albumArts = context.resources.obtainTypedArray(R.array.album_arts)

        listaSongs = ArrayList()

        for (i in songTitles.indices) {
            val audioResourceId = audioResources.getResourceId(i, 0)
            val duration = calculateDuration(context, audioResourceId)

            val song = Song(
                name = songTitles[i],
                artist = artists[i],
                duration = duration,
                coverArt = albumArts.getResourceId(i, 0),
                songUrl = "android.resource://${context.packageName}/$audioResourceId"
            )
            listaSongs.add(song)
        }

        audioResources.recycle()
        albumArts.recycle()
    }

    private fun calculateDuration(context: Context, resourceId: Int): String {
        val retriever = MediaMetadataRetriever()
        val assetFileDescriptor = context.resources.openRawResourceFd(resourceId)

        retriever.setDataSource(
            assetFileDescriptor.fileDescriptor,
            assetFileDescriptor.startOffset,
            assetFileDescriptor.length
        )

        val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        val durationMs = durationStr?.toLong() ?: 0

        retriever.release()
        assetFileDescriptor.close()

        val minutes = TimeUnit.MILLISECONDS.toMinutes(durationMs)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(durationMs) % 60

        return String.format("%d:%02d", minutes, seconds)
    }
}
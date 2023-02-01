package com.example.myapplication.cloud_storage

import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityMainCsMusicTestBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import wseemann.media.FFmpegMediaMetadataRetriever

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainCsMusicTestBinding
    private val mediaPlayer = MediaPlayer()
    private val ffmmr = FFmpegMediaMetadataRetriever()

    private val uri = "gs://my-application-be885.appspot.com"
    private val storage = Firebase.storage(uri)
    private var storageRef = storage.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainCsMusicTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPlayResume.setOnClickListener {
            onClickBtnPlayResumeAudio()
        }
        binding.btnPause.setOnClickListener {
            onClickBtnPauseAudio()
        }

        val imageRef = storageRef.child("music-app/audio/Missing-You-Phuong-Ly-TINLE.mp3")
        imageRef.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val url = task.result
                Log.d(TAG, "url = $url")

                // setup ff media metadata - async
                getImageFromMp3File(url)

                // setup media player
                mediaPlayer.apply {
                    setAudioStreamType(AudioManager.STREAM_MUSIC)
                    setDataSource(applicationContext, url)
                    prepare()
                    start()
                    Log.d(TAG, "start music")
                }
            }
        }
    }

    private fun getImageFromMp3File(url: Uri?) {
        GlobalScope.launch() {
            ffmmr.setDataSource(url.toString())
            launch(Dispatchers.IO) {
                ffmmr.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST).let {
                    withContext(Dispatchers.Main) {
                        binding.tvSinger.text = it
                        Log.d(TAG, "load artist name")
                    }
                }
            }

            launch(Dispatchers.IO) {
                ffmmr.embeddedPicture.let { data ->
                    val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                    withContext(Dispatchers.Main) {
                        Glide
                            .with(this@MainActivity)
                            .load(bitmap)
                            .centerCrop()
                            .into(binding.imvCoverPhoto)
                        Log.d(TAG, "load cover photo")
                    }
                }
            }
        }
    }

    private fun onClickBtnPlayResumeAudio() {
        if (mediaPlayer.isPlaying) {
            return
        } else {
            mediaPlayer.start()
            Log.d(TAG, "resume music")
        }
    }

    private fun onClickBtnPauseAudio() {
        if (!mediaPlayer.isPlaying) {
            return
        } else {
            mediaPlayer.pause()
            Log.d(TAG, "pause music")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
        ffmmr.release()
        Log.d(TAG, "release media-metadata")
    }
}
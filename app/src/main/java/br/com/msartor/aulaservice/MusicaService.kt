package br.com.msartor.aulaservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast

class MusicaService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        inicializarMediaPlayer()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(mediaPlayer == null){
            inicializarMediaPlayer()
        }
        mediaPlayer?.start()
        Toast.makeText(this,"Tocando", Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        releaseMediaPlayer()
        super.onDestroy()
    }

    private fun inicializarMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.teste)
    }

    private fun releaseMediaPlayer(){
        if( mediaPlayer != null){
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}
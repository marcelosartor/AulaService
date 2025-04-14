package br.com.msartor.aulaservice.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import br.com.msartor.aulaservice.R
import java.time.LocalDateTime
import java.time.ZoneId

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
        val idCanal = "notificacaoLembrete"

        val notificationManager = getSystemService(NotificationManager::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(idCanal, "Lembrete", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(canal)
        }

        val localDateTime = LocalDateTime.now() // Ou qualquer LocalDateTime que você tenha
        val timestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val notification = NotificationCompat
            .Builder(this, idCanal).apply {
                setSmallIcon(R.drawable.ic_musica_24)
                //setWhen(timestamp)
                setShowWhen(true)
                setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.album))
                setContentTitle("Gavin DeGraw")
                setContentText("I Don't Want to Be")
            }


        if(mediaPlayer == null){
            inicializarMediaPlayer()
        }
        mediaPlayer?.start()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1, notification.build(), ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
        }else{
            startForeground(1, notification.build())
        }

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
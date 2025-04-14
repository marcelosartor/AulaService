package br.com.msartor.aulaservice.service

import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import br.com.msartor.aulaservice.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId


class MinhaConexao : Service() {

    private val coroutine = CoroutineScope(Dispatchers.IO)
    var contador = 0

    inner class CustomBinder : Binder() {
        fun recuperarService(): MinhaConexao {
            return this@MinhaConexao
        }
    }

    override fun onBind(intent: Intent): IBinder? {
       return CustomBinder()
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("MeuServico", "onCreate: ")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("MeuServico", "onStartCommand: ")


        val idCanal = "notificacaoLembrete"

        val notificationManager = getSystemService(NotificationManager::class.java)
        val canal = notificationManager.getNotificationChannel(idCanal)
        if (canal == null) {
            Log.e("MeuServico", "Canal de notificação não existe!")
        }

        val localDateTime = LocalDateTime.now() // Ou qualquer LocalDateTime que você tenha
        val timestamp = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val notification = NotificationCompat
            .Builder(this, idCanal).apply {
                setSmallIcon(R.drawable.ic_localizacao_24)
                setWhen(timestamp)
                setShowWhen(true)
                setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.perfil))
                setContentTitle("Acompanhando a localização")
                setContentText("Necessario para acompanhar seus passsos")
            }


        /*
        val notification = NotificationCompat.Builder(this, idCanal)
            .setSmallIcon(R.drawable.ic_localizacao_24)
            .setContentTitle("Acompanhando a localização")
            .setContentText("Necessário para acompanhar seus passos")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .setOngoing(true) // Impede que o usuário a remova manualmente
            .build()


        Log.i("MeuServico", "Antes do startForeground")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // SDK 34+
            Log.i("MeuServico", "Antes do startForeground 34")
            startForeground(1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE)
        } else {
            Log.i("MeuServico", "Antes do startForeground 34")
            startForeground(1, notification)
        }
         */

        Log.i("MeuServico", "Antes do startForeground 34")
        startForeground(1, notification.build())

        coroutine.launch {
            repeat(10) { i ->
                contador = i
                Log.i("MeuServico", "run: $contador")
                delay(500)
            }
            stopSelf()
        }
        //return START_STICKY
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.i("MeuServico", "onDestroy: ")
        coroutine.cancel()
        super.onDestroy()
    }
}
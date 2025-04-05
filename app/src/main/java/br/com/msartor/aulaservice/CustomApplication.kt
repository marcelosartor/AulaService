package br.com.msartor.aulaservice

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager

class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        criarCanais()
    }

    private fun criarCanais() {
        val idCanal = "notificacaoLembrete"
        val canal = NotificationChannel(
            idCanal,
            "Lembrete",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            enableLights(true)
            enableVibration(true)
        }

        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(canal)
            //.createNotificationChannels(listOf(canal))
    }
}
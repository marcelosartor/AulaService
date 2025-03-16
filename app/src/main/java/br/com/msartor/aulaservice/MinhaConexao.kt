package br.com.msartor.aulaservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        coroutine.launch {
            repeat(10) { i ->
                contador = i
                Log.i("MeuServico", "run: $contador")
                delay(2000)
            }
            stopSelf()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.i("MeuServico", "onDestroy: ")
        coroutine.cancel()
        super.onDestroy()
    }
}
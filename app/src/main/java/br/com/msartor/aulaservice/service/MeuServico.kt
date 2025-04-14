package br.com.msartor.aulaservice.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/* ------------------------------------------
Exemplo não utilizado
 ------------------------------------------ */
class MeuServico: Service() {

    private val coroutine = CoroutineScope(Dispatchers.IO)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.i("MeuServico", "onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // codigo do servico
        Log.i("MeuServico", "onStartCommand: ")

        // Thread para executar o codigo do servico
        //val minhaThread = MinhaThread()
        //minhaThread.start()
        val tempo = intent?.extras?.getLong("tempoDuracao") ?: 2000L

        coroutine.launch {
            repeat(10) {contador->
                Log.i("MeuServico", "run: $contador tempo: $tempo")
                delay(tempo)
            }
            stopSelf()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    inner class MinhaThread: Thread() {
        override fun run() {
            super.run()
            repeat(10) {contador->
                Log.i("MeuServico", "run: $contador")
                sleep(2000)
            }
            stopSelf()
        }
    }

    override fun onDestroy() {
        Log.i("MeuServico", "onDestroy: ")
        coroutine.cancel()
        super.onDestroy()
    }

}
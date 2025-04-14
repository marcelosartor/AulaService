package br.com.msartor.aulaservice.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ReinicializacaoReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == Intent.ACTION_BOOT_COMPLETED){
            Log.i("ReinicializacaoReceiver", "Reiniciando serviço")
        }

    }
}
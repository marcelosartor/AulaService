package br.com.msartor.aulaservice.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ComunicacaoBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if(intent.action == "br.com.msartor.aulaservice.ABRIR_ARQUIVO_PDF"){
            Log.i("ComunicacaoBroadcastReceiver", "Abrindo arquivo PDF")
        }
    }
}
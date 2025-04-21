package br.com.msartor.aulaservice.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class BroadcastPriority02Receiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Thread.sleep(2000)
        var initialCode = resultCode
        var initialData = resultData
        var extras = getResultExtras(true)
        var dadosExtras = extras.getString("DadosExtras")

        val textoParametro = "[B2] Code: $initialCode - Data: $initialData - Extras: $dadosExtras"
        Log.i("broadcast_android", textoParametro)

        initialCode++
        initialData += " -> Receive 02"
        dadosExtras += " -> Alterado 02"
        extras.putString("DadosExtras", dadosExtras)

        setResult(initialCode,initialData,extras)
    }
}
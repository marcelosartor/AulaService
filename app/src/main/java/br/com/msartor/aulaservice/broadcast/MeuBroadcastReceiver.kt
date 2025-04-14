package br.com.msartor.aulaservice.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast

class MeuBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val statusWifi = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)

        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val message = when(intent.action){
            Intent.ACTION_AIRPLANE_MODE_CHANGED->"ACTION_AIRPLANE_MODE_CHANGED"
            Intent.ACTION_BATTERY_CHANGED-> "ACTION_BATTERY_CHANGED "
            Intent.ACTION_POWER_CONNECTED->"ACTION_POWER_CONNECTED "
            Intent.ACTION_POWER_DISCONNECTED->"ACTION_POWER_DISCONNECTED "
            WifiManager.WIFI_STATE_CHANGED_ACTION->"WIFI_STATE_CHANGED_ACTION "
            else -> intent.action.toString()
        }

        Log.i("MeuBroadcastReceiver", "Status: $statusWifi")
        val messageWifi = when(statusWifi){
            WifiManager.WIFI_STATE_ENABLED->{
                Log.i("MeuBroadcastReceiver", "Status: WIFI Habilitado")
                "WIFI Habilitado"}
            WifiManager.WIFI_STATE_DISABLED->{
                Log.i("MeuBroadcastReceiver", "Status: WIFI Desabilitado")
                "WIFI Desabilitado"}
            else->{
                Log.i("MeuBroadcastReceiver", "Status: WIFI processando")
                "Processando WIFI"}
        }

        Log.i("MeuBroadcastReceiver", message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        Toast.makeText(context, messageWifi, Toast.LENGTH_SHORT).show()


    }
}
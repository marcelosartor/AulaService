package br.com.msartor.aulaservice

import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.msartor.aulaservice.broadcast.MeuBroadcastReceiver
import br.com.msartor.aulaservice.databinding.ActivityBroadcastBinding

class BroadcastActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBroadcastBinding.inflate(layoutInflater)
    }
    private lateinit var meuBroadcastReceiver: MeuBroadcastReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        inicializarBroadcast()

        /* Verifica se o wifi esta habilitado.
        val wifiManager = getSystemService(WifiManager::class.java)
        if(wifiManager.isWifiEnabled){
            binding.textResultado.text = "WIFI-Habilidado"
        }else{
            binding.textResultado.text = "WIFI-Desabilidado"
        }
         */


    }

    private fun inicializarBroadcast() {
        meuBroadcastReceiver = MeuBroadcastReceiver()
        IntentFilter().apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            addAction(Intent.ACTION_BATTERY_CHANGED)
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
        }.also {
            registerReceiver(meuBroadcastReceiver, it)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(meuBroadcastReceiver)
        super.onDestroy()
    }
}
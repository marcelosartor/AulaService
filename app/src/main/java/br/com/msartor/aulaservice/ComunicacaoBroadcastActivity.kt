package br.com.msartor.aulaservice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.msartor.aulaservice.broadcast.ComunicacaoBroadcastReceiver
import br.com.msartor.aulaservice.databinding.ActivityBroadcastBinding

class ComunicacaoBroadcastActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBroadcastBinding.inflate(layoutInflater)
    }

    private lateinit var comunicacaoBroadcastReceiver: ComunicacaoBroadcastReceiver

    private val broadcastActivity = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            var message = "Cabo de Energia"
            if (intent.action == Intent.ACTION_POWER_CONNECTED) {
                message = "$message Conectado"
                Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
                binding.textInformacao.text = message
            }else{
                message = "$message Desconectado"
                Toast.makeText(applicationContext,message, Toast.LENGTH_SHORT).show()
                binding.textInformacao.text = message
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        comunicacaoBroadcastReceiver = ComunicacaoBroadcastReceiver()
        IntentFilter("br.com.msartor.aulaservice.ABRIR_ARQUIVO_PDF").apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Log.i("ComunicacaoBroadcastReceiver", "Registro 1")
                registerReceiver(comunicacaoBroadcastReceiver, this, Context.RECEIVER_EXPORTED)
            } else {
                @Suppress("DEPRECATION")
                Log.i("ComunicacaoBroadcastReceiver", "Registro 2")
                registerReceiver(comunicacaoBroadcastReceiver, this)
            }
        }

        // Broadcast Activity
        IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
        }.also {
             registerReceiver(broadcastActivity, it)
        }
    }

    override fun onDestroy() {
        unregisterReceiver(comunicacaoBroadcastReceiver)
        unregisterReceiver(broadcastActivity)
        super.onDestroy()
    }
}
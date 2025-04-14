package br.com.msartor.aulaservice

import android.content.Context
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
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

    }

    override fun onDestroy() {
        unregisterReceiver(comunicacaoBroadcastReceiver)
        super.onDestroy()
    }
}
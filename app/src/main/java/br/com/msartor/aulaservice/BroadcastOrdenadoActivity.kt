package br.com.msartor.aulaservice

import android.content.Context
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.msartor.aulaservice.broadcast.BroadcastPriority01Receiver
import br.com.msartor.aulaservice.broadcast.BroadcastPriority02Receiver
import br.com.msartor.aulaservice.broadcast.BroadcastPriority03Receiver
import br.com.msartor.aulaservice.databinding.ActivityBroadcastBinding
import br.com.msartor.aulaservice.databinding.ActivityBroadcastOrdenadoBinding

class BroadcastOrdenadoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBroadcastOrdenadoBinding.inflate(layoutInflater)
    }

    private var broadcastPriority01Receiver:BroadcastPriority01Receiver = BroadcastPriority01Receiver()
    private var broadcastPriority02Receiver:BroadcastPriority02Receiver = BroadcastPriority02Receiver()
    private var broadcastPriority03Receiver:BroadcastPriority03Receiver = BroadcastPriority03Receiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        IntentFilter().apply {
            addAction("br.com.msartor.aulaservice.ACAO_ORDENADO")
            priority = 10
        }.also {
            registerReceiver(broadcastPriority01Receiver,it, Context.RECEIVER_EXPORTED)
        }

        IntentFilter().apply {
            addAction("br.com.msartor.aulaservice.ACAO_ORDENADO")
            priority = 5
        }.also {
            registerReceiver(broadcastPriority02Receiver,it, Context.RECEIVER_EXPORTED)
        }


        IntentFilter().apply {
            addAction("br.com.msartor.aulaservice.ACAO_ORDENADO")
            priority = 1
        }.also {
            registerReceiver(broadcastPriority03Receiver,it, Context.RECEIVER_EXPORTED)
        }

    }

    override fun onDestroy() {
        unregisterReceiver(broadcastPriority01Receiver)
        unregisterReceiver(broadcastPriority02Receiver)
        unregisterReceiver(broadcastPriority03Receiver)
        super.onDestroy()
    }
}
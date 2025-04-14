package br.com.msartor.aulaservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.msartor.aulaservice.databinding.ActivityMainBinding
import br.com.msartor.aulaservice.service.MinhaConexao

class MainActivity : AppCompatActivity(),ServiceConnection {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var serviceConnection: ServiceConnection
    private lateinit var servicoConectado: MinhaConexao


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        solicitarPermissaoNotificacao()

        val serviceConnection = this
        //val meuServico = Intent(this,MeuServico::class.java)
        val minhaConexaoServico = Intent(this, MinhaConexao::class.java)


        binding.btnIniciarService.setOnClickListener {
            //meuServico.putExtra("tempoDuracao",3000L)
            //startService(meuServico)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(minhaConexaoServico)
            }else{
                startService(minhaConexaoServico)
            }
            bindService(minhaConexaoServico,serviceConnection, BIND_AUTO_CREATE)
        }
        binding.btnParaService.setOnClickListener{
            stopService(minhaConexaoServico)
            unbindService(serviceConnection)
        }

        binding.btnPegarDados.setOnClickListener {
            val contador = servicoConectado.contador
            Toast.makeText(this,"Contador: $contador",Toast.LENGTH_SHORT).show()
        }

    }

    private fun solicitarPermissaoNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33+
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
            /*
            val permissionNotification = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
            if(permissionNotification != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
            */
        }
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        Log.i("MeuServico", "onServiceConnected: ")
        val customBinder = service as MinhaConexao.CustomBinder
        servicoConectado = customBinder.recuperarService()

    }

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.i("MeuServico", "onServiceDisconnected: ")
        Toast.makeText(this,"Serviço Desconectado",Toast.LENGTH_SHORT).show()
    }
}



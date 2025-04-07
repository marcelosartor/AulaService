package br.com.msartor.aulaservice

import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.msartor.aulaservice.databinding.ActivityMusicaBinding

class MusicaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMusicaBinding.inflate(layoutInflater)
    }

    private var mediaPlayer: MediaPlayer? = null


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
        inicializarMediaPlayer()
        inicializarControles()
    }

    private fun solicitarPermissaoNotificacao() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // API 33+
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1)
            }
        }
    }
    private fun inicializarControles() {
        binding.btnTocar.setOnClickListener { play() }
        binding.btnPausar.setOnClickListener { pause() }
        binding.btnParar.setOnClickListener { stop() }
        inicializarControleDeVolume()
        executarServicoMusica()
    }

    private fun executarServicoMusica() {

        val musicaService = Intent(this, MusicaService::class.java)


        binding.btnIniciarServicoMusica.setOnClickListener{
            //In Background
            //startService(musicaService)

            //In Foreground
            startForegroundService(musicaService)
        }
        binding.btnPararServicoMusica.setOnClickListener{
            stopService(musicaService)
        }
    }

    private fun inicializarControleDeVolume() {
        // Outra forma de pegar o AudioManager
        //val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        val audioManager = getSystemService(AudioManager::class.java)

        binding.seekVolume.max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        binding.seekVolume.progress = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        binding.seekVolume.setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress,0)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
    }

    private fun releaseMediaPlayer(){
        if( mediaPlayer != null){
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    private fun stop() {
        if(mediaPlayer?.isPlaying == true){
            releaseMediaPlayer()
        }
        Toast.makeText(this,"Parado",Toast.LENGTH_SHORT).show()
    }

    private fun pause() {
        if(mediaPlayer?.isPlaying == true){
            mediaPlayer?.pause()
        }
        Toast.makeText(this,"Pausado",Toast.LENGTH_SHORT).show()
    }

    private fun play() {
        if(mediaPlayer == null){
            inicializarMediaPlayer()
        }
        mediaPlayer?.start()
        Toast.makeText(this,"Tocando",Toast.LENGTH_SHORT).show()
    }

    private fun inicializarMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.teste)
    }


    override fun onDestroy() {
        super.onDestroy()
        releaseMediaPlayer()
    }
}
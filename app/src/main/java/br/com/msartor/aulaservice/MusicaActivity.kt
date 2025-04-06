package br.com.msartor.aulaservice

import android.media.MediaPlayer
import android.os.Bundle
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
        inicializarMediaPlayer()
        inicializarControles()
    }

    private fun inicializarControles() {
        binding.btnTocar.setOnClickListener { play() }
        binding.btnPausar.setOnClickListener { pause() }
        binding.btnParar.setOnClickListener { stop() }
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
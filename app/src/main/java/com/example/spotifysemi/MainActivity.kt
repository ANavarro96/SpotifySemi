package com.example.spotifysemi

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var mediaPlayer: MediaPlayer
    lateinit var albumCover: ImageView
    lateinit var  songName: TextView
    lateinit var  btnPrev: ImageButton
    lateinit var  btnPause: ImageButton
    lateinit var  btnNext: ImageButton
    lateinit var seekBar: SeekBar
    lateinit var  btnLoop: ImageButton
    lateinit var  tiempoLlevamos: TextView
    lateinit var  tiempoTotal: TextView

    private var cancionActual = 0
    private lateinit var canciones: List<Cancion>
    private val handler = Handler(Looper.getMainLooper())

    fun formatearTiempo(milisegundos: Int): String {
        val seg = (milisegundos / 1000) % 60
        val min = (milisegundos / (1000 * 60)) % 60
        return String.format("%02d:%02d", min, seg)
    }

    // Función para actualizar la UI con los datos de la canción actual
    fun actualizarCancion() {
        // TODO 4: Obtener los nuevos valores y ajustar la GUI
        val cancion = canciones[cancionActual]

        // Cambiar la canción en MediaPlayer
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer.create(this, cancion.audio)
        mediaPlayer.setOnCompletionListener {
            // TODO: ¿Qué hace esto?
                if (cancionActual < canciones.size - 1) {
                    cancionActual++
                    actualizarCancion()
                } else {
                    Toast.makeText(this, "Fin de la lista de reproducción", Toast.LENGTH_SHORT).show()
                }

        }
        // TODO 5: Gestionar la SeekBar y el tiempo

        mediaPlayer.start()
    }

    fun inicializarVistas(){
        albumCover =  findViewById(R.id.albumCover)
        songName = findViewById(R.id.textView)
        //val albumName: TextView = findViewById(R.id.albumName)
        btnPrev= findViewById(R.id.btnPrev)
        btnPause= findViewById(R.id.btnPause)
        btnNext = findViewById(R.id.btnNext)
        btnLoop = findViewById(R.id.btnLoop)
        tiempoTotal = findViewById(R.id.tiempoTotal)
        tiempoLlevamos = findViewById(R.id.tiempoLlevamos)
        seekBar = findViewById(R.id.seekBar)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inicializarVistas()

        // Lista de canciones
        canciones = listOf(
            Cancion(R.drawable.amaral,R.raw.amaral , "Marta, Sebas, Guille y los demás","Amaral"),
            Cancion(R.drawable.cd,R.raw.cd, "No tan jóvenes", "Carolina durante"),
            Cancion(R.drawable.im,R.raw.im, "2 minutes to midnight", "Iron Maiden"),
            Cancion(R.drawable.tenso,R.raw.tenso, "Que tenso, no?", "Tenso"),
        )

        // Inicializar MediaPlayer con la primera canción
        mediaPlayer = MediaPlayer.create(this, canciones[cancionActual].audio)
        actualizarCancion()

        // Botón Anterior
        btnPrev.setOnClickListener {
            // TODO 3: Gestionar el botón de atrás.
        }

        // Botón Pausa/Reanudar
        btnPause.setOnClickListener {
            // TODO 1: Si la música está sonando, pausarla. Si no, reaundarla (start())
        }

        // Botón Siguiente
        btnNext.setOnClickListener {
            // TODO 2: Gestionar el botón de siguiente canción.
        }

        // Botón Bucle
        btnLoop.setOnClickListener {
            // Esta os la doy hecha ;)
            mediaPlayer.isLooping = !mediaPlayer.isLooping
            Toast.makeText(
                this,
                if (mediaPlayer.isLooping) "Bucle activado" else "Bucle desactivado",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Listener del SeekBar para cambiar el progreso de la canción
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // TODO 7: Si el usuario ha modificado el seekbar...

                    // TODO 7:...habrá que coger la posición del seekbar, y mover el audio allá.
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        gestionarBarra()
    }

    // Métofo onDestroy, liberamos recursos.
    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
    }


    private fun gestionarBarra() {
        handler.post(object : Runnable {
            override fun run() {
                    // TODO 6: Este hilo se lanza cada segundo...

                    // TODO 6: ... Y tiene que gestionar la posición del seekBar.
                    handler.postDelayed(this, 1000) // Actualiza cada segundo
                }
        })
    }
}
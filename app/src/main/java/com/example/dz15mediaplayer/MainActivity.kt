package com.example.dz15mediaplayer

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.dz15mediaplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var toolbarMain: Toolbar
    private lateinit var binding: ActivityMainBinding
    private var mediaPlayer: MediaPlayer? = null
    private var songList = mutableListOf(
        R.raw.dvaokeana,
        R.raw.potomy,
        R.raw.shatunov
    )
    private var countSound:Int=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Инициализация Тулбар
        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = " Плеер"
        toolbarMain.subtitle = " Вер.1.Главная страница"
        toolbarMain.setLogo(R.drawable.pleer)

        //начали
        pleySaund(songList[countSound])

    }

    private fun pleySaund(sound: Int) {
        binding.pleyButtonFAB.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, sound)
                initializeSeekBar()
            }
            mediaPlayer?.start()
            Toast.makeText(
                applicationContext, "Выбрана мелодия ${countSound} идет Воспроизведение",
                Toast.LENGTH_LONG
            ).show()
        }
        binding.pauseButtonFAB.setOnClickListener {
            if (mediaPlayer != null) {
                mediaPlayer?.pause()
            }
        }
        binding.stopButtonFAB.setOnClickListener {
            if (mediaPlayer != null) {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }
        binding.forwardButtonFAB.setOnClickListener{
            if (mediaPlayer != null) {
                countSound=countSound+1
                if (countSound>songList.size)countSound=(songList.size-1)
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
                Toast.makeText(
                    applicationContext, "Выбрана мелодия ${countSound}, нажмите Воспроизведение",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.backButtonFAB.setOnClickListener(){
            if (mediaPlayer != null) {
                countSound=countSound-1
                if (countSound<0)countSound=0
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
                mediaPlayer = null
                Toast.makeText(
                    applicationContext, "Выбрана мелодия ${countSound}, нажмите Воспроизведение",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        binding.seekbarSB.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               if (fromUser)mediaPlayer?.seekTo(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }


        })
    }

    private fun initializeSeekBar() {
        binding.seekbarSB.max = mediaPlayer!!.duration
        var handler = Handler()
        handler.postDelayed(object : Runnable{
            override fun run() {
              try {
                  binding.seekbarSB.progress = mediaPlayer!!.currentPosition
                  handler.postDelayed(this,1000)
              } catch (e:Exception){
                  binding.seekbarSB.progress=0
              }
            }
        },0)
    }
    //Инициализация Меню
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.infoMenuMain -> {
                Toast.makeText(
                    applicationContext, "Автор Ефремов О.В. Создан 5.12.2024",
                    Toast.LENGTH_LONG
                ).show()
            }

            R.id.exitMenuMain -> {
                Toast.makeText(
                    applicationContext, "Работа приложения завершена",
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
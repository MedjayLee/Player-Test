package com.example.playertest

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var runnable: Runnable
    private var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val seekbar = findViewById<SeekBar>(R.id.seekbar)
        val prev_btn = findViewById<ImageButton>(R.id.prev_btn)
        val play_btn = findViewById<ImageButton>(R.id.play_btn)
        val next_btn = findViewById<ImageButton>(R.id.next_btn)
        val cover = findViewById<ImageView>(R.id.cover)
        val title = findViewById<TextView>(R.id.title)

        val playlist = mutableListOf<Song>()
        var songNumber = 0

        val song1 = Song(R.raw.audio, R.drawable.cover, "Тренировочный день", "Markul ft. Kyok")
        val song2 = Song(R.raw.music, R.drawable.release, "Отпускай", "Три дня дождя")

        playlist.add(0, song1)
        playlist.add(1, song2)

        var mediaPlayer = MediaPlayer.create(this, playlist[songNumber].source)
        title.text = "${playlist[songNumber].artist} - ${playlist[songNumber].name}"





//        PREV BUTTON
        prev_btn.setOnClickListener{
            if (mediaPlayer!=null) play_btn.setImageResource(R.drawable.pause)

            if (songNumber > 0) songNumber -= 1

            if (mediaPlayer.isPlaying) mediaPlayer.stop()

            mediaPlayer = MediaPlayer.create(this, playlist[songNumber].source)

            cover.setImageResource(playlist[songNumber].cover)
            title.text = "${playlist[songNumber].artist} - ${playlist[songNumber].name}"

            mediaPlayer.start()
        }

//        PLAY BUTTON
        play_btn.setOnClickListener{
            if (!mediaPlayer.isPlaying){
                mediaPlayer.start()
                play_btn.setImageResource(R.drawable.pause)
            }else{
                mediaPlayer.pause()
                play_btn.setImageResource(R.drawable.play)
            }
        }

//        NEXT BUTTON
        next_btn.setOnClickListener{
            if (mediaPlayer!=null) play_btn.setImageResource(R.drawable.pause)

            if (songNumber < playlist.size-1) songNumber++

            if (mediaPlayer.isPlaying) mediaPlayer.stop()

            mediaPlayer = MediaPlayer.create(this, playlist[songNumber].source)

            cover.setImageResource((playlist[songNumber]).cover)
            title.text = "${playlist[songNumber].artist} - ${playlist[songNumber].name}"

            mediaPlayer.start()
        }



        seekbar.progress = 0
        seekbar.max = mediaPlayer.duration




        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                if (changed){
                    mediaPlayer.seekTo(pos)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })

        runnable = Runnable {
            seekbar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)

        mediaPlayer.setOnCompletionListener {
            if (songNumber < playlist.size-1){
                songNumber++

                if (mediaPlayer.isPlaying) mediaPlayer.stop()

                mediaPlayer = MediaPlayer.create(this, playlist[songNumber].source)

                cover.setImageResource((playlist[songNumber]).cover)
                title.text = "${playlist[songNumber].artist} - ${playlist[songNumber].name}"

                mediaPlayer.start()
            }else{
                songNumber = 0

                if (mediaPlayer.isPlaying) mediaPlayer.stop()

                mediaPlayer = MediaPlayer.create(this, playlist[songNumber].source)

                cover.setImageResource((playlist[songNumber]).cover)
                title.text = "${playlist[songNumber].artist} - ${playlist[songNumber].name}"

                mediaPlayer.start()
            }
        }
    }
}
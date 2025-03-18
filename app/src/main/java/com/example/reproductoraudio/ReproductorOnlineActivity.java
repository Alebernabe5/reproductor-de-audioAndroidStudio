package com.example.reproductoraudio;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class ReproductorOnlineActivity extends AppCompatActivity {

    protected TextView texto1, texto2;
    protected ImageButton botonPause, botonPlay, botonStop;
    protected MediaPlayer mp;
    protected float milisegundo = 0;

    protected String urlAudio = "https://cdn.pixabay.com/download/audio/2024/07/24/audio_5ec636ca14.mp3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reproductor_online);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texto1 = (TextView) findViewById(R.id.texto1_online);
        texto2 = (TextView) findViewById(R.id.texto2_online);
        botonPause = (ImageButton) findViewById(R.id.imabotonPause_online);
        botonPlay = (ImageButton) findViewById(R.id.imabotonPlay_online);
        botonStop = (ImageButton) findViewById(R.id.imabotonStop_online);

        botonPause.setVisibility(View.GONE);
        botonStop.setVisibility(View.GONE);

        //BOTON PLAY
        botonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //play

                if(milisegundo>0){

                    mp.start();
                    botonPause.setVisibility(View.VISIBLE);
                    botonStop.setVisibility(View.VISIBLE);
                } else if (milisegundo<0) {

                    //Stop

                    mp.prepareAsync();  //hasta que no este el buffer relleno no se reproduce
                    texto2.setText("Cargando...");

                    //Cuando el buffer este relleno, empiezo
                    mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mp.start();
                            texto2.setText("");
                            botonPause.setVisibility(View.VISIBLE);
                            botonStop.setVisibility(View.VISIBLE);
                        }
                    });

                } else {


                    try {
                        mp = new MediaPlayer();
                        mp.setDataSource(urlAudio);
                        mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mp.prepareAsync();  //hasta que no este el buffer relleno no se reproduce
                        texto2.setText("Cargando...");

                        //Cuando el buffer este relleno, empiezo
                        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mp.start();
                                texto2.setText("");
                                botonPause.setVisibility(View.VISIBLE);
                                botonStop.setVisibility(View.VISIBLE);
                            }
                        });


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });

        //BOTON PAUSE
        botonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Pause
                milisegundo = mp.getCurrentPosition();
                mp.pause();

                botonPause.setVisibility(View.GONE);

            }
        });

        //BOTON STOP
        botonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                milisegundo=-1;
                mp.stop();
                botonPause.setVisibility(View.GONE);
                botonStop.setVisibility(View.GONE);


            }
        });


    }
}
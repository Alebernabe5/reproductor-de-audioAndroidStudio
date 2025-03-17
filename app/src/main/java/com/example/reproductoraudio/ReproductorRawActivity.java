package com.example.reproductoraudio;

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

public class ReproductorRawActivity extends AppCompatActivity {

    protected TextView texto1;
    protected ImageButton botonPause, botonPlay, botonStop;
    protected MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reproductor_raw);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texto1 = (TextView) findViewById(R.id.texto1_raw);
        botonPause = (ImageButton) findViewById(R.id.imabotonPause_raw);
        botonPlay = (ImageButton) findViewById(R.id.imabotonPlay_raw);
        botonStop = (ImageButton) findViewById(R.id.imabotonStop_raw);

        //BOTON PLAY
        botonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mp = MediaPlayer.create(ReproductorRawActivity.this, R.raw.cancion1);
                mp.start();;
            }
        });

        //BOTON PAUSE
        botonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //BOTON STOP
        botonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
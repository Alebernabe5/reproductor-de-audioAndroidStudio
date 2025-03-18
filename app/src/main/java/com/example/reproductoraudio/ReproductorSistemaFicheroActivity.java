package com.example.reproductoraudio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;

public class ReproductorSistemaFicheroActivity extends AppCompatActivity {

    protected TextView texto1, texto2;
    protected ImageButton botonPause, botonPlay, botonStop;
    protected MediaPlayer mp;
    protected float milisegundo = 0;
    protected String rutaCarpetaAudio = "";

    public void checkPermission(String permission, int requeestCode)
    {
        //Si no tenemos permiso
        if (ContextCompat.checkSelfPermission(ReproductorSistemaFicheroActivity.this, permission)== PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(ReproductorSistemaFicheroActivity.this, new String [] {permission},requeestCode);
        }
        //Si tenemos permiso
        else {
            Toast.makeText(this, "Ya tenemos permisos del usuario", Toast.LENGTH_SHORT).show();
            botonPlay.setVisibility(View.VISIBLE);
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reproductor_sistema_fichero);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texto1 = (TextView) findViewById(R.id.texto1_sistemaFicheros);
        texto2 = (TextView) findViewById(R.id.texto2_sistemaFicheros);
        botonPause = (ImageButton) findViewById(R.id.imabotonPause_sistemaFicheros);
        botonPlay = (ImageButton) findViewById(R.id.imabotonPlay_sistemaFicheros);
        botonStop = (ImageButton) findViewById(R.id.imabotonStop_sistemaFicheros);

        botonPause.setVisibility(View.GONE);
        botonStop.setVisibility(View.GONE);
        botonPlay.setVisibility(View.GONE);

        rutaCarpetaAudio = Environment.getExternalStorageDirectory().getPath() + "/Download/cancion3.mp3";
        File f = new File(rutaCarpetaAudio);

        if (!f.exists()) {
            Toast.makeText(this, "No se puede reproducir el fichero", Toast.LENGTH_SHORT).show();
            botonPlay.setVisibility(View.GONE);
            botonPause.setVisibility(View.GONE);
            botonStop.setVisibility(View.GONE);
            texto2.setText("No se puede reproducir el fichero MP3");
        } else {
            texto2.setText(rutaCarpetaAudio);
        }

        //chequea los permisos
        checkPermission(Manifest.permission.READ_MEDIA_AUDIO, 100);


            //BOTON PLAY
            botonPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //play

                    if (milisegundo>0) {
                        mp.start();
                    } else if (milisegundo<0)
                    {
                        try {
                            mp.prepare();//Al obtener el fichero no hay conexion de por medio.
                            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {

                                    mp.start();
                                }
                            });
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    } else{

                        try {
                            mp = new MediaPlayer();
                            mp.setDataSource(rutaCarpetaAudio);
                            mp.prepare();//Al obtener el fichero no hay conexion de por medio.

                            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mediaPlayer) {

                                    mp.start();
                                }
                            });


                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }


                    botonPause.setVisibility(View.VISIBLE);
                    botonStop.setVisibility(View.VISIBLE);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);

        if(requestCode==100){
        if(grantResults.length>0 && grantResults [0]==PackageManager.PERMISSION_GRANTED)
            {
                botonPlay.setVisibility(View.VISIBLE);
            }
        else {
            Toast.makeText(this, "No ha dado permiso para acceder al sistema de ficheros", Toast.LENGTH_SHORT).show();
        }

        }
    }
}


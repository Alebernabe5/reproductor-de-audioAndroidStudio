package com.example.reproductoraudio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    protected TextView texto1;

    protected Button boton1, boton2, boton3;

    protected Intent pasarPantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texto1 = (TextView) findViewById(R.id.texto1_main);
        boton1 = (Button) findViewById(R.id.boton1_main);
        boton2 = (Button) findViewById(R.id.boton2_main);
        boton3 = (Button) findViewById(R.id.boton3_main);

        //Boton reproducir audio RAW
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pasarPantalla = new Intent(MainActivity.this, ReproductorRawActivity.class);
                startActivity(pasarPantalla);
            }
        });

        //Boton reproducir audio online
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pasarPantalla = new Intent(MainActivity.this, ReproductorOnlineActivity.class);
                startActivity(pasarPantalla);
            }
        });

        //Boton reproducir audio sistema de ficheros
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pasarPantalla = new Intent(MainActivity.this, ReproductorSistemaFicheroActivity.class);
                startActivity(pasarPantalla);
            }
        });


    }
}
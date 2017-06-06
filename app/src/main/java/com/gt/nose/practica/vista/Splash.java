package com.gt.nose.practica.vista;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gt.nose.practica.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        getSupportActionBar().hide();

        Thread pantalla = new Thread(){

            @Override
            public void run() {

                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intent pantalla = new Intent(Splash.this, Menu.class);
                    startActivity(pantalla);
                }

            }
        };

        pantalla.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        finish();
    }

}

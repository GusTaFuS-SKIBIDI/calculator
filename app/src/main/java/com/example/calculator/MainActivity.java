
package com.example.calculator;

import android.os.Bundle;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button buttonMatroshka = findViewById(R.id.button_matroshka);
        Button buttonColor = findViewById(R.id.button_color);

        Button rightButton = findViewById(R.id.right_button);
        Button exitButton = findViewById(R.id.exit_button);


        buttonMatroshka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MatreshkaActivity.class);
                startActivity(intent);
            }
        });


        buttonColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, mathematicsActivity.class);
                startActivity(intent);
            }
        });




        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "Нажата правая кнопка", Toast.LENGTH_SHORT).show();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finishAffinity();
            }
        });
    }
}
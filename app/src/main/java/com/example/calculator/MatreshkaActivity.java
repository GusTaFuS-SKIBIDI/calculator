package com.example.calculator;

import android.content.Intent; // Импорт для переходов между активностями
import android.os.Bundle; // Импорт для класса Bundle
import android.view.View; // Импорт для работы с View
import android.widget.Button; // Импорт для работы с кнопками

import androidx.appcompat.app.AppCompatActivity; // Импорт для базового класса активности

public class MatreshkaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matreshka); // Устанавливаем макет активности

        // Находим кнопку "Назад" по её ID
        Button backButton = findViewById(R.id.back_button);

        // Устанавливаем обработчик нажатия для кнопки "Назад"
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на MainActivity
                Intent intent = new Intent(MatreshkaActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Закрываем текущую активность, чтобы не возвращаться по кнопке "назад"
            }
        });

        // Находим кнопку "Приступить" по её ID
        Button startButton = findViewById(R.id.btn_start);

        // Устанавливаем обработчик нажатия для кнопки "Приступить"
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на MatreshkaGameActivity
                Intent intent = new Intent(MatreshkaActivity.this, MatreshkaGameActivity.class);
                startActivity(intent);
            }
        });
    }
}
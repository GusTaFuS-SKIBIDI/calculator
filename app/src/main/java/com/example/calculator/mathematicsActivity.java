package com.example.calculator;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class mathematicsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathematics);

        // Находим кнопку btn_start по ID
        Button btnStart = findViewById(R.id.btn_start);
        // Находим кнопку back_button по ID
        Button backButton = findViewById(R.id.back_button);

        // Устанавливаем обработчик для кнопки "btn_start"
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на mathematicsGameActivity
                Intent intent = new Intent(mathematicsActivity.this, mathematicsGameActivity.class);
                startActivity(intent);
            }
        });

        // Устанавливаем обработчик для кнопки "back_button"
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на MainActivity
                Intent intent = new Intent(mathematicsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

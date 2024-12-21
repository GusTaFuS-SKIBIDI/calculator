package com.example.calculator;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;  // Добавляем импорт для LinearLayout
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class mathematicsGameActivity extends AppCompatActivity {

    private Button startButton, nextButton, backButton;
    private EditText numberUpToInput, secondsInput, additionalEditText;
    private TextView numberDisplay, counterDisplay, timerDisplay, sumNumberDisplay, secondTextView;
    private int currentNumberIndex = 0;
    private int count = 0;
    private int maxNumbers = 10;
    private List<Integer> randomNumbers = new ArrayList<>();
    private int seconds = 5; // Таймер по умолчанию на 5 секунд
    private Handler timerHandler = new Handler(); // Handler для обновления таймера
    private Runnable timerRunnable;
    private boolean isTimerRunning = false;
    private int sum = 0; // Переменная для хранения суммы чисел

    // Элементы для additional_layout и result_layout
    private LinearLayout additionalLayout, resultLayout;  // Используем LinearLayout
    private Button additionalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mathematics_game);

        // Инициализация стандартных элементов
        startButton = findViewById(R.id.start_button);
        nextButton = findViewById(R.id.next_button);
        backButton = findViewById(R.id.back_button);
        numberDisplay = findViewById(R.id.number_display);
        counterDisplay = findViewById(R.id.counter_display);
        timerDisplay = findViewById(R.id.timer_display); // Добавляем таймер
        sumNumberDisplay = findViewById(R.id.SumNumber); // Добавляем TextView для суммы
        numberUpToInput = findViewById(R.id.number_up_to_input);
        secondsInput = findViewById(R.id.seconds_input);

        // Инициализация элементов для additional_layout и result_layout
        additionalEditText = findViewById(R.id.additional_edit_text);
        additionalButton = findViewById(R.id.additional_button);
        additionalLayout = findViewById(R.id.additional_layout);  // Инициализация LinearLayout
        resultLayout = findViewById(R.id.result_layout);  // Инициализация LinearLayout
        secondTextView = findViewById(R.id.secondTextView);

        // Скрываем resultLayout при старте
        resultLayout.setVisibility(View.GONE);

        // Обработчик нажатия на кнопку "Начать"
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberUpToStr = numberUpToInput.getText().toString();
                String secondsStr = secondsInput.getText().toString();

                if (numberUpToStr.isEmpty() || secondsStr.isEmpty()) {
                    Toast.makeText(mathematicsGameActivity.this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                maxNumbers = Integer.parseInt(numberUpToStr);
                seconds = Integer.parseInt(secondsStr); // Получаем значение для секунд

                generateRandomNumbers(maxNumbers); // Генерация случайных чисел

                // Скрываем ввод и показываем игру
                findViewById(R.id.input_layout).setVisibility(View.GONE);
                findViewById(R.id.game_layout).setVisibility(View.VISIBLE);

                // Запускаем таймер
                startTimer();
                showNextNumber(); // Показываем первое число
            }
        });

        // Обработчик нажатия на кнопку "Следующее"
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNumberIndex < randomNumbers.size()) {
                    showNextNumber(); // Показываем следующее число
                    restartTimer(); // Сбрасываем таймер на введенное значение секунд
                } else {
                    // Игра завершена
                    endGame();
                }
            }
        });

        // Обработчик нажатия на кнопку "Назад"
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Закрывает текущую активность и возвращает на предыдущую
            }
        });

        // Обработчик нажатия на кнопку из additional_layout
        additionalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String additionalText = additionalEditText.getText().toString();

                if (additionalText.isEmpty()) {
                    Toast.makeText(mathematicsGameActivity.this, "Введите число", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Скрываем additional_layout
                additionalLayout.setVisibility(View.GONE);

                // Показываем result_layout
                resultLayout.setVisibility(View.VISIBLE);

                // Устанавливаем введенное число в secondTextView
                secondTextView.setText("Введенное число: " + additionalText);
            }
        });
    }

    // Генерация случайных чисел
    private void generateRandomNumbers(int count) {
        randomNumbers.clear();
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            randomNumbers.add(random.nextInt(10) + 1); // Числа от 1 до 10
        }
    }

    // Показываем следующее число
    private void showNextNumber() {
        if (currentNumberIndex < randomNumbers.size()) {
            numberDisplay.setText(String.valueOf(randomNumbers.get(currentNumberIndex)));
            counterDisplay.setText((currentNumberIndex + 1) + "/" + maxNumbers);
            currentNumberIndex++; // Увеличиваем индекс для следующего числа
        }
    }

    // Запуск таймера
    private void startTimer() {
        timerDisplay.setText("Таймер: " + seconds);
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (seconds > 0) {
                    timerDisplay.setText("Таймер: " + seconds);
                    seconds--;
                    timerHandler.postDelayed(this, 1000); // Обновляем каждую секунду
                } else {
                    timerDisplay.setText("Таймер: 0");
                    // Когда таймер заканчивается, автоматически срабатывает кнопка "Следующее"
                    nextButton.performClick(); // Программный клик на кнопку "Следующее"
                }
            }
        };
        isTimerRunning = true;
        timerHandler.postDelayed(timerRunnable, 1000); // Запускаем таймер
    }

    // Сбрасываем таймер при нажатии кнопки "Следующее"
    private void restartTimer() {
        if (isTimerRunning) {
            timerHandler.removeCallbacks(timerRunnable); // Убираем старый таймер
        }
        seconds = Integer.parseInt(secondsInput.getText().toString()); // Считываем новые секунды
        startTimer(); // Запускаем новый таймер
    }

    // Завершение игры и вывод суммы
    public void endGame() {
        // Подсчитываем сумму всех чисел
        sum = 0;
        for (int number : randomNumbers) {
            sum += number;
        }

        // Прячем игровую разметку
        findViewById(R.id.game_layout).setVisibility(View.GONE);

        // Показываем сумму чисел в SumNumber (если нужно, можно просто обновить текст)
        sumNumberDisplay.setText("Сумма чисел: " + sum);

        // Показываем additional_layout (это если надо показывать дополнительную информацию)
        findViewById(R.id.additional_layout).setVisibility(View.VISIBLE);
    }
}

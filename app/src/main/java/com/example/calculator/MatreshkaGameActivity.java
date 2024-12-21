package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MatreshkaGameActivity extends AppCompatActivity {

    private TextView wordText, timerText, wordsRememberedText, resultText;
    private Button nextWordButton, backButton;
    private LinearLayout buttonsContainer;
    private Button option1, option2, option3, option4;
    private List<String> words;
    private List<String> rememberedWords = new ArrayList<>();
    private List<String> selectedWords = new ArrayList<>(); // Слова, выбранные через кнопки
    private int currentWordIndex = 0;
    private int timeLeft = 10; // Начальное время (10 секунд)
    private Handler handler;
    private Runnable showNextWord;
    private Runnable updateTimer;
    private int buttonClickCount = 0; // Счётчик нажатий кнопок
    private int correctGuesses = 0; // Счётчик угаданных слов

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matreshka_game);

        // Инициализация кнопок и текстовых полей
        wordText = findViewById(R.id.word_text);
        timerText = findViewById(R.id.timerText);
        wordsRememberedText = findViewById(R.id.wordsRememberedText);
        resultText = findViewById(R.id.resultText); // TextView для результата

        nextWordButton = findViewById(R.id.next_word);
        backButton = findViewById(R.id.back_button);

        // Инициализация контейнера кнопок и самих кнопок
        buttonsContainer = findViewById(R.id.buttonsContainer);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        // Скрываем кнопки изначально
        buttonsContainer.setVisibility(View.GONE);

        // Скрываем результат TextView по умолчанию
        resultText.setVisibility(View.GONE);

        // Инициализация списка слов (увеличен до 100)
        words = new ArrayList<>();
        Collections.addAll(words,
                "Слон", "Книга", "Дерево", "Кот", "Дом", "Река", "Гора", "Лес", "Машина", "Ручка",
                "Собака", "Птица", "Рыба", "Кофе", "Телефон", "Автомобиль", "Карандаш", "Тетрадь", "Компьютер", "Телевизор",
                "Папа", "Мама", "Сестра", "Брат", "Тетя", "Дядя", "Дядюшка", "Таблица", "Автобус", "Самолет",
                "Собака", "Скейт", "Гитара", "Свет", "Тьма", "Зима", "Лето", "Осень", "Весна", "Зарядка",
                "Урок", "Класс", "Лавка", "Окно", "Дверь", "Лампа", "Картинка", "Ручка", "Цветок", "Картинка",
                "Фильм", "Кино", "Гардероб", "Рюкзак", "Папа", "Мама", "Шкаф", "Подушка", "Покрывало", "Тапочки",
                "Стул", "Коврик", "Скульптура", "Молоко", "Хлеб", "Пирог", "Чай", "Кофе", "Леденец", "Шоколад",
                "Вода", "Молоко", "Кефир", "Вино", "Сок", "Компот", "Печенье", "Конфеты", "Кекс", "Торт",
                "Реальность", "Мечта", "Сказка", "Реальность", "Космос", "Звезда", "Туман", "Луна", "Планета",
                "Звезды", "Галактика", "Черная дыра", "Космонавт", "Космодром", "Гравитация", "Астрономия", "Теория",
                "Космология", "Телескоп", "Луна", "Марс", "Юпитер", "Млечный путь", "Корабль", "Зонд", "Туманность",
                "Тропики", "Полярный круг", "Арктика", "Антарктида", "Тундра", "Саванна", "Горы", "Пустыня", "Печка",
                "Гроза", "Торнадо", "Цунами", "Ураган", "Лавина", "Снежинка", "Молния", "Гроза", "Радуга", "Цветы"
        );
        Collections.shuffle(words);

        // Обработчик нажатия для кнопки "Следующий"
        nextWordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWordManually();
            }
        });

        // Обработчик нажатия для кнопки "Назад"
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Очистить все списки, кроме words
                rememberedWords.clear();
                selectedWords.clear(); // Очистить выбранные слова
                currentWordIndex = 0;
                buttonClickCount = 0;
                correctGuesses = 0; // Сбросить количество угаданных слов

                // Переход к предыдущей активности
                Intent intent = new Intent(MatreshkaGameActivity.this, MatreshkaActivity.class);
                startActivity(intent);
                finish(); // Закрываем текущую активность
            }
        });

        // Обновление таймера каждую секунду
        handler = new Handler();
        updateTimer = new Runnable() {
            @Override
            public void run() {
                if (timeLeft > 0) {
                    timeLeft--;
                    timerText.setText(timeLeft + " секунд");
                    handler.postDelayed(this, 1000); // Каждую секунду
                } else {
                    // Когда время заканчивается, показываем следующее слово
                    showWord();
                    timeLeft = 10; // Сбросить таймер
                }
            }
        };

        // Показ слов каждые 10 секунд автоматически
        showNextWord = new Runnable() {
            @Override
            public void run() {
                showWord();
                handler.postDelayed(this, 10000); // Каждые 10 секунд
            }
        };
        handler.post(showNextWord);

        // Добавляем обработчики нажатий для кнопок
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(option1);
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(option2);
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(option3);
            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleButtonClick(option4);
            }
        });
    }

    private void handleButtonClick(Button button) {
        if (buttonClickCount < 10) {
            // Слово, выбранное пользователем, заносится в список selectedWords
            selectedWords.add(button.getText().toString());
            buttonClickCount++;

            // Если нажатий 10, скрываем кнопки и показываем результат
            if (buttonClickCount >= 10) {
                hideButtons();
                showResult();
            } else {
                // Обновляем кнопки с новыми словами
                updateButtons();
            }
        }
    }

    private void showWord() {
        if (currentWordIndex < 10) { // Показываем только первые 10 слов
            wordText.setText(words.get(currentWordIndex));
            rememberedWords.add(words.get(currentWordIndex)); // Добавляем слово в список запомненных
            currentWordIndex++;
            timeLeft = 10; // Сбросить таймер на 10 секунд
            handler.removeCallbacks(updateTimer);
            handler.post(updateTimer);

            // Обновляем текст о количестве запомненных слов
            wordsRememberedText.setText(rememberedWords.size() + "/10");

        } else {
            // Когда все слова показаны, скрываем все элементы
            wordText.setVisibility(View.GONE);
            timerText.setVisibility(View.GONE);
            wordsRememberedText.setVisibility(View.GONE);
            nextWordButton.setVisibility(View.GONE);

            // Появление кнопок
            buttonsContainer.setVisibility(View.VISIBLE);

            // Обновляем текст на кнопках
            updateButtons();
        }
    }

    private void showWordManually() {
        handler.removeCallbacks(showNextWord);
        showWord();
    }

    private void updateButtons() {
        // Очищаем кнопки
        List<String> buttonWords = new ArrayList<>();
        // Выбираем одно запомненное слово для одной кнопки
        String correctWord = rememberedWords.get(buttonClickCount);

        // Добавляем правильное слово в список для случайной кнопки
        buttonWords.add(correctWord);

        // Добавляем 3 случайных слова из общего списка для других кнопок
        List<String> otherWords = new ArrayList<>(words);
        Collections.shuffle(otherWords);
        otherWords.removeAll(buttonWords);
        buttonWords.add(otherWords.get(0));
        buttonWords.add(otherWords.get(1));
        buttonWords.add(otherWords.get(2));

        // Перемешиваем список слов для кнопок
        Collections.shuffle(buttonWords);

        // Назначаем текст для кнопок
        option1.setText(buttonWords.get(0));
        option2.setText(buttonWords.get(1));
        option3.setText(buttonWords.get(2));
        option4.setText(buttonWords.get(3));
    }

    private void hideButtons() {
        buttonsContainer.setVisibility(View.GONE);
    }

    private void showResult() {
        resultText.setVisibility(View.VISIBLE);
        int correctCount = 0;
        for (String word : selectedWords) {
            if (rememberedWords.contains(word)) {
                correctCount++;
            }
        }
        resultText.setText("Вы угадали " + correctCount + " из 10 слов.");
    }
}

package me.jorlowski.quiz_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private TextView questionCounter;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button resetButton;
    private int currentIndex;
    private int score;

    private final Question[] questions = new Question[] {
            new Question(R.string.q_planets, true),
            new Question(R.string.q_maths, false),
            new Question(R.string.q_war, false),
            new Question(R.string.q_temp, true),
            new Question(R.string.q_skin, true)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupAll();
    }

    private void setNextQuestion() {
        if (currentIndex + 1 == questions.length) {
            onFinal();
        } else {
            currentIndex++;
            questionTextView.setText(questions[currentIndex].getQuestionId());
        }
    }

    private void onFinal() {
        setContentView(R.layout.activity_end);
        questionCounter = findViewById(R.id.question_counter);
        questionCounter.setText(Integer.toString(score));
        resetButton = findViewById(R.id.reset_button);
        resetButton.setOnClickListener(v -> {
            setContentView(R.layout.activity_main);
            setupAll();
        });
    }

    private void setupAll() {
        trueButton = findViewById(R.id.true_button);
        trueButton.setOnClickListener(v -> {
            checkAnswerCorrectness(true);
            setNextQuestion();
        });
        falseButton = findViewById(R.id.false_button);
        falseButton.setOnClickListener(v -> {
            checkAnswerCorrectness(false);
            setNextQuestion();
        });
        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(v -> setNextQuestion());

        questionTextView = findViewById(R.id.question_text_view);
        currentIndex = 0;
        score = 0;
    }

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId;
        if (userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
            score++;
        } else {
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }
}
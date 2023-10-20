package me.jorlowski.quiz_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {


    public static final String KEY_EXTRA_ANSWER_SHOWN = "extraAnswerShown";
    private boolean wasAnswerShown;


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_EXTRA_ANSWER_SHOWN, wasAnswerShown);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);
        boolean correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER, true);
        TextView answerText = findViewById(R.id.podpowiedz);
        Button showHelpButton = findViewById(R.id.button_podpowiedz);
        if (savedInstanceState != null) {
            wasAnswerShown = savedInstanceState.getBoolean(KEY_EXTRA_ANSWER_SHOWN);
            if (wasAnswerShown) {
                setAnswerShownResult(true);
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerText.setText(answer);
            }
        } else {
            wasAnswerShown = false;
        }
        showHelpButton.setOnClickListener(v -> {
            wasAnswerShown = true;
            int answer = correctAnswer ? R.string.button_true : R.string.button_false;
            answerText.setText(answer);
            setAnswerShownResult(wasAnswerShown);
        });
    }

    private void setAnswerShownResult(boolean answerWasShown) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, answerWasShown);
        setResult(RESULT_OK, resultIntent);
    }
}
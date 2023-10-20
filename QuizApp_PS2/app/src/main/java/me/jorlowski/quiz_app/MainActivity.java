package me.jorlowski.quiz_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String QUIZ_TAG = "MyQuizTag";
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    private static final String KEY_SCORE = "score";
    private static final String KEY_LAYOUT_ID = "layoutId";
    public static final String KEY_EXTRA_ANSWER = "extraAnswer";
    private static final int REQUEST_CODE_PROMPT = 0;
    private static final String KEY_ANSWER_WAS_SHOWN = "answerShown";
    private TextView questionTextView;
    private TextView questionCounter;
    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button resetButton;
    private Button helpButton;
    private int currentIndex;
    private int layoutId;
    private int score;
    private boolean answerWasShown;

    private final Question[] questions = new Question[] {
            new Question(R.string.q_planets, true),
            new Question(R.string.q_maths, false),
            new Question(R.string.q_war, false),
            new Question(R.string.q_temp, true),
            new Question(R.string.q_skin, true)
    };


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(QUIZ_TAG, "I'm on SaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_LAYOUT_ID, layoutId);
        outState.putBoolean(KEY_ANSWER_WAS_SHOWN, answerWasShown);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(QUIZ_TAG, "I'm on Create");
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
            score = savedInstanceState.getInt(KEY_SCORE);
            layoutId = savedInstanceState.getInt(KEY_LAYOUT_ID);
            answerWasShown = savedInstanceState.getBoolean(KEY_ANSWER_WAS_SHOWN);
            setContentView(layoutId);
            setupAll(layoutId);
        } else {
            currentIndex = 0;
            score = 0;
            setMain();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(QUIZ_TAG, "I'm on Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(QUIZ_TAG, "I'm on Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(QUIZ_TAG, "I'm on Destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(QUIZ_TAG, "I'm on Pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(QUIZ_TAG, "I'm on Resume");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {return;}
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) {return;}
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    private void setMain() {
        layoutId = R.layout.activity_main;
        setContentView(layoutId);
        setupAll(layoutId);
    }

    private void setEnd() {
        layoutId = R.layout.activity_end;
        setContentView(layoutId);
        setupAll(layoutId);
    }

    private void setNextQuestion() {
        if (currentIndex + 1 == questions.length) {
            setEnd();
        } else {
            currentIndex++;
            answerWasShown = false;
            questionTextView.setText(questions[currentIndex].getQuestionId());
        }
    }

    private void setupAll(int layoutId) {
        if (layoutId == R.layout.activity_main) {
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
            helpButton = findViewById(R.id.help_button);
            helpButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, PromptActivity.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            });
            questionTextView = findViewById(R.id.question_text_view);
            questionTextView.setText(questions[currentIndex].getQuestionId());
        } else if (layoutId == R.layout.activity_end) {
            questionCounter = findViewById(R.id.question_counter);
            questionCounter.setText(Integer.toString(score));
            resetButton = findViewById(R.id.reset_button);
            resetButton.setOnClickListener(v -> {
                currentIndex = 0;
                score = 0;
                setMain();
            });
        }
    }

    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId;
        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
        } else {
            if (userAnswer == correctAnswer) {
                resultMessageId = R.string.correct_answer;
                score++;
            } else {
                resultMessageId = R.string.incorrect_answer;
            }
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }
}
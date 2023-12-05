package me.jorlowski.ps8_libraryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

public class EditBookActivity extends AppCompatActivity {
    public static final String EXTRA_EDIT_BOOK_TITLE = "editBookTitle";
    public static final String EXTRA_EDIT_BOOK_AUTHOR = "editBookAuthor";
    public static final String EXTRA_EDIT_BOOK_ID = "editBookId";

    private EditText titleEditText;
    private EditText authorEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        titleEditText = findViewById(R.id.edit_book_title);
        authorEditText = findViewById(R.id.edit_book_author);
        if (getIntent().hasExtra(EXTRA_EDIT_BOOK_TITLE)) {
            titleEditText.setText(getIntent().getStringExtra(EXTRA_EDIT_BOOK_TITLE));
        }
        if (getIntent().hasExtra(EXTRA_EDIT_BOOK_AUTHOR)) {
            authorEditText.setText(getIntent().getStringExtra(EXTRA_EDIT_BOOK_AUTHOR));
        }

        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            if (TextUtils.isEmpty(titleEditText.getText()) || TextUtils.isEmpty(authorEditText.getText())) {
                setResult(RESULT_CANCELED, intent);
            } else {
                String title = titleEditText.getText().toString();
                String author = authorEditText.getText().toString();
                intent.putExtra(EXTRA_EDIT_BOOK_ID, getIntent().getIntExtra(EXTRA_EDIT_BOOK_ID, -1));
                intent.putExtra(EXTRA_EDIT_BOOK_TITLE, title);
                intent.putExtra(EXTRA_EDIT_BOOK_AUTHOR, author);
                setResult(RESULT_OK, intent);
            }
            finish();
        });
    }
}
package me.jorlowski.ps9_booksearch;

import static me.jorlowski.ps9_booksearch.MainActivity.IMAGE_URL_BASE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookInfoActivity extends AppCompatActivity {

    private ImageView bookImage;
    private TextView bookTitle;
    private TextView bookAuthor;
    private TextView bookPublisher;
    private TextView bookDate;
    private TextView bookLang;
    private TextView bookISBN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        bookImage = findViewById(R.id.book_image);
        bookTitle = findViewById(R.id.book_title);
        bookAuthor = findViewById(R.id.book_author);
        bookPublisher = findViewById(R.id.book_publisher);
        bookDate = findViewById(R.id.book_date);
        bookLang = findViewById(R.id.book_lang);
        bookISBN = findViewById(R.id.book_isbn);
        if (getIntent().hasExtra(MainActivity.KEY_EXTRA_KEY)) {
            BookDetails book = (BookDetails) getIntent().getSerializableExtra(MainActivity.KEY_EXTRA_KEY);
            if (book != null && checkNullOrEmpty(book.getTitle()) && book.getAuthors() != null) {
                if (book.getImage() != null) {
                    Picasso.get()
                            .load(IMAGE_URL_BASE + book.getImage() + "-L.jpg")
                            .placeholder(R.drawable.baseline_book_24)
                            .into(bookImage);
                } else {
                    bookImage.setImageResource(R.drawable.baseline_book_24);
                }
                bookTitle.setText(getString(R.string.title_res, book.getTitle()));
                bookAuthor.setText(getString(R.string.author_res, TextUtils.join(", ", book.getAuthors())));
                bookPublisher.setText(getString(R.string.publisher_res, TextUtils.join(", ", book.getPublishers())));
                bookDate.setText(getString(R.string.date_res, TextUtils.join(", ", book.getDates())));
                bookLang.setText(getString(R.string.lang_res, TextUtils.join(", ", book.getLangs())));
                bookISBN.setText(getString(R.string.isbn_res, TextUtils.join(", ", book.getIsbns())));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.book_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_item_return) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean checkNullOrEmpty(String text) {
        return text != null && !TextUtils.isEmpty(text);
    }
}
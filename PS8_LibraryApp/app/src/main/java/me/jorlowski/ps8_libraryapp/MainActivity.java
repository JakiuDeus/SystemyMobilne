package me.jorlowski.ps8_libraryapp;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addButton;
    private BookViewModel bookViewModel;
    private RecyclerView recyclerView;
    private BookAdapter adapter;
    public static final int NEW_BOOK_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_BOOK_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditBookActivity.class);
            startActivityForResult(intent, NEW_BOOK_ACTIVITY_REQUEST_CODE);
        });
        recyclerView = findViewById(R.id.recyclerview);
        adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        bookViewModel.findAll().observe(this, adapter::setBooks);
        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAbsoluteAdapterPosition();
                bookViewModel.delete(adapter.books.get(pos));
                adapter.notifyItemRemoved(pos);
                Snackbar.make(findViewById(R.id.coordinator_layout), getString(R.string.book_archived), Snackbar.LENGTH_LONG).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Book book = new Book(data.getStringExtra(EditBookActivity.EXTRA_EDIT_BOOK_TITLE), data.getStringExtra(EditBookActivity.EXTRA_EDIT_BOOK_AUTHOR));
            bookViewModel.insert(book);
            Snackbar.make(findViewById(R.id.coordinator_layout), getString(R.string.book_added), Snackbar.LENGTH_LONG).show();
        } else if (requestCode == EDIT_BOOK_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            int bookId = data.getIntExtra(EditBookActivity.EXTRA_EDIT_BOOK_ID, -1);
            Book book = adapter.books.stream().filter(b -> b.getId() == bookId).findFirst().get();
            book.setTitle(data.getStringExtra(EditBookActivity.EXTRA_EDIT_BOOK_TITLE));
            book.setAuthor(data.getStringExtra(EditBookActivity.EXTRA_EDIT_BOOK_AUTHOR));
             bookViewModel.update(book);
            Snackbar.make(findViewById(R.id.coordinator_layout), getString(R.string.book_updated), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(findViewById(R.id.coordinator_layout), getString(R.string.empty_not_saved), Snackbar.LENGTH_LONG).show();
        }
    }

    private class BookHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {
        private TextView bookTitleTextView;
        private TextView bookAuthorTextView;
        private Book book;

        public BookHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.book_list_item, parent, false));
            bookTitleTextView = itemView.findViewById(R.id.book_title);
            bookAuthorTextView = itemView.findViewById(R.id.book_author);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void bind(Book book) {
            this.book = book;
            bookTitleTextView.setText(book.getTitle());
            bookAuthorTextView.setText(book.getAuthor());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, EditBookActivity.class);
            intent.putExtra(EditBookActivity.EXTRA_EDIT_BOOK_ID, book.getId());
            intent.putExtra(EditBookActivity.EXTRA_EDIT_BOOK_TITLE, book.getTitle());
            intent.putExtra(EditBookActivity.EXTRA_EDIT_BOOK_AUTHOR, book.getAuthor());
            startActivityForResult(intent, EDIT_BOOK_ACTIVITY_REQUEST_CODE);
        }

        @Override
        public boolean onLongClick(View v) {
            BookAdapter bookAdapter = (BookAdapter) getBindingAdapter();
            int pos = getAbsoluteAdapterPosition();
            bookViewModel.delete(book);
            bookAdapter.notifyItemRemoved(pos);
            Snackbar.make(findViewById(R.id.coordinator_layout), getString(R.string.book_deleted), Snackbar.LENGTH_LONG).show();
            return true;
        }
    }

    private class BookAdapter extends RecyclerView.Adapter<BookHolder> {
        private List<Book> books;

        @NonNull
        @Override
        public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookHolder(getLayoutInflater(), parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BookHolder holder, int position) {
            if (books != null) {
                Book book = books.get(position);
                holder.bind(book);
            } else {
                Log.d("MainActivity", "No books");
            }
        }

        @Override
        public int getItemCount() {
            return books != null ? books.size() : 0;
        }

        void setBooks(List<Book> books) {
            this.books = books;
            notifyDataSetChanged();
        }
    }
}
package me.jorlowski.ps9_booksearch;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookContainer {
    @SerializedName("docs")
    private List<BookDetails> bookList;

    public List<BookDetails> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookDetails> bookList) {
        this.bookList = bookList;
    }
}

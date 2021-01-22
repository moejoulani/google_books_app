package com.example.books;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.books.Model.Book;
import com.example.books.Utils.BookUtils;

import java.util.List;

public class BooksLoader extends AsyncTaskLoader<List<Book>> {
    private String requestURL;
    public BooksLoader(@NonNull Context context,String requestURL) {
        super(context);
        this.requestURL=requestURL;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {
        if(requestURL==null)
        {
            return null;
        }
        List<Book> books =BookUtils.fetchBookAPIdata(requestURL);
        return books;
    }
}

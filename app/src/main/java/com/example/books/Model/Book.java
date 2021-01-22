package com.example.books.Model;

import android.graphics.Bitmap;

public class Book {
    private String author,title;
    private String image;

    public Book(String author, String title, String image)
    {
        this.author=author;
        this.title=title;
        this.image=image;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

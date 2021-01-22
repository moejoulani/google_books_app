package com.example.books.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.books.Model.Book;
import com.example.books.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Context context, int resource, List<Book> objects) {
        super(context, resource, objects);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view =convertView;
        if(view == null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.book_item,parent,false);
        }
        Book current =getItem(position);
        TextView txtAuthor = (TextView)view.findViewById(R.id.author);
        TextView txtTitle  = (TextView)view.findViewById(R.id.title);
        ImageView imageView=(ImageView)view.findViewById(R.id.bookImage);
        Picasso.get().load(current.getImage()).placeholder(R.drawable.bookicon).into(imageView);
        txtAuthor.setText(current.getAuthor());
        txtTitle.setText(current.getTitle());


        return view;
    }
}

package com.example.books;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.books.Adapters.BookAdapter;
import com.example.books.Model.Book;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private ListView listView;
    BookAdapter adapter;
    private SearchView sv;
    private String subQuery;
    private SwipeRefreshLayout sw;
    private static final int BOOKS_LOADER=1;
    private static final int BOOKS_LOADER_SEARCH=2;
    private static final String BOOK_HTTP_REQUEST = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10";
    private static final String DOMAN = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String QUERYURL = "&maxResults=10";
    private ProgressBar progressBar;
    private String qqq="";
    private TextView emptyVIewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sv=(SearchView) findViewById(R.id.topSearch);
        progressBar=(ProgressBar)findViewById(R.id.progress_circular);
        emptyVIewText=(TextView)findViewById(R.id.empty);
        sw=(SwipeRefreshLayout)findViewById(R.id.swipRef);
        sw.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSupportLoaderManager().restartLoader(BOOKS_LOADER,null,MainActivity.this);
                sw.stopNestedScroll();
                progressBar.setVisibility(View.GONE);

            }


        });


        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(MainActivity.this,"On Query Text Submit : "+sv.getQuery(),Toast.LENGTH_SHORT).show();
                subQuery=sv.getQuery().toString();
                Toast.makeText(MainActivity.this,"The APpend =  "+appenURL(subQuery),Toast.LENGTH_LONG).show();
                qqq=appenURL(subQuery);
                getSupportLoaderManager().restartLoader(BOOKS_LOADER_SEARCH,null,MainActivity.this);
                return false;
        }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        listView=(ListView)findViewById(R.id.listView);
         adapter=new BookAdapter(this,0,new ArrayList<Book>());
        listView.setAdapter(adapter);
        listView.setEmptyView(emptyVIewText);

        ConnectivityManager connMGR = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMGR.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected())
        {
            getSupportLoaderManager().initLoader(BOOKS_LOADER,null,this);
        }
        else{
            progressBar.setVisibility(View.GONE);
            emptyVIewText.setText(R.string.no_internet);

        }
    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
            progressBar.setVisibility(View.VISIBLE);
        if(id == BOOKS_LOADER)
        {
            return new BooksLoader(this,BOOK_HTTP_REQUEST);
        }
        else if(id==BOOKS_LOADER_SEARCH)
        {
            return new BooksLoader(this,qqq);
        }
        return null;
        }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {
        progressBar.setVisibility(View.GONE);

        sw.setRefreshing(false);
        emptyVIewText.setText(R.string.empty_view_text);
        adapter.clear();
        if(data !=null && !data.isEmpty())
        {
            adapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
        adapter.clear();
    }

    private String appenURL(String s)
    {
        String URL_RQUEST  = "";
        URL_RQUEST=DOMAN+s+QUERYURL;
        return URL_RQUEST;
    }


}

package com.example.books.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.ScriptGroup;
import android.text.TextUtils;
import android.util.Log;

import com.example.books.Model.Book;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class BookUtils {


    public static List<Book> fetchBookAPIdata(String requestURL)
    {
        String jsonResponse = null;
        URL url =createURL(requestURL);
        try {
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e)
        {

        }
        List<Book> list =extractJSONData(jsonResponse);
        return list;

    }
    private static List<Book> extractJSONData(String jsonResponse)
    {
        if(TextUtils.isEmpty(jsonResponse))
        {
            return null;
        }
        List<Book> books =new ArrayList<>();

        try {
            JSONObject baseObject= new JSONObject(jsonResponse);
            JSONArray items = baseObject.getJSONArray("items");
            for(int i=0;i<items.length();i++)
            {
                JSONObject current=items.getJSONObject(i);
                JSONObject volumeInfo =current.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                String author = volumeInfo.getJSONArray("authors").get(0).toString();
                String imageURL = volumeInfo.getJSONObject("imageLinks").getString("smallThumbnail");

                Bitmap image = getBitmapFromURL(imageURL);
                Log.e("link json image : ",imageURL);

                books.add(new Book(author,title,imageURL));
            }
        }
        catch (JSONException e)
        {
            Log.e("jsonerror :",e.getMessage());
        }
        return books;
    }
    private static Bitmap getBitmapFromURL(String src)
    {
       try{
           URL url =new URL(src);
           HttpURLConnection connection =(HttpURLConnection)url.openConnection();
           connection.setDoInput(true);
           connection.connect();
           InputStream inputStream =connection.getInputStream();
           Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

           return bitmap;
       }
       catch (IOException e)
       {
           Log.e("bitmap joulani",e.getMessage());
          return null;
       }

    }
    private static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = "";
        if(url == null)
        {
            return jsonResponse;
        }
        HttpURLConnection urlConnection=null;
        InputStream inputStream =null;
        try {
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(150000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200)
            {
                inputStream=urlConnection.getInputStream();
                jsonResponse=ReadFromStream(inputStream);
            }
        }
        catch (IOException e)
        {

        }
        finally {
            if(urlConnection!=null)
            {
                urlConnection.disconnect();
            }
            if(inputStream!=null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;

    }
    private static String ReadFromStream(InputStream inputStream) throws  IOException
    {
        StringBuilder output = new StringBuilder();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String line = reader.readLine();
        while(line != null)
        {
            output.append(line);
            line= reader.readLine();
        }
        return  output.toString();

    }
    private static URL createURL(String requestURL)
    {
        URL url = null;
        try {
            url = new URL(requestURL);
        }
        catch (MalformedURLException e)
        {

        }
        return  url;
    }


}

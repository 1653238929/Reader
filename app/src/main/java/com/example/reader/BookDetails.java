package com.example.reader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.reader.db.BookDetail;
import com.example.reader.db.BookName;
import com.example.reader.util.BookId;
import com.example.reader.util.DelectFile;
import com.example.reader.util.HttpUtil;
import com.example.reader.util.Utility;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class BookDetails extends AppCompatActivity {

    private int bookId;

    private String responseText;

    private ImageView imageView;

    private TextView name,author,context;

    private Button add, read;

    private String TAG ="BOOK Details中的bookid";

    private List<String> bookMallIcon;

    private List<String> bookMallName;

    private List<String> bookMallAuthor;

    private List<String> bookMallContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        initView();

        bookId = getIntent().getIntExtra("book_id",0);

        DelectFile.cleanSharedPreference(BookDetails.this);

        requestBookDetails();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String data = prefs.getString("bookMallIcon4", "");
        String data1 = prefs.getString("bookMallName4","");
        String data2 = prefs.getString("bookMallAuthor","");
        String data3 = prefs.getString("bookMallContext","");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        bookMallIcon = gson.fromJson(data, listType);
        bookMallName = gson.fromJson(data1,listType);
        bookMallAuthor = gson.fromJson(data2,listType);
        bookMallContext = gson.fromJson(data3,listType);

        if(bookMallName==null||bookMallIcon==null)
        {
            bookMallName = new ArrayList<>();
            bookMallIcon = new ArrayList<>();
            bookMallAuthor = new ArrayList<>();
            bookMallContext = new ArrayList<>();
        }
//        Log.e(TAG, "onCreate: "+bookMallName );
    }

    private void initView() {
        imageView = findViewById(R.id.book_details_image);
        name = findViewById(R.id.book_details_name);
        author = findViewById(R.id.book_details_auther);
        context = findViewById(R.id.book_details_context);
        add = findViewById(R.id.book_details_add_button);
        read = findViewById(R.id.book_details_read_button);
    }

    public void requestBookDetails()
    {
        String url = "http://106.55.148.161:8080/ireader/book/detail?bookId=" +bookId;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseText = response.body().string();
                Log.e(TAG, "onResponse: "+responseText );
                final BookDetail bookDetail = Utility.handleBookDetail(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showBookDetails(bookDetail);
                    }
                });

            }
        });
    }

    private void showBookDetails(final BookDetail bookDetails) {

        Glide.with(BookDetails.this).load(bookDetails.data.bookIcon).into(imageView);
        name.setText(bookDetails.data.bookName);
        author.setText(bookDetails.data.bookAuthor);
        context.setText("简介："+bookDetails.data.bookDescription);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookDetails.this,ReadActivity.class);
                intent.putExtra("bookName",bookDetails.data.bookName);
                startActivity(intent);
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bookMallName.contains(bookDetails.data.bookName))
                {
                    Toast.makeText(BookDetails.this,"已添加过",Toast.LENGTH_SHORT).show();
                }
                else {
                    bookMallIcon.add(bookDetails.data.bookIcon);
                    bookMallName.add(bookDetails.data.bookName);
                    bookMallAuthor.add(bookDetails.data.bookAuthor);
                    bookMallContext.add(bookDetails.data.bookDescription);
                    Gson gson = new Gson();
                    String data = gson.toJson(bookMallIcon);
                    String data1 = gson.toJson(bookMallName);
                    String data2 = gson.toJson(bookMallAuthor);
                    String data3 = gson.toJson(bookMallContext);
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(BookDetails.this).edit();
                    editor.putString("bookMallIcon4",data);
                    editor.putString("bookMallName4",data1);
                    editor.putString("bookMallAuthor",data2);
                    editor.putString("bookMallContext",data3);
                    editor.apply();
                    Toast.makeText(BookDetails.this,"已加入书架",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

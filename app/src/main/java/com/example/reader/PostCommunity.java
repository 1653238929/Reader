package com.example.reader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.reader.util.State;
import com.example.reader.util.Utility;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static okhttp3.FormBody.*;

public class PostCommunity extends AppCompatActivity {

    private Button sendButton;

    private EditText bookNameSend;

    private EditText bookCommunitySend;

    private String TAG = "PostCommunity = ";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_community);
        initview();
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkhttp();
            }
        });
    }

    private void initview() {
        sendButton = findViewById(R.id.button_sending);
        bookNameSend = findViewById(R.id.book_name_send);
        bookCommunitySend = findViewById(R.id.book_community_send);
    }

    private void sendRequestWithOkhttp()
    {
        final String bookNameSendText = bookNameSend.getText().toString();
        final String bookCommunityText = bookCommunitySend.getText().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody requestBody = new Builder()
                        .add("userId","0")
                        .add("bookName",bookNameSendText)
                        .add("postContent",bookCommunityText)
                        .build();
                Request request = new Request.Builder()
                        .url("https://106.55.148.161:8080/ireader/post/release")
                        .post(requestBody)
                        .build();

                try {
                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();
                    String responseText = response.body().string();
                    State state = Utility.handleStateResponse(responseText);
                    Log.e(TAG,"code = "+state.code);
                    if(state.code==0)
                    {
                        Toast.makeText(PostCommunity.this,"发表成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(PostCommunity.this,"发表失败",Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

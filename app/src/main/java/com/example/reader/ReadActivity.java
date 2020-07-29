package com.example.reader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reader.util.BookContext;
import com.example.reader.util.BookId;
import com.example.reader.util.Cyclephoto;
import com.example.reader.util.HttpUtil;
import com.example.reader.util.MyScrollView;
import com.example.reader.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReadActivity extends AppCompatActivity {

    private TextView bookName;

    public LinearLayout bottomMenu;

    public LinearLayout setting;

    public RelativeLayout topMenu;

    private RelativeLayout screen;

    public  boolean state = true;

    public boolean state_night = true;

    public int size = 25;

    public Button buttonBack;

    private String TAG = "tag+ = ";

    private LinearLayout catalogueLinear,cacheLinear,nightLinear,settingLinear;

    private Cyclephoto cyclePhoto1,cyclePhoto2,cyclePhoto3,cyclePhoto4,cyclePhoto5;

    private ImageView readingBackground;

    private static Drawable drawable;

    public RelativeLayout catalogue;

    private ListView catalogueListView;

    private BookId bookId;

    private String bookNameText;

    private BookContext bookContext;

    private TextView readText;

    private ArrayAdapter<String> adapter;

    private MyScrollView myscrollView;

    private Button reduceByteButton;

    private Button addByteButton;

    private TextView byteText;

    private int chapter = 0;

    private Button upChapter;

    private Button downChapter;

    private SeekBar chapterSeeBar;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initview();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//Android 状态栏消失
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int size_save = prefs.getInt("size",0);
        chapter = prefs.getInt("chapter",0);
        Log.e(TAG,"chapter == "+chapter);
        if(size_save!=0)
        {
            readText.setTextSize(size_save);
            size = size_save;
            byteText.setText(size+"");
        }
        bookNameText = getIntent().getStringExtra("bookName");
        bookName.setText(bookNameText);
        Log.e(TAG,"bookname === "+bookNameText);
        requestBookId();
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadActivity.this,MainActivity.class);
                startActivity(intent);
                state = !state;
                finish();
            }
        });
        catalogueLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                catalogue.setVisibility(View.VISIBLE);
                bottomMenu.setVisibility(View.INVISIBLE);
                topMenu.setVisibility(View.INVISIBLE);
                state = !state;
            }
        });
        cacheLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nightLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state_night)
                {
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.night_background, null);
                    readingBackground.setBackground(drawable);
                }else {
                    drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.color_1, null);
                    readingBackground.setBackground(drawable);
                }
                state_night=!state_night;
            }
        });
        settingLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    setting.setVisibility(View.VISIBLE);
                    bottomMenu.setVisibility(View.INVISIBLE);
                    topMenu.setVisibility(View.INVISIBLE);
            }
        });
        cyclePhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.color_1, null);
              readingBackground.setBackground(drawable);
            }
        });
        cyclePhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.color_2, null);
                readingBackground.setBackground(drawable);
            }
        });
        cyclePhoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.color_3, null);
                readingBackground.setBackground(drawable);
            }
        });
        cyclePhoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.color_4, null);
                readingBackground.setBackground(drawable);
            }
        });
        cyclePhoto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.color_5, null);
                readingBackground.setBackground(drawable);
            }
        });
        readingBackground.setBackground(drawable);

        reduceByteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size--;
                if(size<0)
                {
                    size=0;
                }
                readText.setTextSize(size);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ReadActivity.this).edit();
                editor.putInt("size",size);
                editor.apply();
                byteText.setText(size+"");
            }
        });
        addByteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size++;
                readText.setTextSize(size);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ReadActivity.this).edit();
                editor.putInt("size",size);
                editor.apply();
                byteText.setText(size+"");
            }
        });
    }

    private void initview() {
        bookName = findViewById(R.id.book_Name);
        bottomMenu = findViewById(R.id.bottom_menu);
        topMenu = findViewById(R.id.top_menu);
        screen = findViewById(R.id.screen);
        buttonBack = findViewById(R.id.button_Back);
        catalogueLinear = findViewById(R.id.catalogue_linearLayout);
        cacheLinear = findViewById(R.id.cache_linearLayout);
        settingLinear = findViewById(R.id.setting_linearLayout);
        nightLinear = findViewById(R.id.night_linearLayout);
        setting = findViewById(R.id.setting);
        cyclePhoto1 = findViewById(R.id.color_1);
        cyclePhoto2 = findViewById(R.id.color_2);
        cyclePhoto3 = findViewById(R.id.color_3);
        cyclePhoto4 = findViewById(R.id.color_4);
        cyclePhoto5 = findViewById(R.id.color_5);
        readingBackground = findViewById(R.id.reading_background);
        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.color_1, null);
        catalogue = findViewById(R.id.catalogue_relative);
        catalogueListView = findViewById(R.id.listView_catalogue);
        readText = findViewById(R.id.readText);
        myscrollView = findViewById(R.id.scrollView);
        reduceByteButton = findViewById(R.id.reduce_typeface);
        addByteButton = findViewById(R.id.add_typeface);
        byteText  = findViewById(R.id.text_typeface);
        upChapter = findViewById(R.id.up);
        downChapter = findViewById(R.id.down);
        chapterSeeBar = findViewById(R.id.seeBar_chapter);
    }


    public void requestBookId()
    {
        final String url = "http://106.55.148.161:8080/ireader/book/findIdByName?bookName="+bookNameText;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                Log.e(TAG,"返回值 =  ="+responseText);
                bookId = Utility.handleBookIdResponse(responseText);
                requestBookContext(bookId.data.bookId);
                Log.e(TAG,"bookid == "+bookId.data.bookId);
            }
        });
    }
    public void requestBookContext(String bookId)
    {
        final String url = "http://106.55.148.161:8080/ireader/book/chapter?bookId="+bookId;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                bookContext = Utility.handleBookContextResponse(responseText);
                final List<String> chapterTitleList = new ArrayList<>();
                for(int i=0;i<bookContext.data.size();i++)
                {
                    chapterTitleList.add(bookContext.data.get(i).chapterTitle);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showBookContext(chapterTitleList);
                    }
                });

            }
        });
    }

    private void showBookContext(final List<String> chapterTitleList) {
        adapter =new ArrayAdapter<>(ReadActivity.this,android.R.layout.simple_list_item_1,chapterTitleList);
        catalogueListView.setAdapter(adapter);
        chapterSeeBar.setMax(chapterTitleList.size()-1);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        chapter = prefs.getInt("chapter",0);
        if(chapter!=0)
        {
            readText.setText(bookContext.data.get(chapter).contentData);
        }else {
            readText.setText(bookContext.data.get(0).contentData);
        }
        chapterSeeBar.setProgress(chapter);
        upChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chapter--;
                if(chapter<0)
                {
                    chapter=0;
                    Toast.makeText(ReadActivity.this,"已经是第一章了",Toast.LENGTH_SHORT).show();
                }
                chapterSeeBar.setProgress(chapter);
                readText.setText(bookContext.data.get(chapter).contentData);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ReadActivity.this).edit();
                editor.putInt("chapter",chapter);
                editor.apply();
            }
        });
        downChapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chapter++;
                if(chapter>=chapterTitleList.size())
                {
                    chapter=chapterTitleList.size()-1;
                    Toast.makeText(ReadActivity.this,"已经到最后一章了",Toast.LENGTH_SHORT).show();
                }
                chapterSeeBar.setProgress(chapter);
                readText.setText(bookContext.data.get(chapter).contentData);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ReadActivity.this).edit();
                editor.putInt("chapter",chapter);
                editor.apply();
            }
        });

        chapterSeeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                chapter = progress;
                readText.setText(bookContext.data.get(chapter).contentData);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ReadActivity.this).edit();
                editor.putInt("chapter",chapter);
                editor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        catalogueListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                readText.setText(bookContext.data.get(position).contentData);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(ReadActivity.this).edit();
                editor.putInt("chapter",position);
                editor.apply();
                chapter = position;
                chapterSeeBar.setProgress(chapter);
                myscrollView.scrollTo(0,0);
                catalogue.setVisibility(View.INVISIBLE);
            }
        });

    }
}

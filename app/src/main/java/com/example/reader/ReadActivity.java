package com.example.reader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ReadActivity extends AppCompatActivity {

    private TextView bookName;

    private LinearLayout bottomMenu;

    private LinearLayout setting;

    private RelativeLayout topMenu;

    private RelativeLayout screen;

    private static boolean state = true;

    private static boolean state_setting = false;

    private Button buttonBack;

    private String TAG = "tag+ = ";

    private LinearLayout catalogueLinear,cacheLinear,nightLinear,settingLinear;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initview();
        final String bookNameText = getIntent().getStringExtra("bookName");
        bookName.setText(bookNameText);
        screen.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(state)
                {
                    bottomMenu.setVisibility(View.VISIBLE);
                    topMenu.setVisibility(View.VISIBLE);
                }else {
                    bottomMenu.setVisibility(View.INVISIBLE);
                    topMenu.setVisibility(View.INVISIBLE);
                }
                if(!state_setting)
                {
                    setting.setVisibility(View.INVISIBLE);
                    state_setting = !state_setting;
                }
                state = !state;
                Log.e(TAG,"state="+state);
                return false;
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadActivity.this,MainActivity.class);
                startActivity(intent);
                state = !state;
                finish();
            }
        });
//        catalogueLinear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                screen.openDrawer(GravityCompat.START);
//            }
//        });
        cacheLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        nightLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screen.setBackgroundColor(0);
            }
        });
        settingLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    setting.setVisibility(View.VISIBLE);
                    bottomMenu.setVisibility(View.INVISIBLE);
                    topMenu.setVisibility(View.INVISIBLE);
                    state_setting = !state_setting;
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
    }
}

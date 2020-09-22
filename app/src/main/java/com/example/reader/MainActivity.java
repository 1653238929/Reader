package com.example.reader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.customview.widget.ViewDragHelper;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;

import com.example.reader.fragment.BookMall_Fragment;
import com.example.reader.fragment.Bookstore_Fragment;
import com.example.reader.fragment.Community_Fragment;
import com.example.reader.Adapter.MfrangmentAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private List<Fragment> fragmentList = new ArrayList<>();

    private DrawerLayout drawerLayout;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        setDrawerLeftEdgSize(MainActivity.this,drawerLayout,0.3f);
        fragmentList.add(new Community_Fragment());
        fragmentList.add(new Bookstore_Fragment());
        fragmentList.add(new BookMall_Fragment());

        MfrangmentAdapter mfrangmentAdapter = new MfrangmentAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(mfrangmentAdapter);

        viewPager.setCurrentItem(1);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.bottom_item_1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.bottom_item_2:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.bottom_item_3:
                        viewPager.setCurrentItem(2);
                }
                return true;
            }
        });
    }

    /*
    初始化
     */
    private void initview() {
        viewPager = findViewById(R.id.viewPager_main);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        drawerLayout = findViewById(R.id.drawerLayout_main);
    }


    /*
    菜单更容易划出
     */
    private void setDrawerLeftEdgSize(Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage){
        if(activity == null||drawerLayout==null)
            return;
        try {
            Field leftDraggerField = drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);
            DisplayMetrics dm = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
            edgeSizeField.setInt(leftDragger,Math.max(edgeSize,(int)(dm.widthPixels *displayWidthPercentage)));

        } catch (IllegalAccessException e) {
            Log.e("IllegalAccessException",e.getMessage().toString());
        } catch (NoSuchFieldException e) {
            Log.e("NoSuchFieldExcption",e.getMessage().toString());
        }
    }
}

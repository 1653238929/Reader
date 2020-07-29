package com.example.reader.util;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.example.reader.MainActivity;
import com.example.reader.ReadActivity;

/**
 * @ClassName: MyScrollView.java
 * @Description:
 * @author iaiai 176291935@qq.com
 * @date 2015-1-1 下午5:43:12
 */
public class MyScrollView extends ScrollView {

    float x_temp01 = 0.0f;
     float y_temp01 = 0.0f;
     float x_temp02 = 0.0f;
     float y_temp02 = 0.0f;

     private String TAG = "Myscrollview";

    public MyScrollView(Context context) {
        super(context);

    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) // 这个方法如果返回 true 的话
    // 两个手指移动，启动一个按下的手指的移动不能被传播出去。
    {
        super.onInterceptTouchEvent(event);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)// 这个方法如果 true 则整个Activity 的
    // onTouchEvent() 不会被系统回调
    {
//        if(event.getAction()==MotionEvent.ACTION_MOVE)
//        {
//            super.onTouchEvent(event);
//            return true;
//        }else
//        {
//            super.onTouchEvent(event);
//            return false;
//        }
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                x_temp01 = x;
                y_temp01 = y;
            }
            break;
            case MotionEvent.ACTION_UP:
            {
                x_temp02 = x;
                y_temp02 = y;
            }
            if(x_temp01!=0&&y_temp01!=0)
            {
                if(x_temp01==x_temp02&&y_temp01==y_temp02)
                {
//                    Log.e(TAG,"true");
                    final ReadActivity readActivity = (ReadActivity) getContext();
//                    readActivity.bottomMenu.setVisibility(View.VISIBLE);
//                    readActivity.topMenu.setVisibility(View.VISIBLE);
//                    readActivity.state = !readActivity.state;
                    if(readActivity.state)
                    {
                        readActivity.bottomMenu.setVisibility(View.VISIBLE);
                        readActivity.topMenu.setVisibility(View.VISIBLE);
                    }else {
                        readActivity.bottomMenu.setVisibility(View.INVISIBLE);
                        readActivity.topMenu.setVisibility(View.INVISIBLE);
                    }
                    readActivity.setting.setVisibility(View.INVISIBLE);
                    readActivity.state = !readActivity.state;
                    readActivity.catalogue.setVisibility(View.INVISIBLE);
                }else
                {
                }
            }
            break;
        }
        Log.e(TAG,"event");
        return super.onTouchEvent(event);
    }

}


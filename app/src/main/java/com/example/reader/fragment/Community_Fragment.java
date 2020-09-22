package com.example.reader.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.example.reader.Adapter.mRecycleAdapter;
import com.example.reader.PostCommunity;
import com.example.reader.R;
import com.example.reader.db.Community;
import com.example.reader.db.CommunityList;
import com.example.reader.util.HttpUtil;
import com.example.reader.util.Pic;
import com.example.reader.util.Utility;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Community_Fragment extends Fragment {

    private RecyclerView recyclerView;

    private List<Community> communityList = new ArrayList<>();

    private String TAG = "找到的  ";

    private Pic pic;

    private ImageView communityAdd;

    private String responseText;

    private String responseTextPic;

    private boolean state_1 = false;

    private boolean state_2 = false;

    private SwipeRefreshLayout swipeRefreshLayout;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what)
            {
                case 1:
                    if(state_1&&state_2)
                    {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mRecycleAdapter mRecycleAdapter = new mRecycleAdapter(communityList,getContext(),pic.picList);
                                recyclerView.setAdapter(mRecycleAdapter);
                            }
                        });

                    }
            }
            super.handleMessage(msg);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_fragment,container,false);
        recyclerView = view.findViewById(R.id.recyclerView);
        communityAdd = view.findViewById(R.id.community_add);
        swipeRefreshLayout = view.findViewById(R.id.community_swipe);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
            RequestCommunity();
            requestLoadPingPic();

        communityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PostCommunity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void requestLoadPingPic()
    {
        String loadPingUrl = "https://api.xygeng.cn/Bing/week/";
        HttpUtil.sendOkHttpRequest(loadPingUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Toast.makeText(WeatherActivity.this,"图片错误",Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                Log.e(TAG,"请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseTextPic = response.body().string();
                pic= Utility.handlePicResponse(responseTextPic);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                editor.putString("bingpic",responseTextPic);
                editor.apply();
                Log.e(TAG,"responseIcon   "+responseTextPic);
                state_1 = !state_1;
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }
        });
    }

    public void RequestCommunity()
    {
        int i = 1;
        int j = 10;
        final String url = "http://106.55.148.161:8080/ireader/post/list?page=0&size=5";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(TAG,"falte");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                    responseText = response.body().string();
                    final CommunityList communities = Utility.handleCommunity(responseText);
                    Log.e(TAG,"code =   "+communities.code);
                    if(communities.code==0)
                    {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                        editor.putString("community", responseText);
                        editor.apply();
                        communityList = communities.communityList;
                    }
                    state_2 = !state_2;
                Message message = Message.obtain();
                message.what = 1;
                handler.sendMessage(message);
            }
        });
    }

}

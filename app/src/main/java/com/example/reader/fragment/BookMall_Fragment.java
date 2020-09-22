package com.example.reader.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.reader.Adapter.BookMallRecycleViewAdapter;
import com.example.reader.R;

public class BookMall_Fragment extends Fragment {

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookmall_fragment,container,false);

        initView(view);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        BookMallRecycleViewAdapter bookMallRecycleViewAdapter = new BookMallRecycleViewAdapter(getContext());
        recyclerView.setAdapter(bookMallRecycleViewAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                BookMallRecycleViewAdapter bookMallRecycleViewAdapter = new BookMallRecycleViewAdapter(getContext());
                recyclerView.setAdapter(bookMallRecycleViewAdapter);

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(1000);//休眠3秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }.start();
            }
        });
        return view;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.book_mall_recycleView);
        swipeRefreshLayout = view.findViewById(R.id.book_mall_swipe);
    }

    public void renew()
    {
        BookMallRecycleViewAdapter bookMallRecycleViewAdapter = new BookMallRecycleViewAdapter(getContext());
        recyclerView.setAdapter(bookMallRecycleViewAdapter);
    }
}

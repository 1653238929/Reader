package com.example.reader.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.reader.R;
import com.example.reader.db.BookCatalogue;
import com.example.reader.db.BookName;
import com.example.reader.util.HttpUtil;
import com.example.reader.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Bookstore_Fragment extends Fragment {
    private BookCatalogue bookCatalogue;
    private List<String> bookList = new ArrayList<>();
    private ListView listView;
    private RecyclerView recyclerView;
    private String responseTextCatalogue;
    private String responseTextBookName;
    private String TAG = "类目表";

    private int pos = 2;

    private WaveSwipeRefreshLayout waveSwipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bookstore_fragment,container,false);
        initView(view);

        requestBookCatalogue();
        requestBookContext(2);

        waveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Log.e(TAG, "pos=    : "+pos );
                requestBookContext(pos);
            }
        });
        return view;
    }

    private void initView(View view) {
        listView = view.findViewById(R.id.bookstore_listView);
        recyclerView = view.findViewById(R.id.book_store_recycleView);
        waveSwipeRefreshLayout = view.findViewById(R.id.book_store_swipe);
    }

    public void requestBookCatalogue()
    {
        String url = "http://106.55.148.161:8080/ireader/book/categoryList";
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseTextCatalogue = response.body().string();
                 bookCatalogue = Utility.handleBookCatalogue(responseTextCatalogue);
                Log.e(TAG, "onResponse: "+responseTextCatalogue);
                if(bookCatalogue.code==0)
                {
                    Log.e(TAG, "onResponse: "+ bookCatalogue.data.get(1).categoryName );
                    for(int i=0;i<bookCatalogue.data.size();i++) {
                        bookList.add(bookCatalogue.data.get(i).categoryName);
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.book_store_listview_item,bookList);
                            listView.setAdapter(arrayAdapter);

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
//                                    Toast.makeText(getContext(),""+ bookCatalogue.data.get(position).categoryId,Toast.LENGTH_SHORT).show();
                                    requestBookContext(bookCatalogue.data.get(position).categoryId);
                                    pos = bookCatalogue.data.get(position).categoryId;
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    public void requestBookContext(int id)
    {
        String url = "http://106.55.148.161:8080/ireader/book/findByCategory?categoryId=" +id;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                responseTextBookName = response.body().string();
                final BookName bookName = Utility.handleBookName(responseTextBookName);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(linearLayoutManager);
                        BookStoreRecycleViewAdapter bookStoreRecycleViewManger = new BookStoreRecycleViewAdapter(bookName,getContext(),getActivity());
                        recyclerView.setAdapter(bookStoreRecycleViewManger);
                    }
                });
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Thread.sleep(2000);//休眠3秒
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        waveSwipeRefreshLayout.setRefreshing(false);
                    }
                }.start();
            }
        });
    }
}

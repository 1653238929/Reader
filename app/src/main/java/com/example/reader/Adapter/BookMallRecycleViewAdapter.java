package com.example.reader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reader.BookDetails;
import com.example.reader.R;
import com.example.reader.ReadActivity;
import com.example.reader.fragment.BookMall_Fragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookMallRecycleViewAdapter extends RecyclerView.Adapter<BookMallRecycleViewAdapter.Holder> {

    private String TAG = "====================";

    private List<String> bookMallIcon;
    private List<String> bookMallName;
    private Context context;

    private List<String> bookMallAuthor;

    private List<String> bookMallContext;


    private int i = 0;
    @NonNull
    @Override
    public BookMallRecycleViewAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_mall_recycleview_item,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    public BookMallRecycleViewAdapter(Context context1)
    {
        context = context1;
    }

    @Override
    public void onBindViewHolder(@NonNull BookMallRecycleViewAdapter.Holder holder, final int position) {
            Glide.with(context).load(bookMallIcon.get(position)).into(holder.imageView1);
            holder.textView1.setText(bookMallName.get(position));
            holder.author.setText(bookMallAuthor.get(position));
           holder.context.setText(bookMallContext.get(position));

        holder.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ReadActivity.class);
                intent.putExtra("bookName",bookMallName.get(position));
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
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

                bookMallIcon.remove(position);
                bookMallAuthor.remove(position);
                bookMallContext.remove(position);
                bookMallName.remove(position);

                 data = gson.toJson(bookMallIcon);
               data1 = gson.toJson(bookMallName);
                 data2 = gson.toJson(bookMallAuthor);
                 data3 = gson.toJson(bookMallContext);
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putString("bookMallIcon4",data);
                editor.putString("bookMallName4",data1);
                editor.putString("bookMallAuthor",data2);
                editor.putString("bookMallContext",data3);
                editor.apply();
                Toast.makeText(context,"已删除",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
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
        Log.e(TAG, "getItemCount: "+bookMallIcon+bookMallAuthor +bookMallContext);
        if(bookMallIcon==null)
        {
            return 0;
        }
        return bookMallIcon.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView1;
        TextView textView1,author,context;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.book_mall_image1);
            textView1 = itemView.findViewById(R.id.book_mall_name);
            author = itemView.findViewById(R.id.book_mall_auther);
            context =itemView.findViewById(R.id.book_mall_context);
        }
    }
}

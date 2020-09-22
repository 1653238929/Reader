package com.example.reader.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reader.BookDetails;
import com.example.reader.R;
import com.example.reader.db.BookName;

class BookStoreRecycleViewAdapter extends RecyclerView.Adapter<BookStoreRecycleViewAdapter.Holder> {

    private BookName bookName;

    private Context context;

    private Activity activity;

    public BookStoreRecycleViewAdapter(BookName bookName1, Context context1, Activity activity1)
    {
        bookName = bookName1;
        context = context1;
        activity = activity1;
    }

    public BookStoreRecycleViewAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_store_recycleview_item,parent,false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookStoreRecycleViewAdapter.Holder holder, final int position) {
        holder.name.setText(bookName.data.get(position).bookName);
        holder.auther.setText(bookName.data.get(position).bookAuthor);
        holder.context.setText(bookName.data.get(position).bookDescription);
        Glide.with(context).load(bookName.data.get(position).bookIcon).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookDetails.class);
                intent.putExtra("book_id",bookName.data.get(position).bookId);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookName.data.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView name,auther,context;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.book_store_image);
            name  = itemView.findViewById(R.id.book_store_name);
            auther = itemView.findViewById(R.id.book_store_auther);
            context = itemView.findViewById(R.id.book_store_context);
        }
    }
}

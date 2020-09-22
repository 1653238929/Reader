package com.example.reader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reader.MainActivity;
import com.example.reader.R;
import com.example.reader.ReadActivity;
import com.example.reader.db.Community;

import org.w3c.dom.Text;

import java.util.List;

public class mRecycleAdapter extends RecyclerView.Adapter<mRecycleAdapter.Holder> {

    private List<Community> communityList;
    private Context context;
    private List<String> picList;
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item,parent,false);
            Holder holder = new Holder(view);
            return holder;
    }

    public mRecycleAdapter(List<Community> list,Context con,List<String> listPic)
    {
        communityList = list;
        context = con;
        picList = listPic;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position) {
        holder.bookName.setText(communityList.get(position).bookName);
        holder.userName.setText(communityList.get(position).userName);
        holder.communityText.setText(communityList.get(position).community);
        Glide.with(context).load(communityList.get(position).icon).into(holder.bookImage);
        Glide.with(context).load(picList.get(position%7)).into(holder.cardImage);
        holder.cardView.setRadius(30);
//        holder.cardView.setContentPadding(5,5,5,5);
        holder.cardView.setCardElevation(5);
        holder.cardView.setCardBackgroundColor(8);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadActivity.class);
                intent.putExtra("bookName",communityList.get(position).bookName);
                context.startActivity(intent);
//                MainActivity mainActivity = (MainActivity) context;
//                mainActivity.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return communityList.size();
    }

    static class Holder extends RecyclerView.ViewHolder{

        TextView userName;

        TextView bookName;

        TextView communityText;

        ImageView bookImage;

        CardView cardView;

        ImageView cardImage;

        public Holder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            bookImage = itemView.findViewById(R.id.bookImage);
            bookName = itemView.findViewById(R.id.bookName);
            communityText = itemView.findViewById(R.id.communityText);
            cardView = itemView.findViewById(R.id.cardView);
            cardImage = itemView.findViewById(R.id.cardImage);
        }
    }
}

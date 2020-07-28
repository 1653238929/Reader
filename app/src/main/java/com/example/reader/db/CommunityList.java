package com.example.reader.db;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommunityList {
    public int code;
    @SerializedName("data")
    public List<Community> communityList;
    public List<Comment> commentList;
}

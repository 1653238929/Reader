package com.example.reader.db;

import com.google.gson.annotations.SerializedName;

public class Community {
    public String postId;
    @SerializedName("userName")
    public String userName;
    @SerializedName("postBook")
    public String bookName;
    @SerializedName("postContent")
    public String community;
    @SerializedName("postIcon")
    public String icon;
    public int  bookId;
}

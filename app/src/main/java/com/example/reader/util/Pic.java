package com.example.reader.util;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Pic {
    @SerializedName("code")
    public int code;
    @SerializedName("data")
    public List<String> picList;
}

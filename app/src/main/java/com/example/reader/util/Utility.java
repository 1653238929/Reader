package com.example.reader.util;

import com.example.reader.BookDetails;
import com.example.reader.db.BookCatalogue;
import com.example.reader.db.BookContentData;
import com.example.reader.db.BookDetail;
import com.example.reader.db.BookName;
import com.example.reader.db.Community;
import com.example.reader.db.CommunityList;
import com.google.gson.Gson;

public class Utility {
    public static CommunityList handleCommunity(String response)
    {
        return new Gson().fromJson(response,CommunityList.class);
    }
    public static Pic handlePicResponse(String response)
    {
        return new Gson().fromJson(response,Pic.class);
    }
    public static State handleStateResponse(String response)
    {
        return  new Gson().fromJson(response,State.class);
    }
    public static BookId handleBookIdResponse(String response)
    {
        return  new Gson().fromJson(response,BookId.class);
    }
    public static BookContext handleBookContextResponse(String response)
    {
        return  new Gson().fromJson(response,BookContext.class);
    }
    public  static BookCatalogue handleBookCatalogue(String response)
    {
        return  new Gson().fromJson(response,BookCatalogue.class);
    }
    public  static BookName handleBookName(String response)
    {
        return  new Gson().fromJson(response,BookName.class);
    }
    public  static BookDetail handleBookDetail(String response)
    {
        return  new Gson().fromJson(response,BookDetail.class);
    }
    public  static BookContentData handleBookContentData(String response)
    {
        return  new Gson().fromJson(response,BookContentData.class);
    }
}

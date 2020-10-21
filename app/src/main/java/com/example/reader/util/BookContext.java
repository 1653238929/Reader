package com.example.reader.util;

import java.util.ArrayList;
import java.util.List;

public class BookContext {
    public int code;
    public List<BookCon> data;

    public int size() {
        return data.size();
    }

    public List<String> getList()
    {
        final List<String> chapterTitleList = new ArrayList<>();
                for(int i=0;i<data.size();i++)
                {
                    chapterTitleList.add(data.get(i).chapterTitle);
                }
        return chapterTitleList;
    }
    public class BookCon {
        public int chapterId;
        public String chapterTitle;
    }
}

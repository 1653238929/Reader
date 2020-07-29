package com.example.reader.util;

import java.util.List;

public class BookContext {
    public int code;
    public List<BookCon> data;

    public class BookCon {
        public String chapterTitle;
        public String contentData;
    }
}

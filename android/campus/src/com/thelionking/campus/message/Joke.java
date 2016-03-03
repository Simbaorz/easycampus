package com.thelionking.campus.message;

import java.io.Serializable;

/**
 * Created by the lion king on 2014/9/16.
 * Joke实体类
 */
public class Joke implements Serializable{
    public static final int TEXT = 0;
    public static final int PICTURE = 1;
    public static final int VEDIO = 2;

    public static final int LASTEST_MODE = 0;
    public static final int RECOMMEND_MODE =1;

    private long id;

    private long date;

    private String author;

    private String source;

    private String content;

    private String imgurl;

    private int readingCount;

    private int type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getReadingCount() {
        return readingCount;
    }

    public void setReadingCount(int readingCount) {
        this.readingCount = readingCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Joke{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", author='" + author + '\'' +
                ", source=" + source +
                ", content='" + content + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", readingCount='" + readingCount + '\'' +
                ", type=" + type +
                '}';
    }

}

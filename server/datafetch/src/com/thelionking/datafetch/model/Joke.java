package com.thelionking.datafetch.model;



/**
 * 笑话实体类
 * @author thelionking
 * Id由数据库生成
 */
public final class Joke{
	public static final int LASTEST_MODE = 0;
	public static final int RECOMMEND_MODE = 1;
	
	public static final int TEXT = 0;
	public static final int PICTURE = 1;
	public static final int VEDIO = 2;
	
	//主键Id
	private long id;
	
	//时间，具体到秒,使用long
	private long date;
	
	//作者姓名
	private String author;
	
	//数据源
	private Source source;
	
//	//标题
//	private String title;
	
	//内容
	private String content;
	
	//图像地址
	private String imgurl;
	
	//浏览次数
	private int readingCount;
	
	//类型
//	private int type;
	
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

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

//	public String getTitle() {
//		return title;
//	}
//
//	public void setTitle(String title) {
//		this.title = title;
//	}

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

	@Override
	public String toString() {
		return "Joke [id=" + id + ", date=" + date + ", author=" + author
				+ ", source=" + source + ", content=" + content + ", imgurl="
				+ imgurl + ", readingCount=" + readingCount + "]";
	}

//	public int getType() {
//		return type;
//	}
//
//	public void setType(int type) {
//		this.type = type;
//	}
}

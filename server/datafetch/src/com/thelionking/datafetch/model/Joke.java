package com.thelionking.datafetch.model;



/**
 * Ц��ʵ����
 * @author thelionking
 * Id�����ݿ�����
 */
public final class Joke{
	public static final int LASTEST_MODE = 0;
	public static final int RECOMMEND_MODE = 1;
	
	public static final int TEXT = 0;
	public static final int PICTURE = 1;
	public static final int VEDIO = 2;
	
	//����Id
	private long id;
	
	//ʱ�䣬���嵽��,ʹ��long
	private long date;
	
	//��������
	private String author;
	
	//����Դ
	private Source source;
	
//	//����
//	private String title;
	
	//����
	private String content;
	
	//ͼ���ַ
	private String imgurl;
	
	//�������
	private int readingCount;
	
	//����
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

package com.thelionking.datafetch.model.fetcher;


public abstract class Fetcher implements IFetcher {
	
	public static final String EMPTY_STR = "";
	
	public static final String[] HTML_FILTERS = {
		//���˵�<span>
		"<\\s*span(\\n|.)*?>",
		//����</span>
		"</span>",
		//����<a>
		"<\\s*a(\\n|.)*?>",
		//����</a>
		"</a>",
		//����<div>
		"<\\s*div(\\n|.)*?>",
		//����</div>
		"</div>",
		//����<br>
		"<\\s*(BR|br)\\s*/\\s*>",
		//����<img />
		"<\\s*(img|IMG)(\\n|.)*/\\s*>"
	};
	
	protected JokeDateGenerator generator;
	
	protected String baseUrl;
	
	protected String propertiesDir;
	
	public Fetcher(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	/**
	 * ֻ�ṩ��FetcherContainerʹ��
	 */
	protected void setGenerator(JokeDateGenerator generator){
		this.generator = generator;
	}
	
	/**
	 * ֻ�ṩ��FetcherContainerʹ��
	 */
	protected abstract void setPropertiesDir(String propertiesDir);
	
	protected abstract String generateUrl();
	
	protected abstract void rollback();
	
	public static String doHtmlFilter(String s, String[] filters) {
		s = s.trim();
		for(String pattern : filters){
			s = s.replaceAll(pattern, EMPTY_STR);
		}
		return s;
	}
	
	public static int generateReadingCount(int base){
		return (100 + (int)(base * Math.random()));
	}
}

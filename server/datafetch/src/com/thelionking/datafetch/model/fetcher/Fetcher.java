package com.thelionking.datafetch.model.fetcher;


public abstract class Fetcher implements IFetcher {
	
	public static final String EMPTY_STR = "";
	
	public static final String[] HTML_FILTERS = {
		//过滤掉<span>
		"<\\s*span(\\n|.)*?>",
		//过滤</span>
		"</span>",
		//过滤<a>
		"<\\s*a(\\n|.)*?>",
		//过滤</a>
		"</a>",
		//过滤<div>
		"<\\s*div(\\n|.)*?>",
		//过滤</div>
		"</div>",
		//过滤<br>
		"<\\s*(BR|br)\\s*/\\s*>",
		//过滤<img />
		"<\\s*(img|IMG)(\\n|.)*/\\s*>"
	};
	
	protected JokeDateGenerator generator;
	
	protected String baseUrl;
	
	protected String propertiesDir;
	
	public Fetcher(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	
	/**
	 * 只提供给FetcherContainer使用
	 */
	protected void setGenerator(JokeDateGenerator generator){
		this.generator = generator;
	}
	
	/**
	 * 只提供给FetcherContainer使用
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

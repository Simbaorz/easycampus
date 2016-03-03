package com.thelionking.datafetch.model.fetcher;

import com.thelionking.datafetch.model.Joke;
import com.thelionking.datafetch.model.Source;


public final class FetchManager {
	
	private FetchManager() {
		
	}
	
	public static class InstanceHelper {
		static FetchManager INSTANCE = new FetchManager();
	}
	
	public static FetchManager getInstance() {
		return InstanceHelper.INSTANCE;
	}
	
	public void createTextThread() {
		FetcherContainer textContainer = new FetcherContainer(DateManager.getInstance().getGenerator(Joke.TEXT), "text.properties");
		Fetcher nhb = new TextNHBFetcher("http://www.neihan8.com/article/list_5_");
		textContainer.addFetcher(Source.NHB, nhb);
		FetchThread fetchThread = new FetchThread(FetchUtil.NeiHanBa, 1, textContainer);
		if(fetchThread.getRunPermission()) {
			new Thread(fetchThread).start();
		}
	}
	
	public void createPictureThread() {
		FetcherContainer pictureContainer = new FetcherContainer(DateManager.getInstance().getGenerator(Joke.PICTURE), "picture.properties");
		Fetcher nhb = new PictureNHSQFetcher("http://www.neihanshequ.com/pic/new/?page=");
		pictureContainer.addFetcher(Source.NHB, nhb);
		FetchThread fetchThread = new FetchThread(FetchUtil.NeiHanSheQu, 1, pictureContainer);
		if(fetchThread.getRunPermission()) {
			new Thread(fetchThread).start();
		}
	}
	
	public void createVedioThread() {
		
	}
	
}

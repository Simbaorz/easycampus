package com.thelionking.datafetch.model.fetcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thelionking.datafetch.exception.AbsentJokeDataGeneratorException;
import com.thelionking.datafetch.model.Source;
/**
 * FetcherContainer��Fetcher��������ÿ������������һ��Ц�����͵�ץȡ
 * ÿ�����͵����ݿ����Բ�ͬ��Դ��ÿ��Fetcher����һ��Դ
 * @author the lion king
 *
 */
public class FetcherContainer implements IFetcher{
	
	protected JokeDateGenerator generator;
	
	protected Map<Source,Fetcher> fetchers;
	
	protected String propertiesDir;
	
	public FetcherContainer(JokeDateGenerator generator, String propertiesDir){
		this.generator = generator;
		this.propertiesDir = propertiesDir;
		fetchers = new HashMap<Source, Fetcher>();
	}

	public void addFetcher(Source source, Fetcher fetcher){
		fetcher.setGenerator(this.generator);
		fetcher.setPropertiesDir(this.propertiesDir);
		this.fetchers.put(source, fetcher);
	}
	
	public void removeFetcher(Source source){
		this.fetchers.remove(source);
	}
	
	@Override
	public void fetch() {
		if(fetchers != null) {
			for(Entry<Source, Fetcher> entry : fetchers.entrySet()){
				entry.getValue().fetch();
			}
		}
	}

	@Override
	public void close() {
		if(fetchers != null) {
			for(Entry<Source, Fetcher> entry : fetchers.entrySet()){
				entry.getValue().close();
			}
		}
		this.generator.write();
	}
}

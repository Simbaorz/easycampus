package com.thelionking.datafetch.buffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.thelionking.datafetch.buffer.JokeBufferManager.JokeData;
import com.thelionking.datafetch.model.Joke;

/**
 * ÿ��һ��JokeBuffer��ʵ������һ���lastest����recommendģʽ�µ��ı���ͼƬ����Ƶ�Ļ���
 * List���������һ�ɰ��ս�������
 * @author the lion king
 *
 */
public abstract class JokeBuffer {	
	//��Ч��startΪ�˸��߿ͻ��˲�Ҫ�ٷ��������ˣ��Ѿ�û�����ݿɸ������߸���û������
	public static final int INVALID_START = -1;
	
	protected int textSize = 0;
	protected int pictureSize = 0;
	protected int vedioSize = 0;
	
	protected List<Joke> text = new ArrayList<Joke>();
	
	protected List<Joke> picture = new ArrayList<Joke>();
	
	protected List<Joke> vedio = new ArrayList<Joke>();
	
	public JokeBuffer() {
		load();
	}
	
	public int getSize(int type) {
		if(type == Joke.TEXT){
			return textSize;
		}
		else if(type == Joke.PICTURE){
			return pictureSize;
		}
		else if(type == Joke.VEDIO){
			return vedioSize;
		}
		return 0;
	}
	
	//û����,���ɸĽ�
	public JokeData getJokes(int type, int start){
		JokeData jokeData = null;
		if(start == INVALID_START){
			jokeData = new JokeData();
			jokeData.list = null;
			jokeData.nextStart = INVALID_START;
			return jokeData;
		}
		if(type == Joke.TEXT && textSize != 0){
			jokeData = get(this.text, start);
		}else if(type == Joke.PICTURE && pictureSize != 0){
			jokeData = get(this.picture, start);
		}else if(type == Joke.VEDIO && vedioSize != 0) {
			jokeData = get(this.vedio, start);
		}else{
			jokeData = new JokeData();
			jokeData.list = null;
			jokeData.nextStart = INVALID_START;
		}
		return jokeData;
	}

	
 	protected void sort(Comparator<Joke> c){
		if(textSize != 0){
			Collections.sort(text, c);
		}
		if(pictureSize != 0){
			Collections.sort(picture, c);
		}
		if(vedioSize != 0){
			Collections.sort(vedio, c);
		}
 	}
 	
 	public List<Joke> getJokeList(int type) {
 		if(type == Joke.TEXT){
 			return text;
 		}else if(type == Joke.PICTURE){
 			return picture;
 		}else if(type == Joke.VEDIO) {
 			return vedio;
 		}else{
 			return null;
 		}
 	}
 	
 	protected void updateSize() {
 		textSize = text.size();
 		pictureSize = picture.size();
 		vedioSize = vedio.size();
 	}
	
	protected abstract JokeData get(List<Joke> jokes, int start);
	
 	protected abstract void load();
 	
 	protected abstract void update(List<Joke> text, List<Joke> picture, List<Joke> vedio);
	
}


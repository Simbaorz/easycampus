package com.thelionking.datafetch.buffer;

import java.util.List;

import com.thelionking.datafetch.buffer.JokeBufferManager.JokeData;
import com.thelionking.datafetch.model.Joke;

public final class RecommendJokeBuffer extends JokeBuffer{
	public RecommendJokeBuffer() {
		
	}
	
	@Override
	protected JokeData get(List<Joke> jokes, int start) {
		return null;
	}

	@Override
	protected synchronized void load() {
		
	}

	@Override
	protected synchronized void update(List<Joke> text, List<Joke> picture, List<Joke> vedio) {
		
	}


}

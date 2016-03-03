package com.thelionking.datafetch.model.fetcher;

import com.thelionking.datafetch.exception.AbsentJokeDataGeneratorException;

public class PictureContainer extends FetcherContainer{

	public PictureContainer(JokeDateGenerator generator, String propertiesDir) throws AbsentJokeDataGeneratorException {
		super(generator, propertiesDir);
	}

}

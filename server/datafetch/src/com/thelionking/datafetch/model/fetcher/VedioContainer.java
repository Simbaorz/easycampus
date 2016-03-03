package com.thelionking.datafetch.model.fetcher;

import com.thelionking.datafetch.exception.AbsentJokeDataGeneratorException;

public class VedioContainer extends FetcherContainer{

	public VedioContainer(JokeDateGenerator generator,String propertiesDir) throws AbsentJokeDataGeneratorException {
		super(generator, propertiesDir);
	}

}

package com.thelionking.datafetch.model.fetcher;

import com.thelionking.datafetch.exception.AbsentJokeDataGeneratorException;


/**
 *  textµÄ×¥È¡
 */
public class TextContainer extends FetcherContainer {

	public TextContainer(JokeDateGenerator generator, String propertiesDir) throws AbsentJokeDataGeneratorException {
		super(generator, propertiesDir);
	}
}



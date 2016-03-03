package com.thelionking.datafetch.exception;

public class AbsentJokeDataGeneratorException extends Exception{

	public AbsentJokeDataGeneratorException() {
		super("缺少JokeDateGenerator, JokeDateGenerator的实例不能为null");
	}
}

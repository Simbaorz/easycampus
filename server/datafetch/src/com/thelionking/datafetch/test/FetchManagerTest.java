package com.thelionking.datafetch.test;

import com.thelionking.datafetch.model.fetcher.FetchManager;

public class FetchManagerTest {
	public static void main(String[] args) {
		FetchManager m = FetchManager.getInstance();
		m.createTextThread();
	}
}

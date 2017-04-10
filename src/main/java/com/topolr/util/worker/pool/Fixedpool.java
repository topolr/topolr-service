package com.topolr.util.worker.pool;

import java.util.concurrent.Executors;

public class Fixedpool extends SimplePool {

	public Fixedpool(int size, String name) {
		this.type = SimplePool.POOL_TYPE_FIXED;
		this.pool = Executors.newFixedThreadPool(size);
		this.name = name;
	}
}

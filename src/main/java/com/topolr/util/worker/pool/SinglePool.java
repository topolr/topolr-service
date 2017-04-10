package com.topolr.util.worker.pool;

import java.util.concurrent.Executors;

public class SinglePool extends SimplePool {

    public SinglePool(String name) {
        this.type = SimplePool.POOL_TYPE_SINGLE;
        this.pool = Executors.newSingleThreadExecutor();
        this.name=name;
    }
}

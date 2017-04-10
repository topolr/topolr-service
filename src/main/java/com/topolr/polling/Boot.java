package com.topolr.polling;

import com.topolr.boot.IBootService;
import com.topolr.polling.base.Polling;
import com.topolr.util.jsonx.Jsonx;

public class Boot implements IBootService {

    @Override
    public void serviceStart(Jsonx option) {
        try {
            Polling.init(option);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void serviceStop() {
        Polling.getPolling().stop();
    }

}

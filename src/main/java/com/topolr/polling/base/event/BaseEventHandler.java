package com.topolr.polling.base.event;

import com.topolr.polling.base.ConnectManager.PollingConnect;
import com.topolr.polling.base.PollingResponse;
import javax.servlet.http.HttpServletRequest;

public abstract class BaseEventHandler {
    

    public abstract void onConnect(PollingConnect connect, HttpServletRequest request, PollingResponse response);

    public abstract void onClose(PollingConnect connect, PollingResponse response);

}

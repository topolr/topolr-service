package com.topolr.polling.router;

import com.topolr.polling.base.PollingRequest;
import com.topolr.polling.base.message.MessageWrapper;

public class RouterResult {

    private PollingRequest request;
    private MessageWrapper wrapper;

    public PollingRequest getRequest() {
        return request;
    }

    public void setRequest(PollingRequest request) {
        this.request = request;
    }

    public MessageWrapper getWrapper() {
        return wrapper;
    }

    public void setWrapper(MessageWrapper wrapper) {
        this.wrapper = wrapper;
    }

}

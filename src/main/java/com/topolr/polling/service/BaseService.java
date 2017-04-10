package com.topolr.polling.service;

import com.topolr.polling.base.PollingRequest;
import com.topolr.polling.base.PollingResponse;
import javax.servlet.http.HttpServletRequest;

public abstract class BaseService {

    protected HttpServletRequest request;

    public abstract void excute(PollingRequest request, PollingResponse response);
}

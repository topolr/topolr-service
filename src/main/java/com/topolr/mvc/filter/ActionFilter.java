package com.topolr.mvc.filter;

import com.topolr.mvc.view.View;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ActionFilter {

    private ActionFilter next;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public abstract View filter() throws Exception;

    public void setFilter(ActionFilter filter) {
        this.next = filter;
    }

    public View next() throws Exception {
        if (null != this.next) {
            this.next.request = this.request;
            return this.next.filter();
        } else {
            return null;
        }
    }

    public boolean isAjaxRequest() {
        String header = request.getHeader("X-Requested-With");
        boolean isAjax = "XMLHttpRequest".equals(header);
        return isAjax;
    }
}

package com.test.site.filter;

import com.axes.Result;
import com.axes.mvc.annotation.Filter;
import com.axes.mvc.filter.ActionFilter;
import com.axes.mvc.view.JspxView;
import com.axes.mvc.view.View;

@Filter(name = "session")
public class SessionFilter extends ActionFilter {

    @Override
    public View filter() throws Exception {
        Object user = this.request.getSession().getAttribute("user");
        if (null != user) {
            return this.next();
        } else {
            if(this.isAjaxRequest()){
                return Result.error("session out");
            }else{
                return new JspxView("admin");
            }
        }
    }

}

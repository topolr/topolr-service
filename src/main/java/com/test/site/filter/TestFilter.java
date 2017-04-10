package com.test.site.filter;

import com.axes.mvc.annotation.Filter;
import com.axes.mvc.filter.ActionFilter;
import com.axes.mvc.view.View;

@Filter(name = "aa")
public class TestFilter extends ActionFilter {

    @Override
    public View filter() throws Exception {
        System.out.println(this.request);
        return this.next();
    }
}

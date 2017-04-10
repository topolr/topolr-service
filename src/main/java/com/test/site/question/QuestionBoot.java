package com.test.site.question;

import com.axes.boot.IBootService;
import com.axes.util.jsonx.Jsonx;

public class QuestionBoot implements IBootService {

    @Override
    public void serviceStart(Jsonx option) {
        QuestionOption.init(option);
    }

    @Override
    public void serviceStop() {
    }

}

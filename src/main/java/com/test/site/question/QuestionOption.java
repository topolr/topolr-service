package com.test.site.question;

import com.axes.util.jsonx.Jsonx;

public class QuestionOption {

    private static QuestionOption option;

    private String sendTo;
    private String path;

    private QuestionOption(Jsonx option) {
        this.sendTo = option.get("sendTo").toString();
        this.path = option.get("path").toString();
    }

    public static void init(Jsonx option) {
        QuestionOption.option = new QuestionOption(option);
    }

    public static QuestionOption getOption() {
        return QuestionOption.option;
    }

    public String getSendTo() {
        return sendTo;
    }

    public String getPath() {
        return path;
    }
    
}

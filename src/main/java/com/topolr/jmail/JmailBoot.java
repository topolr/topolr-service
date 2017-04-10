package com.topolr.jmail;

import com.topolr.boot.IBootService;
import com.topolr.jmail.data.JmailConfig;
import com.topolr.jmail.page.JmailPage;
import com.topolr.util.jsonx.Jsonx;
import com.topolr.util.worker.JworkerContainer;

public class JmailBoot implements IBootService {

    @Override
    public void serviceStart(Jsonx option) {
        try {
            JmailConfig.init(option);
            JmailPage.init(option.get("template"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void serviceStop() {
        JworkerContainer.getContainer().getSinglePool("xxmailonly").stop();
    }

}

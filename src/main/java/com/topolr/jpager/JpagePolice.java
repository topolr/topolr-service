package com.topolr.jpager;

import com.topolr.jpager.base.Jpager;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JpagePolice {

    private ScheduledExecutorService schedule;
    private static JpagePolice police;

    public static JpagePolice getPolice() throws Exception {
        if (JpagePolice.police == null) {
            JpagePolice.police = new JpagePolice();
        }
        return JpagePolice.police;
    }

    private JpagePolice() {
    }

    protected void startup() throws Exception {
        this.schedule = Executors.newScheduledThreadPool(1);
        this.schedule.scheduleAtFixedRate(new PagePolice(), 10, Jpager.getPager().getConfig().get("backupTime").toLong(), TimeUnit.SECONDS);
    }

    protected void stupdown() {
        this.schedule.shutdownNow();
    }

    public class PagePolice implements Runnable {

        public void run() {
            try {
                JpageContainer.getContainer().backup();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

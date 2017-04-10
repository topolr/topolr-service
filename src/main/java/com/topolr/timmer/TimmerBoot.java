package com.topolr.timmer;

import com.topolr.boot.IBootService;
import com.topolr.util.jsonx.JsonEachArray;
import com.topolr.util.jsonx.Jsonx;
import com.topolr.util.worker.JworkerContainer;

public class TimmerBoot implements IBootService {

    @Override
    public void serviceStart(Jsonx option) {
        try {
            final int size = option.length();
            option.each(new JsonEachArray() {
                @Override
                public boolean each(int index, Jsonx json) {
                    try {
                        int delay = json.get("delay").toInt();
                        int circle = json.get("circle").toInt();
                        String type = json.get("type").toString();
                        String className = json.get("className").toString();
                        JtimmerTask task = (JtimmerTask) Class.forName(
                                className).newInstance();
                        task.setCircle(circle);
                        task.setDelay(delay);
                        task.setType(type);
                        JworkerContainer.getContainer()
                                .getSchedulePool("mainTimmer", size)
                                .addTask(task);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return false;
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void serviceStop() {
        JworkerContainer.getContainer().getSchedulePool("mainTimmer").stop();
    }
}

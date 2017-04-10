package com.test.site.contenter;

import com.axes.contenter.template.macro.base.ContentProviderBase;
import com.axes.mvc.annotation.ControllerService;
import com.axes.mvc.mapping.ServiceContainer;
import com.axes.util.base.reflect.ObjectSnooper;
import com.axes.util.base.reflect.each.FieldAnnotaionEach;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map.Entry;

public class BrooderProvider extends ContentProviderBase {

    public BrooderProvider() {
        try {
            final HashMap<String, String> map = new HashMap<>();
            ObjectSnooper.snoop(this).fieldAnnotaions(ControllerService.class, new FieldAnnotaionEach() {
                @Override
                public boolean each(Field field, Annotation annotion) throws Exception {
                    map.put(field.getName(), ((ControllerService) annotion).name());
                    return false;
                }
            });
            HashMap<String, Object> sers = new HashMap<>();
            for (Entry<String, String> entry : map.entrySet()) {
                sers.put(entry.getKey(), ServiceContainer.getContainer().getService(entry.getValue()));
            }
            ObjectSnooper.snoop(this).set(sers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

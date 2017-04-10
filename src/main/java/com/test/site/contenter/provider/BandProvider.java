package com.test.site.contenter.provider;

import com.axes.contenter.template.annotation.ContenterProvider;
import com.axes.mvc.annotation.ControllerService;
import com.axes.util.base.reflect.ObjectSnooper;
import com.test.site.contenter.BrooderProvider;
import com.test.site.models.Brand;
import com.test.site.service.BrandsService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ContenterProvider(name = "brandProvider")
public class BandProvider extends BrooderProvider {

    @ControllerService(name = "brand")
    private BrandsService service;

    public List<HashMap<String, Object>> getBrandsList() {
        try {
            List<HashMap<String, Object>> t=this.service.findAllBrands();
            System.out.println("===>"+t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            List<HashMap<String, Object>> list = new ArrayList<>();
            HashMap<String, Object> map = ObjectSnooper.snoop(new Brand()).toHashMap();
            list.add(map);
            return list;
        }
    }

    public List<HashMap<String, Object>> getBrands() {
        int num = Integer.parseInt(this.tagParameters.get("num").toString());
        try {
            return this.service.findBrands(num);
        } catch (Exception ex) {
            List<HashMap<String, Object>> list = new ArrayList<>();
            HashMap<String, Object> map = ObjectSnooper.snoop(new Brand()).toHashMap();
            list.add(map);
            return list;
        }
    }
}

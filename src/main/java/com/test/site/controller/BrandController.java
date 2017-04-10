package com.test.site.controller;

import com.axes.Result;
import com.axes.mvc.annotation.Action;
import com.axes.mvc.annotation.Controller;
import com.axes.mvc.annotation.ControllerService;
import com.axes.mvc.annotation.Filters;
import com.axes.mvc.controller.BaseController;
import com.axes.mvc.view.View;
import com.axes.util.file.Jile;
import com.test.site.models.Brand;
import com.test.site.service.BrandsService;
import java.io.File;

@Controller(basePath = "/brand")
@Filters(filters = "session")
public class BrandController extends BaseController {

    @ControllerService(name = "brand")
    private BrandsService service;
    private String name = "";
    private String url;
    private String ids;
    private int page;
    private int pageSize;
    private int id;
    private String image;
    private File file;

    @Action(path = "/editbrand")
    public View editBrand() throws Exception {
        Brand brand = new Brand();
        if (null != image && !image.equals("")) {
            brand.setImage(image);
        }
        brand.setId(id);
        brand.setUrl(url);
        brand.setName(name);
        service.edit(brand);
        return Result.success();
    }

    @Action(path = "/brandlist")
    public View brandList() throws Exception {
        return Result.success(service.findBrandPage("select * from brand where name like '%" + name + "%'", page-1, pageSize, null));
    }

    @Action(path = "/addbrand")
    public View addBrand() throws Exception {
        Brand brand = new Brand();
        if (null != image && !image.equals("")) {
            brand.setImage(image);
        }
        brand.setId(id);
        brand.setUrl(url);
        brand.setName(name);
        service.add(brand);
        return Result.success();
    }

    @Action(path = "/removebrands")
    public View name() throws Exception {
        service.removeBrands(ids);
        return Result.success();
    }

    @Action(path = "/brandimage")
    public View brandImage() throws Exception {
        String picname = System.currentTimeMillis() + ".png";
        Jile.with(file).move(this.basePath() + File.separator + "uploads" + File.separator + "brand" + File.separator + picname);
        return Result.success("uploads/brand/" + picname);
    }

}

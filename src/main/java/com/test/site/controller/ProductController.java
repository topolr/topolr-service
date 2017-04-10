package com.test.site.controller;

import com.axes.Result;
import com.axes.mvc.annotation.Action;
import com.axes.mvc.annotation.Controller;
import com.axes.mvc.annotation.ControllerService;
import com.axes.mvc.annotation.Filters;
import com.axes.mvc.controller.BaseController;
import com.axes.mvc.view.View;
import com.axes.util.file.Jile;
import com.test.site.models.Product;
import com.test.site.service.ProductService;
import java.io.File;

@Controller(basePath = "/product")
@Filters(filters = "session")
public class ProductController extends BaseController{

    @ControllerService(name = "product")
    private ProductService service;
    private int id;
    private String desc;
    private String image;
    private String pagename;
    private String title="";
    private File file;
    private int page;
    private int pageSize;
    private String ids;
    
    @Action(path = "/editproduct")
    public View editProduct() throws Exception {
        Product product=new Product();
        product.setId(id);
        if(null!=image&&!image.equals("")){
            product.setImage(image);
        }
        product.setId(id);
        product.setPagename(pagename);
        product.setTitle(title);
        product.setDescc(desc);
        service.edit(product);
        return Result.success();
    }
    
    @Action(path = "/productlist")
    public View productList() throws Exception {
        return Result.success(service.findProductPage("select * from product where title like '%"+title+"%'", page-1, pageSize, null));
    }
    
    @Action(path = "/addproduct")
    public View addProduct() throws Exception {
        Product product=new Product();
        if(null!=image&&!image.equals("")){
            product.setImage(image);
        }
        product.setId(id);
        product.setPagename(pagename);
        product.setTitle(title);
        product.setDescc(desc);
        service.add(product);
        return Result.success();
    }
    
    @Action(path = "/removeproducts")
    public View removeProduct() throws Exception {
        service.removeProducts(ids);
        return Result.success();
    }
    
    @Action(path = "/productimage")
    public View brandImage() throws Exception {
        String picname = System.currentTimeMillis() + ".png";
        Jile.with(file).move(this.basePath() + File.separator + "uploads" + File.separator + "product" + File.separator + picname);
        return Result.success("uploads/product/" + picname);
    }
}

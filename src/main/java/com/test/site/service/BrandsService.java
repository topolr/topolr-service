package com.test.site.service;

import com.axes.mvc.data.dao.MysqlDao.PageInfo;
import com.test.site.models.Brand;
import java.util.HashMap;
import java.util.List;

public interface BrandsService {

    public Brand add(Brand brand) throws Exception;

    public void remvoe(Brand brand) throws Exception;

    public void edit(Brand brand) throws Exception;

    public Brand find(Brand brand) throws Exception;

    public PageInfo findBrandPage(String sql, int from, int size, Object[] parameters) throws Exception;
    
    public List<HashMap<String,Object>> findAllBrands() throws Exception;
    
    public List<HashMap<String,Object>> findBrands(int num) throws Exception;
    
    public void removeBrands(String ids) throws Exception;
}

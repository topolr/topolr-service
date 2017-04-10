package com.test.site.service;

import com.axes.mvc.data.dao.MysqlDao.PageInfo;
import com.test.site.models.Product;

public interface ProductService {

    public Product add(Product product) throws Exception;

    public void remove(Product product) throws Exception;

    public void edit(Product product) throws Exception;

    public Product find(Product product) throws Exception;

    public PageInfo findProductPage(String sql, int from, int size, Object[] parameters) throws Exception;

    public void removeProducts(String ids) throws Exception;
}

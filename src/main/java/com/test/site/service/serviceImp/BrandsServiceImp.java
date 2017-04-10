package com.test.site.service.serviceImp;

import com.axes.mvc.annotation.Dao;
import com.axes.mvc.annotation.Service;
import com.axes.mvc.annotation.Transation;
import com.axes.mvc.data.dao.MysqlDao;
import com.test.site.models.Brand;
import com.test.site.service.BrandsService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service(name = "brand")
public class BrandsServiceImp implements BrandsService {

    @Dao
    private MysqlDao dao;

    @Override
    @Transation
    public Brand add(Brand brand) throws Exception {
        return this.dao.insert(brand);
    }

    @Override
    @Transation
    public void remvoe(Brand brand) throws Exception {
        this.dao.delete(brand);
    }

    @Override
    @Transation
    public void edit(Brand brand) throws Exception {
        this.dao.update(brand);
    }

    @Override
    public Brand find(Brand brand) throws Exception {
        return this.dao.query(brand);
    }

    @Override
    public MysqlDao.PageInfo findBrandPage(String sql, int from, int size, Object[] parameters) throws Exception {
        return this.dao.queryPage(sql, from, size, parameters);
    }

    @Override
    @Transation
    public void removeBrands(String ids) throws Exception {
        String[] t = ids.split(",");
        for (String id : t) {
            Brand brand = new Brand();
            brand.setId(Integer.parseInt(id));
            this.dao.delete(brand);
        }
    }

    @Override
    public List<HashMap<String, Object>> findAllBrands() throws Exception {
        return this.dao.queryList("select * from brand", null);
    }

    @Override
    public List<HashMap<String, Object>> findBrands(int num) throws Exception {
        List<HashMap<String, Object>> list = new ArrayList<>();
        List<HashMap<String, Object>> listn = this.dao.queryList("select * from brand", null);
        int a = num;
        for (HashMap<String, Object> map : listn) {
            list.add(map);
            a--;
            if (a == 0) {
                break;
            }
        }
        return list;
    }

}

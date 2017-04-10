package com.test.site.service.serviceImp;

import com.axes.mvc.annotation.Dao;
import com.axes.mvc.annotation.Service;
import com.axes.mvc.annotation.Transation;
import com.axes.mvc.data.dao.MysqlDao;
import com.test.site.models.Role;
import com.test.site.service.RoleService;

@Service(name = "role")
public class RoleServiceImp implements RoleService {

    @Dao
    private MysqlDao dao;

    @Override
    @Transation
    public Role add(Role role) throws Exception {
        return this.dao.insert(role);
    }

    @Override
    @Transation
    public void remove(Role role) throws Exception {
        this.dao.delete(role);
    }

    @Override
    @Transation
    public void edit(Role role) throws Exception {
        this.dao.update(role);
    }

    @Override
    public Role find(Role role) throws Exception {
        return this.dao.query(role);
    }

    @Override
    public MysqlDao.PageInfo findRolePage(String sql, int form, int size, Object[] parameters) throws Exception {
        return this.dao.queryPage(sql, form, size, parameters);
    }

    @Override
    public MysqlDao.RowList findRoleRowList(String sql, Object[] parameters) throws Exception {
        return this.dao.queryRowList(sql, parameters);
    }

}

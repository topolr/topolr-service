package com.test.site.service;

import com.axes.mvc.data.dao.MysqlDao.PageInfo;
import com.axes.mvc.data.dao.MysqlDao.RowList;
import com.test.site.models.Role;

public interface RoleService {

    public Role add(Role role) throws Exception;

    public void remove(Role role) throws Exception;

    public void edit(Role role) throws Exception;

    public Role find(Role role) throws Exception;

    public PageInfo findRolePage(String sql, int form, int size, Object[] parameters) throws Exception;

    public RowList findRoleRowList(String sql, Object[] parameters) throws Exception;

}

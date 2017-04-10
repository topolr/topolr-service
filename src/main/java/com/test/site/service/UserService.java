package com.test.site.service;

import com.axes.mvc.data.dao.MysqlDao.PageInfo;
import com.test.site.models.User;

public interface UserService {

    public User getUser(User user) throws Exception;
    
    public User addUser(User user) throws Exception;

    public void deleteUser(User user) throws Exception;

    public void editUser(User user) throws Exception;

    public PageInfo getUserPage(String sql, int from, int size, Object[] parlist) throws Exception;
    
    public User login(String username,String password) throws Exception;
    
    public void deleteUsers(String ids) throws Exception;
}

package com.test.site.service.serviceImp;

import com.axes.mvc.annotation.Dao;
import com.axes.mvc.annotation.Service;
import com.axes.mvc.annotation.Transation;
import com.axes.mvc.data.dao.MysqlDao;
import com.axes.mvc.data.dao.MysqlDao.PageInfo;
import com.axes.util.base.reflect.ObjectSnooper;
import com.test.site.models.User;
import com.test.site.service.UserService;
import java.util.HashMap;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFConditionalFormatting;

@Service(name = "user")
public class UserServiceImp implements UserService {

    @Dao
    private MysqlDao dao;

    @Override
    public User getUser(User user) throws Exception {
        return this.dao.query(user);
    }

    @Override
    @Transation
    public User addUser(User user) throws Exception {
        return this.dao.insert(user);
    }

    @Override
    @Transation
    public void deleteUser(User user) throws Exception {
        this.dao.delete(user);
    }

    @Override
    @Transation
    public void editUser(User user) throws Exception {
        this.dao.update(user);
    }

    @Override
    public PageInfo getUserPage(String sql, int from, int size, Object[] parlist) throws Exception {
        return this.dao.queryPage(sql, from, size, parlist);
    }

    @Override
    public User login(String username, String password) throws Exception {
        String sql = "select * from user where username=? and password=?";
        List<HashMap<String, Object>> list = this.dao.queryList(sql, new Object[]{username, password});
        if (list.size() > 0) {
            User u = new User();
            return (User) ObjectSnooper.snoop(u).set(list.get(0)).object();
        } else {
            return null;
        }
    }

    @Override
    @Transation
    public void deleteUsers(String ids) throws Exception {
        String[] t = ids.split(",");
        for (String id : t) {
            User user = new User();
            user.setId(Integer.parseInt(id));
            this.dao.delete(user);
        }
    }
}

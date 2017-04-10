package com.test.site.service.serviceImp;

import com.axes.mvc.annotation.Dao;
import com.axes.mvc.annotation.Service;
import com.axes.mvc.data.dao.MysqlDao;
import com.axes.mvc.data.dao.MysqlDao.RowList;
import com.test.site.service.MenuService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service(name = "menu")
public class MenuServiceImp implements MenuService {

    @Dao
    private MysqlDao dao;

    @Override
    public List<HashMap<String, Object>> getDefaultMenu() throws Exception {
        RowList row = this.dao.queryRowList("select * from menu", null);
        List<HashMap<String, Object>> rows = row.getRows();
        List<HashMap<String, Object>> result = new ArrayList<>();
        HashMap<String, HashMap<String, Object>> map = new HashMap<>();
        for (HashMap<String, Object> mp : rows) {
            List<HashMap<String, Object>> l = new ArrayList<>();
            mp.put("list", l);
            map.put(mp.get("id").toString(), mp);
        }
        for (HashMap<String, Object> mp : rows) {
            HashMap<String, Object> t = map.get(mp.get("pid").toString());
            if (null != t) {
                ((List<HashMap<String, Object>>) t.get("list")).add(mp);
            }
            if (mp.get("pid").toString().equals("0")) {
                result.add(mp);
            }
        }
        return result;
    }

}

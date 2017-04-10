package com.topolr.mvc.data.jdbc.pool;

import com.topolr.util.jsonx.Jsonx;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.util.HashMap;
import org.apache.log4j.Logger;

public class DBBoot {

    public static HashMap<String, String> map = new HashMap<String, String>();

    public DBBoot(Jsonx option) {
        try {
            Jsonx json = option;
            String urlis = json.get("jdbcUrl").toString();
            String drivclass = json.get("driverClass").toString();
            String useris = json.get("username").toString();
            String passwordis = json.get("password").toString();
            String minis = json.get("minPoolSize").toString();
            String maxis = json.get("maxPoolSize").toString();
            String tablePrefix = json.get("tablePrefix").toString();

            DBBoot.map.put("url", urlis);
            DBBoot.map.put("driver", drivclass);
            DBBoot.map.put("username", useris);
            DBBoot.map.put("password", passwordis);
            DBBoot.map.put("min", minis);
            DBBoot.map.put("max", maxis);
            DBBoot.map.put("tablePrefix", tablePrefix);

            ComboPooledDataSource ds = DBPool.getDataSource();
            ds.setDriverClass(drivclass);
            ds.setUser(useris);
            ds.setPassword(passwordis);
            ds.setMinPoolSize(Integer.parseInt(minis));
            ds.setMaxPoolSize(Integer.parseInt(maxis));
            ds.setJdbcUrl(urlis);
        } catch (Exception e) {
            Logger.getLogger(DBBoot.class).debug("Init the DBPool is failed");
        }
    }
}

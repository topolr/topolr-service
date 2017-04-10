package com.test.site.service.serviceImp;

import com.axes.mvc.annotation.Dao;
import com.axes.mvc.annotation.Service;
import com.axes.mvc.annotation.Transation;
import com.axes.mvc.data.dao.MysqlDao;
import com.axes.mvc.data.dao.MysqlDao.PageInfo;
import com.test.site.models.News;
import com.test.site.service.NewsService;
import java.util.HashMap;
import java.util.List;

@Service(name = "news")
public class NewsServiceImp implements NewsService {

    @Dao
    private MysqlDao dao;

    @Override
    @Transation
    public News add(News news) throws Exception {
        return this.dao.insert(news);
    }

    @Override
    @Transation
    public void remove(News news) throws Exception {
        this.dao.delete(news);
    }

    @Override
    @Transation
    public void edit(News news) throws Exception {
        this.dao.update(news);
    }

    @Override
    public News find(News news) throws Exception {
        return this.dao.query(news);
    }

    @Override
    public PageInfo findNewsPage(String sql, int from, int size, Object[] parameters) throws Exception {
        return this.dao.queryPage(sql, from, size, parameters);
    }

    @Override
    public void removeNews(String ids) throws Exception {
        String[] t = ids.split(",");
        for (String id : t) {
            News news = new News();
            news.setId(Integer.parseInt(id));
            this.dao.delete(news);
        }
    }

    @Override
    public HashMap<String, Object> find(String sql, Object[] parameters) throws Exception {
        List<HashMap<String,Object>> list=this.dao.queryList(sql, parameters);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

}

package com.topolr.contenter.page;

import com.topolr.contenter.data.ContentPage;
import java.util.HashMap;
import java.util.List;

public abstract class BaseContentPageProvider {

    public abstract HashMap<String, ContentPage> getAllContentPages();

    public abstract void saveContentPageList(List<ContentPage> pageList);

    public abstract void saveContentPage(ContentPage page);

    public abstract void removeContentPage(ContentPage page);

    public abstract void removeContentPageList(List<ContentPage> pageList);

    public abstract void editContentPage(ContentPage page);

    public abstract void removeAllContentPage();

}

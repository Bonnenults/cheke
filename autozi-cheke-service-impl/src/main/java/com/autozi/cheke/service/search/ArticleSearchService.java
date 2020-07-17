package com.autozi.cheke.service.search;

import com.autozi.common.search.es.entity.SearchKeyWords;
import com.autozi.common.search.es.service.SearchKeyWordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wang-lei on 2018/1/4.
 */
@Service
public class ArticleSearchService implements IArticleSearchService {

    @Autowired
    private SearchKeyWordsService searchKeyWordsService;


    @Override
    public int indexKeyWrods(SearchKeyWords keyWords) {
        return searchKeyWordsService.indexKeyWrods(keyWords);
    }


    @Override
    public List<SearchKeyWords> findByTitle(String title) {
        return searchKeyWordsService.findByTitle(title);
    }

    @Override
    public int deleteTitle(Long id) {
        return searchKeyWordsService.deleteTitle(id);
    }
}

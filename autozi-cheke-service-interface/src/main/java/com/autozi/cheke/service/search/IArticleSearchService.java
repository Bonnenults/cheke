package com.autozi.cheke.service.search;

import com.autozi.common.search.es.entity.SearchKeyWords;

import java.util.List;

/**
 * Created by wang-lei on 2018/1/4.
 */
public interface IArticleSearchService {
    int indexKeyWrods(SearchKeyWords keyWords);
    List<SearchKeyWords> findByTitle(String title);
    int deleteTitle(Long id);
}

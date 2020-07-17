package com.autozi.common.search.es.repository;

import com.autozi.common.search.es.entity.SearchKeyWords;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by wang-lei on 2017/12/28.
 */
public interface SearchKeyWordsRepository extends PagingAndSortingRepository<SearchKeyWords,Long> {

    /**
     * 根据输入title匹配
     * @param title
     * @return
     */
    List<SearchKeyWords> findByTitle(String title);

}

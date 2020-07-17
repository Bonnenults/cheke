package com.autozi.common.search.es.service;

import com.autozi.common.search.es.entity.SearchKeyWords;
import com.autozi.common.search.es.repository.SearchKeyWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wang-lei on 2017/12/28.
 */
@Service
public class SearchKeyWordsService {

    @Autowired
    private SearchKeyWordsRepository searchKeyWordsRepository;


    public int indexKeyWrods(SearchKeyWords keyWords) {
        try {
            searchKeyWordsRepository.save(keyWords);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }


    public List<SearchKeyWords> findByTitle(String title) {
        return searchKeyWordsRepository.findByTitle(title);
    }


    public int deleteTitle(Long id){
        try{
            searchKeyWordsRepository.delete(id);
            return 1;
        }catch(Exception ex){
            return 0;
        }
    }


}

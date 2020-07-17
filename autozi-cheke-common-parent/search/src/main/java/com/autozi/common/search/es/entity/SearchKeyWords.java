package com.autozi.common.search.es.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * Created by wang-lei on 2017/12/28.
 */
@Document(indexName = "ck_search_keyword",replicas=1,shards=6,type="article")
public class SearchKeyWords implements Serializable{

    @Id
    @Field(index= FieldIndex.not_analyzed)
    private Long id;

    @Field(index = FieldIndex.analyzed,searchAnalyzer="ik",analyzer="ik",store=true, type=FieldType.String)
    private String title;

    @Field(index = FieldIndex.analyzed,searchAnalyzer="ik",analyzer="ik",store=true, type=FieldType.String)
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}

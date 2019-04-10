package com.taotao.search.dao;

import org.apache.solr.client.solrj.SolrQuery;

import com.taotao.search.pojo.SearchResult;

public interface ItemSearchDao {

	SearchResult searchItem(SolrQuery solrQuery) throws Exception;
}

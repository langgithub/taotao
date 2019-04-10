package com.taotao.search.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.pojo.SearchResult;

public interface ItemService {

	TaotaoResult importItemToIndex() throws Exception;
	SearchResult searchItem(String queryString, Integer page) throws Exception;
}

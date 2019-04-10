package com.taotao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.ExceptionUtil;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.ItemService;

@Controller
@RequestMapping("/search")
public class ItemSearchController {
	
	@Autowired
	private ItemService itemSearchService;

	@RequestMapping("/query")
	@ResponseBody
	public TaotaoResult search(@RequestParam(value = "q") String queryString,
			@RequestParam(value = "page", defaultValue = "1") Integer page) {
		
		System.out.println("==============查询");
		if (StringUtils.isBlank(queryString)) {
			return TaotaoResult.build(400, "查询条件是必须的参数");
		}
		SearchResult result = null;
		try {
			queryString=new String(queryString.getBytes("ISO8859-1"),"utf-8");
			result = itemSearchService.searchItem(queryString, page);
			 
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		return TaotaoResult.ok(result);
	}
}


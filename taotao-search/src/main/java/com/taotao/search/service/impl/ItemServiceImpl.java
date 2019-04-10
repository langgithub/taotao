package com.taotao.search.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.dao.ItemSearchDao;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${SEARCH_RESULT_PAGE_SIZE}")
	private Integer PAGE_SIZE;
	@Autowired
	private ItemSearchDao itemSearchDao;
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	
	@Override
	public TaotaoResult importItemToIndex() throws Exception {
		//查询商品列表
		List<Item> itemList = itemMapper.getItemList();
		//将商品列表导入solr
		for (Item item : itemList) {
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", item.getId());
			document.addField("item_title", item.getTitle());
			document.addField("item_sell_point", item.getSell_point());
			document.addField("item_price", item.getPrice());
			document.addField("item_image", item.getImage());
			document.addField("item_category_name", item.getCategory_name());
			//将文档写入索引库
			solrServer.add(document);
		}
		//提交修改
		solrServer.commit();
		return TaotaoResult.ok();
	}

	@Override
	public SearchResult searchItem(String queryString, Integer page) throws Exception {
		//创建一个查询对象
		SolrQuery solrQuery = new SolrQuery();
		//查询条件
		if (StringUtils.isBlank(queryString)) {
			solrQuery.setQuery("*:*");
		} else {
			solrQuery.setQuery(queryString);
		}
		//分页条件
		if (page == null) {
			page = 1;
		}
		solrQuery.setStart((page -1) * PAGE_SIZE);
		solrQuery.setRows(PAGE_SIZE);
		//高亮显示
		solrQuery.setHighlight(true);
		//设置高亮显示的域
		solrQuery.addHighlightField("item_title");
		//高亮显示前缀
		solrQuery.setHighlightSimplePre("<em style=\"color:red\">");
		//后缀
		solrQuery.setHighlightSimplePost("</em>");
		//设置默认搜索域
		solrQuery.set("df", "item_keywords");
		
		//执行查询
		SearchResult result = itemSearchDao.searchItem(solrQuery);
		//计算分页
		Long recordCount = result.getRecordCount();
		int pageCount = (int) (recordCount / PAGE_SIZE);
		if (recordCount % PAGE_SIZE > 0) {
			pageCount++;
		}
		result.setPageCount(pageCount);
		result.setCurPage(page);
		
		return result;
	}


}

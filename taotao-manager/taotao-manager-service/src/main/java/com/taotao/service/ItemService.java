package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
/**
 * 
 * @author lang
 *
 */
public interface ItemService {

	TbItem getItemById(long itemId);
	EUDataGridResult getItemList(int page, int rows);
	TaotaoResult saveItem(TbItem item, String desc,String itemParam) throws Exception;
}

package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

/**
 * 内容管理接口
 * @author lang
 *
 */
public interface ContentService {

	//返回对象
	public EUDataGridResult getContentList(Long categoryId,int page, int rows);
	//
	//表单保存
	public TaotaoResult insertContent(TbContent content);
}

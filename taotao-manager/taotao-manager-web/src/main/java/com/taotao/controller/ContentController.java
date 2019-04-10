package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

/**
 * 内容管理
 * @author lang
 *
 */
@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping(value="/query/list")
	@ResponseBody
	public EUDataGridResult getList(Long categoryId,int page, int rows){
		System.out.println(categoryId+":"+page+":"+rows);
		return contentService.getContentList(categoryId, page, rows);
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		return result;
	}

}

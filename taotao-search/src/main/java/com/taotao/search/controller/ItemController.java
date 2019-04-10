package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.ExceptionUtil;
import com.taotao.search.service.ItemService;

@Controller
@RequestMapping("/manager")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/importall")
	@ResponseBody
	public TaotaoResult importAll() {
		TaotaoResult result = null;
		try {
			result = itemService.importItemToIndex();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return result;
	}
}


package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
    @ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId, String name) {
		TaotaoResult result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteContentgory(Long id){
        return contentCategoryService.deleteContentCategory(id);
	}
	@RequestMapping("update")
	@ResponseBody
	public TaotaoResult updateContentgory(Long id,String name){
		System.out.println(id+":"+name);
		return contentCategoryService.updateContentCategory(id,name);
	}
	

}


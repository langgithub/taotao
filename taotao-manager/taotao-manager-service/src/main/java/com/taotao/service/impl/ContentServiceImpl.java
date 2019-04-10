package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.HttpClientUtil;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.ContentService;

/**
 * 内容管理接口实现
 * @author lang
 *
 */
@Service
public class ContentServiceImpl implements ContentService{

	
	@Autowired 
	TbContentMapper tbContentMapper;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	/**
	 * 获取内容list
	 */
	@Override
	public EUDataGridResult getContentList(Long categoryId,int page, int rows) {
		TbContentExample example=new TbContentExample();
		PageHelper.startPage(page, rows);
		Criteria criteria=example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list=tbContentMapper.selectByExample(example);
		
		EUDataGridResult result=new EUDataGridResult();
		result.setRows(list);
		//取记录总条数
		PageInfo<TbContent> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public TaotaoResult insertContent(TbContent content) {
		//补全pojo内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		tbContentMapper.insert(content);
		
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok();

	}

}

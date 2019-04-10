package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService{

	private Logger logger=Logger.getLogger(ContentCategoryServiceImpl.class);
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		//根据parentid查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			//创建一个节点
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			
			resultList.add(node);
		}
		return resultList;
	}
	@Override
	public TaotaoResult insertContentCategory(long parentId, String name) {
		logger.info("------------------insertContentCategory("+parentId+","+name+")-------------------");
		//创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		//'状态。可选值:1(正常),2(删除)',
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//添加记录
		contentCategoryMapper.insert(contentCategory);
		//查看父节点的isParent列是否为true，如果不是true改成true
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
		//判断是否为true
		if(!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		//返回结果
		return TaotaoResult.ok(contentCategory);

	}
	/**
	 * 主键删除
	 */
	@Override
	public TaotaoResult deleteContentCategory(long Id) {
        //由于parentId有错 所有就先查询
		System.out.println("-------------------deleteContentCategory("+Id+")------------------");
		TbContentCategory tbContentCategoryForParentID =contentCategoryMapper.selectByPrimaryKey(Id);
		
		//根据parentid 先到id
		TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(tbContentCategoryForParentID.getParentId());
		//根据找到的id查看查看是否有多个parentId，如果自有一个子节点那么该isParent要设为0
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(tbContentCategoryForParentID.getParentId());
		List<TbContentCategory> list =contentCategoryMapper.selectByExample(example);
		if(list.size()>0){
			parentCat.setIsParent(false);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCat);
		}
		
		//根据删除节点的id 查询哪些父节点等于id， 将他们都删除
		TbContentCategoryExample example2 = new TbContentCategoryExample();
		Criteria criteria2 = example2.createCriteria();
		criteria2.andParentIdEqualTo(Id);
		
		List<TbContentCategory> list2 =contentCategoryMapper.selectByExample(example2);
		for (TbContentCategory tbContentCategory : list2) {
			contentCategoryMapper.deleteByPrimaryKey(tbContentCategory.getId());
		}
		
		contentCategoryMapper.deleteByPrimaryKey(Id);

		return TaotaoResult.ok();
	}
	/**
	 * 跟新节点
	 */
	@Override
	public TaotaoResult updateContentCategory(long Id, String name) {
		logger.info("==========id:"+Id+" name:"+name);
		
		TbContentCategory category=contentCategoryMapper.selectByPrimaryKey(Id);
		category.setName(name);
		contentCategoryMapper.updateByPrimaryKey(category);
		return TaotaoResult.ok();
	}

}

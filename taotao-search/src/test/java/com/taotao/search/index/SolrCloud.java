package com.taotao.search.index;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloud {

	@Test
	public void addDocument() throws SolrServerException, IOException{
		//创建一个solr连接
		String zkHost = "192.168.71.128:2181,192.168.71.128:2182,192.168.71.128:2183";
	    CloudSolrServer solrServer=new CloudSolrServer(zkHost);
	    //设置默认的collection
	    solrServer.setDefaultCollection("collection2");
	    //创建一个文档对象
	    SolrInputDocument document=new SolrInputDocument();
		//文档对象中添加域
	    document.addField("id", "test001");
	    document.addField("name", "title");
	    //document.addField("item_content", "content");
		//把文档加到索引库
	    solrServer.add(document);
		//提交
	    solrServer.commit();
	}
	@Test
	public void deletDocument() throws Exception{
		//创建一个solr连接
		String zkHost = "192.168.71.128:2181,192.168.71.128:2182,192.168.71.128:2183";
	    CloudSolrServer solrServer=new CloudSolrServer(zkHost);
	    //设置默认的collection
	    solrServer.setDefaultCollection("collection2");
	    solrServer.deleteByQuery("*:*");
	    solrServer.commit();
	}
}

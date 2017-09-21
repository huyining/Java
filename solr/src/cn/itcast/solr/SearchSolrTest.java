package cn.itcast.solr;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

/**
 * @author ning
 *
 */
public class SearchSolrTest {
	@Test
	public void test() throws Exception {
		//1.创建连接
		HttpSolrServer solrServer = new  HttpSolrServer("http://localhost:8080/solr");
		//2.创建查询对象
		SolrQuery query = new SolrQuery();
		//3.添加查询条件
		query.setQuery("*:*");
		//4.根据这个链接对象和查询条件对象执行查询,返回响应
		QueryResponse queryResponse = solrServer.query(query);
		//5.根据响应获取结果集,遍历结果集:SolrDocument
		SolrDocumentList list = queryResponse.getResults();
		//打印总记录数
		System.out.println(list.getNumFound());
		for (SolrDocument doc : list) {
			System.err.println(doc.get("id")+"=="+doc.get("name"));
			
		}
	}

}

package cn.itcast.solr;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

/**
 * @author ning
 *
 */
public class HightSearchSolrTest {

	@Test
	public void testHightSearch() throws Exception {
		//1.创建连接
		HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr");
		//2.创建查询对象
		SolrQuery query = new SolrQuery();
		//3.设置默认搜索域
		query.setQuery("台灯");
		query.set("df", "product_keywords");
		query.setFilterQueries("product_catalog_name:雅致灯饰");
		query.setFilterQueries("product_price:[20 TO 30]");
		query.setSort("product_price",ORDER.desc);
		//分页
		query.setStart(0);
		query.setRows(10);
		//开启高亮
		query.setHighlight(true);
		query.addHighlightField("product_name");
		//设置前缀
		query.setHighlightSimplePre("<span style=\"color:red\">");
		//设置后缀
		query.setHighlightSimplePost("</span>");
		// 4.根据链接对象和查询条件对象执行查询,返回响应
		QueryResponse queryResponse = solrServer.query(query);
		// 5.根据响应获取结果集,遍历结果集:SolrDocument
		SolrDocumentList documentList = queryResponse.getResults();
		// 打印总记录数
		System.out.println(documentList.getNumFound());
		for (SolrDocument doc : documentList) {
			String productname = "";
			//获取高亮
			Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
			List<String> list = highlighting.get(doc.get("id")).get("product_name");
			if(list != null){
				productname = list.get(0);
			}else{
				productname = String.valueOf(doc.get("product_name"));
			}
			System.out.println("==id=="+doc.get("id"));
			System.out.println("==product_name=="+productname);
			System.out.println("==product_catalog_name=="+doc.get("product_catalog_name"));
			System.out.println("==product_price=="+doc.get("product_price"));
		}
	}

}

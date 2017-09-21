package cn.itcast.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @author ning
 *
 */
public class IndexSorJTest {
	/**
	 * Solr操作Solr服务器之添加&修改
	 * 
	 * @throws Exception
	 * @throws SolrServerException
	 */
	@Test
	public void test() throws SolrServerException, Exception {
		// 1链接Solr服务器
		HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
		// 2创建文档对象SolrInputDocument,里面有Filed存放数据
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "b003");
		doc.addField("name", "老子");
		// 3根据这个创建连接的对象写入文档
		solrServer.add(doc);
		// 4提交
		solrServer.commit();

	}

	/**
	 * Solr操作Solr服务器之删除
	 * 
	 * @throws Exception
	 * @throws SolrServerException
	 */
	@Test
	public void testName() throws Exception {
		// 1创建SolrServer链接对象
		HttpSolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/");
		// 2根据id参数
		// solrServer.deleteById("b001");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
}

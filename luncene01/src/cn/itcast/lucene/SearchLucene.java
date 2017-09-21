package cn.itcast.lucene;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @author ning
 *
 */
public class SearchLucene {
	@SuppressWarnings("resource")
	@Test
	public void test() throws Exception {
		// 1.创建分词器对象
		//StandardAnalyzer analyzer = new StandardAnalyzer();
		IKAnalyzer analyzer = new IKAnalyzer();
		// 2.创建解析器对象Queryparser(): 需要分词器对象
		QueryParser parser = new QueryParser("name", analyzer);
		// 3.根据解析对象创建查询条件对象
		Query query = parser.parse("desc:编程");
		// 4.创建索引库地址流对象
		Directory directory = FSDirectory.open(new File("F:\\workspaces\\eclipse\\temp"));
		// 5.根据这个流读取索引库来(索引和文档)
		IndexReader indexReader = DirectoryReader.open(directory);
		// 6.更据读取的对象创建IndexSearch搜索对象
		IndexSearcher searcher = new IndexSearcher(indexReader);
		// 7.根据搜索对象和查询条件来进行查询:
		TopDocs topDocs = searcher.search(query, 2);
		// 总记录数
		System.err.println("总记录数" + topDocs.totalHits);
		// 8.根据查出的结果集来获取坐标:数组形式
		ScoreDoc[] docs = topDocs.scoreDocs;
		// 9.遍历
		for (ScoreDoc scoreDoc : docs) {
			System.out.println("===");
			// 10.获取坐标下的文档
			int docId = scoreDoc.doc;
			// 11.根据文档ID,根据IndexSearch对象来查询文档
			Document doc = searcher.doc(docId);
			System.err.println("====id====" + doc.get("id"));
			System.err.println("====name====" + doc.get("name"));
			System.err.println("====price====" + doc.get("price"));
			System.err.println("====pic====" + doc.get("pic"));
		}

		// 13、释放资源
		indexReader.close();

	}
}

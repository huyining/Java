package cn.itcast.lucene;

import java.io.File;

import javax.swing.plaf.synth.SynthScrollBarUI;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * @author ning
 *
 */

public class QueryLucene {
	
	/**
	 * 演示Query子类的查询之TermQuery
	 */
	@Test
	public void test() throws Exception {
      Query query = new TermQuery(new Term("name","java"));
      //指定查询
      search(query);
	}
	
	/**
	 * Query子类查询NumericRangeQuery
	 * 根据数值类型的查询
	 * @throws Exception 
	 */
	@Test
	public void testName() throws Exception {
		Query query = NumericRangeQuery.newFloatRange("price", 55f, 70f, true,false);
		search(query);
				
	}
	
	/**
	 * Query子类组合查询
	 * BooleanQuery
	 * @throws Exception 
	 */
	@Test
	public void testBooleanQuery() throws Exception {
		//1.根据关键字查询
		Query query1 = new TermQuery(new Term("name", "spring"));
		//2.根据价格查询
		Query query2 = NumericRangeQuery.newFloatRange("price", 55f, 70f, true,false);
		
		//组合
		BooleanQuery query = new BooleanQuery();
		//组合
		query.add(query1, Occur.MUST);
		query.add(query2, Occur.MUST);
		System.out.println("========" + query);
		
		//执行查询
		search(query);
	}
	
	/**
	 * 多个域的查询
	 * @throws Exception 
	 * 
	 */
	@Test
	public  void testMultiFieldQueryParser() throws Exception {
		//分词器
		IKAnalyzer analyzer = new IKAnalyzer();
		//域数组
		String[] fields = {"name","desc"}; 
		MultiFieldQueryParser queryParser = new MultiFieldQueryParser(fields, analyzer);
		Query query = queryParser.parse("java");
		System.out.println("===="+ query);
		//执行
		search(query);
	}

	public void search(Query query) throws Exception {
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

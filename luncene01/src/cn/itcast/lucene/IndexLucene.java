package cn.itcast.lucene;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.itcast.dao.BookDao;
import cn.itcast.dao.impl.BookDaoImpl;
import cn.itcast.pojo.Book;

/**
 * @author ning
 *
 */
public class IndexLucene {

	@SuppressWarnings("resource")
	@Test
	public void test() throws Exception {
		// 1.数据的采集
		BookDao daoImpl = new BookDaoImpl();
		List<Book> list = daoImpl.findBookList();
		// 创建文档集合对象
		List<Document> docList = new ArrayList<>();
		for (Book book : list) {
			// 2.创建文档对象
			Document doc = new Document();
			// 3.创建域Filed对象,将数据放到Field中
			Field idField = new StringField("id", book.getId() + "", Store.YES);
			Field nameField = new TextField("name", book.getName() + "", Store.YES);
			Field priceField = new FloatField("price", book.getPrice(), Store.YES);
			Field picField = new StoredField("pic", book.getPic() + "");
			Field descField = new TextField("desc", book.getDesc() + "", Store.YES);
			// 4.将域对象放在文档中
			doc.add(idField);
			doc.add(nameField);
			doc.add(priceField);
			doc.add(picField);
			if (book.getId()==4) {
				descField.setBoost(100000f);
			}
			doc.add(descField);
			docList.add(doc);
		}

		// 5.创建分词器
		//StandardAnalyzer analyzer = new StandardAnalyzer();
		 IKAnalyzer analyzer = new IKAnalyzer();
		// 6.创建指定的索引库地址流对象
		Directory directory = FSDirectory.open(new File("F:\\workspaces\\eclipse\\temp"));
		// 7.创建写入索引库的配置对象
		IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LUCENE_4_10_3, analyzer);
		// 8.创建 写入索引库对象IndexWriter
		IndexWriter indexWriter = new IndexWriter(directory, writerConfig);
		// 9.根据这个写入索引库的对象将文档写入索引库
		for (Document document : docList) {
			indexWriter.addDocument(document);
		}
		// 10.资源释放
		indexWriter.close();
	}

}

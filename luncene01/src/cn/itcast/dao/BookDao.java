package cn.itcast.dao;

import java.util.List;

import cn.itcast.pojo.Book;

/**
 * @author ning
 *
 */
public interface BookDao {

	List<Book> findBookList();

}
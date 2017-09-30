package ren.xiayi.bookeeper.dao.book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import ren.xiayi.bookeeper.entity.book.Book;

public interface BookDao extends PagingAndSortingRepository<Book, Long>, JpaSpecificationExecutor<Book> {

	List<Book> findByUrl(String bookUrl);

}
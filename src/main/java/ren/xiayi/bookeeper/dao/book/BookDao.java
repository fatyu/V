package ren.xiayi.bookeeper.dao.book;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import ren.xiayi.bookeeper.entity.book.Book;

public interface BookDao extends PagingAndSortingRepository<Book, Long>, JpaSpecificationExecutor<Book> {

	Book findByUrl(String bookUrl);

}
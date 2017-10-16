package cc.notalk.v.dao.book;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cc.notalk.v.entity.book.BookIndex;

public interface BookIndexDao extends PagingAndSortingRepository<BookIndex, Long>, JpaSpecificationExecutor<BookIndex> {

}
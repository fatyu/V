package ren.xiayi.bookeeper.dao.book;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import ren.xiayi.bookeeper.entity.book.BookUrl;

public interface BookUrlDao extends PagingAndSortingRepository<BookUrl, Long>, JpaSpecificationExecutor<BookUrl> {

}
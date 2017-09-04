package ren.xiayi.bookeeper.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import ren.xiayi.bookeeper.entity.Book;

public interface BookDao extends PagingAndSortingRepository<Book, Long>, JpaSpecificationExecutor<Book> {

}
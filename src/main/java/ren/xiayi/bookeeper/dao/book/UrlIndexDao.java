package ren.xiayi.bookeeper.dao.book;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import ren.xiayi.bookeeper.entity.book.UrlIndex;

public interface UrlIndexDao extends PagingAndSortingRepository<UrlIndex, Long>, JpaSpecificationExecutor<UrlIndex> {

}
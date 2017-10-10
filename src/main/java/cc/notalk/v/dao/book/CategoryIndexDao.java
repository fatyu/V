package cc.notalk.v.dao.book;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cc.notalk.v.entity.book.CategoryIndex;

public interface CategoryIndexDao
		extends PagingAndSortingRepository<CategoryIndex, Long>, JpaSpecificationExecutor<CategoryIndex> {

}
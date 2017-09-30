package ren.xiayi.bookeeper.dao.book;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import ren.xiayi.bookeeper.entity.book.CategoryIndex;

public interface CategoryIndexDao
		extends PagingAndSortingRepository<CategoryIndex, Long>, JpaSpecificationExecutor<CategoryIndex> {

}
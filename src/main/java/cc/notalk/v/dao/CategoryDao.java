package cc.notalk.v.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cc.notalk.v.entity.Category;

public interface CategoryDao extends PagingAndSortingRepository<Category, Long>, JpaSpecificationExecutor<Category> {

}
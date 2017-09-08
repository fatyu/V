package ren.xiayi.bookeeper.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import ren.xiayi.bookeeper.entity.SiteCategory;

public interface SiteCategoryDao
		extends PagingAndSortingRepository<SiteCategory, Long>, JpaSpecificationExecutor<SiteCategory> {

}
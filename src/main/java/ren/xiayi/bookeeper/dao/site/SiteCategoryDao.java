package ren.xiayi.bookeeper.dao.site;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import ren.xiayi.bookeeper.entity.site.SiteCategory;

public interface SiteCategoryDao
		extends PagingAndSortingRepository<SiteCategory, Long>, JpaSpecificationExecutor<SiteCategory> {

}
package cc.notalk.v.dao.site;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cc.notalk.v.entity.site.SiteCategory;

public interface SiteCategoryDao
		extends PagingAndSortingRepository<SiteCategory, Long>, JpaSpecificationExecutor<SiteCategory> {

}
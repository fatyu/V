package ren.xiayi.bookeeper.dao.site;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import ren.xiayi.bookeeper.entity.site.Site;

public interface SiteDao extends PagingAndSortingRepository<Site, Long>, JpaSpecificationExecutor<Site> {

}
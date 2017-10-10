package cc.notalk.v.dao.site;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import cc.notalk.v.entity.site.Site;

public interface SiteDao extends PagingAndSortingRepository<Site, Long>, JpaSpecificationExecutor<Site> {

}
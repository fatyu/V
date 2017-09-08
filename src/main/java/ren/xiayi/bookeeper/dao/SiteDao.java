package ren.xiayi.bookeeper.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import ren.xiayi.bookeeper.entity.Site;

public interface SiteDao extends PagingAndSortingRepository<Site, Long>, JpaSpecificationExecutor<Site> {

}
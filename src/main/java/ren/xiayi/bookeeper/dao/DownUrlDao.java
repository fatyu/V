package ren.xiayi.bookeeper.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import ren.xiayi.bookeeper.entity.DownUrl;

public interface DownUrlDao extends PagingAndSortingRepository<DownUrl, Long>, JpaSpecificationExecutor<DownUrl> {

}
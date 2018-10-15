package com.pure.service.repository;

import com.pure.service.domain.UserRegion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the UserRegion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRegionRepository extends JpaRepository<UserRegion, Long>, JpaSpecificationExecutor<UserRegion> {

    @Query("select user_region from UserRegion user_region where user_region.user.login = ?#{principal.username}")
    List<UserRegion> findByUserIsCurrentUser();

}

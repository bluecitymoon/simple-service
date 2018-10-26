package com.pure.service.repository;

import com.pure.service.domain.UserGuideDocument;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserGuideDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserGuideDocumentRepository extends JpaRepository<UserGuideDocument, Long>, JpaSpecificationExecutor<UserGuideDocument> {

}

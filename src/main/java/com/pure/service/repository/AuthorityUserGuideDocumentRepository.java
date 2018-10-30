package com.pure.service.repository;

import com.pure.service.domain.AuthorityUserGuideDocument;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AuthorityUserGuideDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorityUserGuideDocumentRepository extends JpaRepository<AuthorityUserGuideDocument, Long>, JpaSpecificationExecutor<AuthorityUserGuideDocument> {

}

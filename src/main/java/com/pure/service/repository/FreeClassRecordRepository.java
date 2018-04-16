package com.pure.service.repository;

import com.pure.service.domain.FreeClassRecord;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the FreeClassRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FreeClassRecordRepository extends JpaRepository<FreeClassRecord, Long>, JpaSpecificationExecutor<FreeClassRecord> {
    @Query("select distinct free_class_record from FreeClassRecord free_class_record left join fetch free_class_record.classCategories")
    List<FreeClassRecord> findAllWithEagerRelationships();

    @Query("select free_class_record from FreeClassRecord free_class_record left join fetch free_class_record.classCategories where free_class_record.id =:id")
    FreeClassRecord findOneWithEagerRelationships(@Param("id") Long id);

}

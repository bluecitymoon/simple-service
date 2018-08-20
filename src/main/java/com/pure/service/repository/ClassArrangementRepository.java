package com.pure.service.repository;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.service.dto.dto.ClassSchedule;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.time.Instant;
import java.util.List;


/**
 * Spring Data JPA repository for the ClassArrangement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClassArrangementRepository extends JpaRepository<ClassArrangement, Long>, JpaSpecificationExecutor<ClassArrangement> {

    List<ClassArrangement> findByClazz_Id(Long id);

    @Query(nativeQuery = true)
    List<ClassSchedule> getAllSchedules();

    @Query(nativeQuery = true)
    List<ClassSchedule> getAllSchedulesByRange(Instant start, Instant end);


}

package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.ClassArrangementRule;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.repository.ClassArrangementRepository;
import com.pure.service.service.ClassArrangementQueryService;
import com.pure.service.service.ClassArrangementService;
import com.pure.service.service.dto.ClassArrangementCriteria;
import com.pure.service.service.dto.dto.ClassArrangementWeekElement;
import com.pure.service.service.dto.dto.ClassSchedule;
import com.pure.service.service.dto.request.BatchReassignClassArrangement;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import com.pure.service.service.dto.request.StudentClassArrangementsRequest;
import com.pure.service.service.util.DateUtil;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ClassArrangement.
 */
@RestController
@RequestMapping("/api")
public class ClassArrangementResource {

    private final Logger log = LoggerFactory.getLogger(ClassArrangementResource.class);

    private static final String ENTITY_NAME = "classArrangement";

    private final ClassArrangementService classArrangementService;

    private final ClassArrangementQueryService classArrangementQueryService;

    @Autowired
    private ClassArrangementRepository arrangementRepository;

    public ClassArrangementResource(ClassArrangementService classArrangementService, ClassArrangementQueryService classArrangementQueryService) {
        this.classArrangementService = classArrangementService;
        this.classArrangementQueryService = classArrangementQueryService;
    }

    /**
     * POST  /class-arrangements : Create a new classArrangement.
     *
     * @param classArrangement the classArrangement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classArrangement, or with status 400 (Bad Request) if the classArrangement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-arrangements")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<ClassArrangement> createClassArrangement(@RequestBody ClassArrangement classArrangement) throws URISyntaxException {
        log.debug("REST request to save ClassArrangement : {}", classArrangement);
        if (classArrangement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new classArrangement cannot already have an ID")).body(null);
        }
        ClassArrangement result = classArrangementService.save(classArrangement);
        return ResponseEntity.created(new URI("/api/class-arrangements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/class-arrangements/batch-reassign")
    @Timed
//    @RegionBasedInsert
    public ResponseEntity<Void> reassignClassArrangements(@RequestBody BatchReassignClassArrangement request) {

        log.debug("REST request to reassign Class Arrangements : {}", request);
        classArrangementService.reassignClassArrangements(request);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/class-arrangements/batch-delete")
    @Timed
//    @RegionBasedInsert
    public ResponseEntity<Void> deleteClassArrangements(@RequestBody BatchReassignClassArrangement request) {

        log.debug("REST request to reassign Class Arrangements : {}", request);
        classArrangementService.deleteClassArrangements(request);

        return ResponseEntity.ok().build();
    }


    @GetMapping("/class-arrangements/generate-by-rule/{id}")
    @Timed
    public ResponseEntity<Void> createClassArrangementsByRule(@PathVariable Long id) {
        log.debug("REST request to generate arrangements by rule : {}", id);

        classArrangementService.createClassArrangementsByRule(id);

        return ResponseEntity.ok().build();
    }

    /**
     * PUT  /class-arrangements : Updates an existing classArrangement.
     *
     * @param classArrangement the classArrangement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classArrangement,
     * or with status 400 (Bad Request) if the classArrangement is not valid,
     * or with status 500 (Internal Server Error) if the classArrangement couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-arrangements")
    @Timed
    public ResponseEntity<ClassArrangement> updateClassArrangement(@RequestBody ClassArrangement classArrangement) throws URISyntaxException {
        log.debug("REST request to update ClassArrangement : {}", classArrangement);
        if (classArrangement.getId() == null) {
            return createClassArrangement(classArrangement);
        }
        ClassArrangement result = classArrangementService.save(classArrangement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classArrangement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-arrangements : get all the classArrangements.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of classArrangements in body
     */
    @GetMapping("/class-arrangements")
    @Timed
    public ResponseEntity<List<ClassArrangement>> getAllClassArrangements(ClassArrangementCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get ClassArrangements by criteria: {}", criteria);
        Page<ClassArrangement> page = classArrangementQueryService.findByCriteria(criteria, pageable);

        List<ClassArrangement> filteredClassArrangements = filterClassArrangements(page.getContent(), criteria);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-arrangements");
        return new ResponseEntity<>(filteredClassArrangements, headers, HttpStatus.OK);
    }

    @GetMapping("/class-arrangements/student-class-arrangements")
    @Timed
    public ResponseEntity<List<ClassArrangement>> findStudentArrangements(StudentClassArrangementsRequest request) {

        List<ClassArrangement> page = classArrangementService.findStudentArrangements(request);

        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    private List<ClassArrangement> filterClassArrangements(List<ClassArrangement> content, ClassArrangementCriteria criteria) {

        if (CollectionUtils.isEmpty(content)) {
            return content;
        }
        List<ClassArrangement> result = new ArrayList<>();

        String startTime = criteria.getStartTime(), endTime = criteria.getEndTime();
        if (StringUtils.isEmpty(startTime) && StringUtils.isEmpty(endTime)) {
            return content;
        }

        for (ClassArrangement classArrangement : content) {

            if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {

                if (sameTimeInstant(classArrangement.getStartDate(), startTime) && sameTimeInstant(classArrangement.getEndDate(), endTime)) {

                    result.add(classArrangement);
                }
            } else {

                result.add(classArrangement);
            }
        }

        return result;
    }

    private boolean sameTimeInstant(Instant targetInstant, String filterTime) {


        LocalDateTime localDateTime = LocalDateTime.ofInstant(targetInstant, DateUtil.defaultShanghaiZoneId);
        int hour = localDateTime.getHour(), minute = localDateTime.getMinute();

        String[] hourMinutes = filterTime.split(":");
        int targetHour = Integer.valueOf(hourMinutes[0]), targetMinute = Integer.valueOf(hourMinutes[1]);

        boolean isEquals = (hour == targetHour && minute == targetMinute);

        return isEquals;
    }
    @GetMapping("/class-arrangements/by-class-id/{id}")
    @Timed
    public ResponseEntity<List<ClassArrangement>> getAllClassArrangementsByProductId(@PathVariable Long id) {

        List<ClassArrangement> page = arrangementRepository.findByClazz_Id(id);
        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

//    @GetMapping("/class-arrangements/timefixup")
//    @Timed
//    public ResponseEntity fixupTime() {
//
//        classArrangementService.fixupClassArrangements();
//
//        return  ResponseEntity.ok().build();
//    }
//

    @GetMapping("/class-arrangements/today")
    @Timed
    public ResponseEntity<List<ClassArrangement>> getClassArrangementsToday() {

        Instant start = DateUtil.getSimpleTodayInstantBegin();
        Instant end = DateUtil.getSimpleTodayInstantEnd();

        ClassArrangementCriteria classArrangementCriteria = new ClassArrangementCriteria();

        InstantFilter instantFilter = new InstantFilter();
        instantFilter.setLessOrEqualThan(end);
        instantFilter.setGreaterOrEqualThan(start);
        classArrangementCriteria.setStartDate(instantFilter);

        List<ClassArrangement> page = classArrangementQueryService.findByCriteria(classArrangementCriteria);

        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    @GetMapping("/class-arrangements/all")
    @Timed
    public ResponseEntity<List<ClassSchedule>> getAllClassSchedules() {

        List<ClassSchedule> page = arrangementRepository.getAllSchedules();

        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    @PostMapping("/class-arrangements/get-by-range")
    @Timed
    public ResponseEntity<List<ClassSchedule>> searchSchedulesInRange(@RequestBody CustomerStatusRequest customerStatusRequest) {

            switch (customerStatusRequest.getQueryType()) {
                case "monthly":

                    if (StringUtils.isEmpty(customerStatusRequest.getYear()) || StringUtils.isEmpty(customerStatusRequest.getMonth())) {
                        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "conditionneeded", "请输入搜索条件")).body(null);
                    }

                    preProccessStatusRequest(customerStatusRequest);

                    break;
                case "dateRange":

                    if (StringUtils.isEmpty(customerStatusRequest.getStartDate()) || StringUtils.isEmpty(customerStatusRequest.getEndDate())) {
                        return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "conditionneeded", "请输入搜索条件")).body(null);
                    }
                    break;
                default:
                    break;
            }
        List<ClassSchedule> page = classArrangementService.searchSchedulesInRange(customerStatusRequest);

        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    @PostMapping("/class-arrangements/create-schedule")
    @Timed
    public ResponseEntity createClassSchedule(@RequestBody ClassArrangementRule rule) {

        classArrangementService.createClassSchedule(rule);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/class-arrangements/get-this-week")
    @Timed
    public ResponseEntity<List<ClassArrangementWeekElement>> getArrangementsInCurrentWeek() {

        List<ClassArrangementWeekElement> page = classArrangementService.getArrangementsInCurrentWeek();

        return new ResponseEntity<>(page, null, HttpStatus.OK);
    }

    @PostMapping("/class-arrangements/testInstantInDifferentServers")
    public ResponseEntity<String> testInstantInDifferentServers() {

        Instant now = Instant.now();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(now, ZoneId.of("Asia/Shanghai"));

        return ResponseEntity.ok(now.toString());
    }


    private void preProccessStatusRequest(CustomerStatusRequest customerStatusRequest) {

        Integer month = customerStatusRequest.getMonth();
        Integer year = customerStatusRequest.getYear();

        Integer nextMonth = month + 1;
        Integer nextYear = year;
        if (nextMonth == 13) {
            nextMonth = 1;

            nextYear = year + 1;
        }

        String monthString = "" + month;
        String nextMonthString = "" + nextMonth;
        if (month < 10) {
            monthString = "0" + monthString;
        }

        if (nextMonth < 10) {
            nextMonthString = "0" + nextMonthString;
        }

        String fullDateStart = "" + year+ "-" + monthString + "-01T00:00:01.00Z";
        String fullDateEnd = "" + nextYear + "-" + nextMonthString + "-01T00:00:01.00Z";

        customerStatusRequest.setStartDate(Instant.parse(fullDateStart));
        customerStatusRequest.setEndDate(Instant.parse(fullDateEnd));
    }

    /**
     * GET  /class-arrangements/:id : get the "id" classArrangement.
     *
     * @param id the id of the classArrangement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classArrangement, or with status 404 (Not Found)
     */
    @GetMapping("/class-arrangements/{id}")
    @Timed
    public ResponseEntity<ClassArrangement> getClassArrangement(@PathVariable Long id) {
        log.debug("REST request to get ClassArrangement : {}", id);
        ClassArrangement classArrangement = classArrangementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(classArrangement));
    }

    /**
     * DELETE  /class-arrangements/:id : delete the "id" classArrangement.
     *
     * @param id the id of the classArrangement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-arrangements/{id}")
    @Timed
    public ResponseEntity<Void> deleteClassArrangement(@PathVariable Long id) {
        log.debug("REST request to delete ClassArrangement : {}", id);
        classArrangementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

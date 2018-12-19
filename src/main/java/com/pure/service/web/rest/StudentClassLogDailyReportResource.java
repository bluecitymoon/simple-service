package com.pure.service.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.pure.service.domain.StudentClassLogDailyReport;
import com.pure.service.region.RegionBasedInsert;
import com.pure.service.region.RegionBasedQuery;
import com.pure.service.service.StudentClassLogDailyReportQueryService;
import com.pure.service.service.StudentClassLogDailyReportService;
import com.pure.service.service.dto.StudentClassLogDailyReportCriteria;
import com.pure.service.service.dto.dto.StatusBasedStudent;
import com.pure.service.service.dto.dto.StudentClassLogMonthlyReport;
import com.pure.service.service.dto.request.CustomerStatusRequest;
import com.pure.service.service.dto.request.SingleDateRequest;
import com.pure.service.web.rest.util.HeaderUtil;
import com.pure.service.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StudentClassLogDailyReport.
 */
@RestController
@RequestMapping("/api")
public class StudentClassLogDailyReportResource {

    private final Logger log = LoggerFactory.getLogger(StudentClassLogDailyReportResource.class);

    private static final String ENTITY_NAME = "studentClassLogDailyReport";

    private final StudentClassLogDailyReportService studentClassLogDailyReportService;

    private final StudentClassLogDailyReportQueryService studentClassLogDailyReportQueryService;

    public StudentClassLogDailyReportResource(StudentClassLogDailyReportService studentClassLogDailyReportService, StudentClassLogDailyReportQueryService studentClassLogDailyReportQueryService) {
        this.studentClassLogDailyReportService = studentClassLogDailyReportService;
        this.studentClassLogDailyReportQueryService = studentClassLogDailyReportQueryService;
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

    @PostMapping("/student-class-log-daily-reports/monthly")
    @Timed
    public ResponseEntity<List<StudentClassLogMonthlyReport>> getMonthlyStudentClassLogDailyReport(@RequestBody CustomerStatusRequest customerStatusRequest) {

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
        List<StudentClassLogMonthlyReport> report = studentClassLogDailyReportService.getMonthlyReport(customerStatusRequest);

        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    /**
     * POST  /student-class-log-daily-reports : Create a new studentClassLogDailyReport.
     *
     * @param studentClassLogDailyReport the studentClassLogDailyReport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentClassLogDailyReport, or with status 400 (Bad Request) if the studentClassLogDailyReport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-class-log-daily-reports")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<StudentClassLogDailyReport> createStudentClassLogDailyReport(@RequestBody StudentClassLogDailyReport studentClassLogDailyReport) throws URISyntaxException {
        log.debug("REST request to save StudentClassLogDailyReport : {}", studentClassLogDailyReport);
        if (studentClassLogDailyReport.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new studentClassLogDailyReport cannot already have an ID")).body(null);
        }
        StudentClassLogDailyReport result = studentClassLogDailyReportService.save(studentClassLogDailyReport);
        return ResponseEntity.created(new URI("/api/student-class-log-daily-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/student-class-log-daily-reports/save-daily-report")
    @Timed
    @RegionBasedInsert
    public ResponseEntity<StudentClassLogDailyReport> saveLogDailyReport(@RequestBody StudentClassLogDailyReport studentClassLogDailyReport) throws URISyntaxException {

        StudentClassLogDailyReport result = studentClassLogDailyReportService.saveLogDailyReport(studentClassLogDailyReport);

        return ResponseEntity.created(new URI("/api/student-class-log-daily-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-class-log-daily-reports : Updates an existing studentClassLogDailyReport.
     *
     * @param studentClassLogDailyReport the studentClassLogDailyReport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentClassLogDailyReport,
     * or with status 400 (Bad Request) if the studentClassLogDailyReport is not valid,
     * or with status 500 (Internal Server Error) if the studentClassLogDailyReport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-class-log-daily-reports")
    @Timed
    public ResponseEntity<StudentClassLogDailyReport> updateStudentClassLogDailyReport(@RequestBody StudentClassLogDailyReport studentClassLogDailyReport) throws URISyntaxException {
        log.debug("REST request to update StudentClassLogDailyReport : {}", studentClassLogDailyReport);
        if (studentClassLogDailyReport.getId() == null) {
            return createStudentClassLogDailyReport(studentClassLogDailyReport);
        }
        StudentClassLogDailyReport result = studentClassLogDailyReportService.save(studentClassLogDailyReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentClassLogDailyReport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-class-log-daily-reports : get all the studentClassLogDailyReports.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of studentClassLogDailyReports in body
     */
    @GetMapping("/student-class-log-daily-reports")
    @Timed
    @RegionBasedQuery
    public ResponseEntity<List<StudentClassLogDailyReport>> getAllStudentClassLogDailyReports(StudentClassLogDailyReportCriteria criteria,@ApiParam Pageable pageable) {
        log.debug("REST request to get StudentClassLogDailyReports by criteria: {}", criteria);
        Page<StudentClassLogDailyReport> page = studentClassLogDailyReportQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-class-log-daily-reports");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /student-class-log-daily-reports/:id : get the "id" studentClassLogDailyReport.
     *
     * @param id the id of the studentClassLogDailyReport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentClassLogDailyReport, or with status 404 (Not Found)
     */
    @GetMapping("/student-class-log-daily-reports/{id}")
    @Timed
    public ResponseEntity<StudentClassLogDailyReport> getStudentClassLogDailyReport(@PathVariable Long id) {
        log.debug("REST request to get StudentClassLogDailyReport : {}", id);
        StudentClassLogDailyReport studentClassLogDailyReport = studentClassLogDailyReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentClassLogDailyReport));
    }

    @PostMapping("/student-class-log-daily-reports/by-date")
    @Timed
    public ResponseEntity<StatusBasedStudent> getStudentClassLogDailyReportToday(@RequestBody SingleDateRequest singleDateRequest) {

        StatusBasedStudent studentClassLogDailyReport = studentClassLogDailyReportService.getStudentClassLogDailyReportToday(singleDateRequest.getLogDate());
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(studentClassLogDailyReport));
    }

    /**
     * DELETE  /student-class-log-daily-reports/:id : delete the "id" studentClassLogDailyReport.
     *
     * @param id the id of the studentClassLogDailyReport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-class-log-daily-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteStudentClassLogDailyReport(@PathVariable Long id) {
        log.debug("REST request to delete StudentClassLogDailyReport : {}", id);
        studentClassLogDailyReportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.pure.service.service.impl;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.ClassArrangementRule;
import com.pure.service.repository.ClassArrangementRepository;
import com.pure.service.repository.ClassArrangementRuleRepository;
import com.pure.service.service.ClassArrangementService;
import com.pure.service.service.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Service Implementation for managing ClassArrangement.
 */
@Service
@Transactional
public class ClassArrangementServiceImpl implements ClassArrangementService {

    private final Logger log = LoggerFactory.getLogger(ClassArrangementServiceImpl.class);

    private final ClassArrangementRepository classArrangementRepository;

    @Autowired
    private ClassArrangementRuleRepository ruleRepository;

    public ClassArrangementServiceImpl(ClassArrangementRepository classArrangementRepository) {
        this.classArrangementRepository = classArrangementRepository;
    }

    /**
     * Save a classArrangement.
     *
     * @param classArrangement the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassArrangement save(ClassArrangement classArrangement) {
        log.debug("Request to save ClassArrangement : {}", classArrangement);
        return classArrangementRepository.save(classArrangement);
    }

    /**
     * Get all the classArrangements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassArrangement> findAll(Pageable pageable) {
        log.debug("Request to get all ClassArrangements");
        return classArrangementRepository.findAll(pageable);
    }

    /**
     * Get one classArrangement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClassArrangement findOne(Long id) {
        log.debug("Request to get ClassArrangement : {}", id);
        return classArrangementRepository.findOne(id);
    }

    /**
     * Delete the  classArrangement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassArrangement : {}", id);
        classArrangementRepository.delete(id);
    }

    @Override
    public void createClassArrangementsByRule(Long id) {

        ClassArrangementRule rule = ruleRepository.findOne(id);

        switch (rule.getLoopWay().getCode()) {

            case "perWeek":
                generateArrangementWeekly(rule);
                break;
            default:
                break;
        }
    }

    private void generateArrangementWeekly(ClassArrangementRule rule) {

        Instant startDate = rule.getEstimateStartDate();
        Instant endDate = rule.getEstimateEndDate();

        Objects.nonNull(startDate);
        Objects.nonNull(endDate);

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("起始时间错误，开始时间应该比结束时间早");
        }

        List<Instant> countDays = DateUtil.getCountWeekdayInRange(startDate, endDate, rule.getCountNumber().getValue(), rule.getEstimateStartTime());

        log.debug("Generating arrangements {} ", countDays);

        List<ClassArrangement> classArrangements = new ArrayList<>();
        countDays.forEach(day -> {
            ClassArrangement arrangement = new ClassArrangement();
            arrangement = arrangement.clazz(rule.getTargetClass())
                            .startDate(day)
                            .endDate(day.plus(rule.getDurationMinutes(), ChronoUnit.MINUTES))
                            .planedTeacher(rule.getTargetClass().getTeacher());

            classArrangements.add(arrangement);
        });

        log.debug("Generate classes: {}", classArrangements);

        classArrangementRepository.save(classArrangements);

    }


}

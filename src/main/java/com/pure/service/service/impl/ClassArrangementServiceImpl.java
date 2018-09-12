package com.pure.service.service.impl;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.ClassArrangementRule;
import com.pure.service.domain.ClassArrangementStatus;
import com.pure.service.domain.ClassRoom;
import com.pure.service.repository.ClassArrangementRepository;
import com.pure.service.repository.ClassArrangementRuleRepository;
import com.pure.service.repository.ClassArrangementStatusRepository;
import com.pure.service.repository.ClassRoomRepository;
import com.pure.service.service.ClassArrangementService;
import com.pure.service.service.dto.dto.ClassArrangementWeekElement;
import com.pure.service.service.dto.dto.ClassSchedule;
import com.pure.service.service.dto.request.CustomerStatusRequest;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


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

    @Autowired
    private ClassArrangementStatusRepository statusRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

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

    @Override
    public List<ClassSchedule> searchSchedulesInRange(CustomerStatusRequest customerStatusRequest) {

        return classArrangementRepository.getAllSchedulesByRange(customerStatusRequest.getStartDate(), customerStatusRequest.getEndDate());
    }

    private static Map<Integer, String> gapWeekdayNameMap = new HashMap<>();
    private static Map<TimePeriod, List<ClassSchedule>> timePeriodListMap = new HashMap<>();

    static {
        gapWeekdayNameMap.put(0, "星期一");
        gapWeekdayNameMap.put(1, "星期二");
        gapWeekdayNameMap.put(2, "星期三");
        gapWeekdayNameMap.put(3, "星期四");
        gapWeekdayNameMap.put(4, "星期五");
        gapWeekdayNameMap.put(5, "星期六");
        gapWeekdayNameMap.put(6, "星期日");
    }


    @Override
    public List<ClassArrangementWeekElement> getArrangementsInCurrentWeek() {

        List<ClassArrangementWeekElement> elements = new ArrayList<>();
        List<ClassRoom> classRooms = classRoomRepository.findAll();

        Instant mondayStart = DateUtil.getCurrentMondayStartSecond();
        int dayGap = 0;

        for (; dayGap < 7; dayGap++) {

            Instant starting = mondayStart.plus(dayGap, ChronoUnit.DAYS);
            Instant ending = mondayStart.plus(dayGap + 1, ChronoUnit.DAYS).minusSeconds(1);

            ClassArrangementWeekElement singledDay = new ClassArrangementWeekElement();
            String weekdayName = gapWeekdayNameMap.get(dayGap);
            singledDay.setWeekdayName(weekdayName);

            for (int i = 0; i < classRooms.size(); i ++) {
                //add rest marks, fixed array as class room
                ClassSchedule rest = new ClassSchedule();
                rest.setClassName("休");
                rest.setClickable(false);
                singledDay.getMoonRest().add(rest);
                singledDay.getAfternoonRest().add(rest);

                singledDay.getClassrooms().add(classRooms.get(i).getName());
            }

            timePeriodListMap.clear();

            timePeriodListMap.put(new TimePeriod(DateUtil.getInstantWithSpecialHourMinutes(starting, "9", "0"),
                DateUtil.getInstantWithSpecialHourMinutes(starting, "10", "30")), singledDay.getMorningFirstHalf());
            timePeriodListMap.put(new TimePeriod(DateUtil.getInstantWithSpecialHourMinutes(starting, "10", "45"),
                DateUtil.getInstantWithSpecialHourMinutes(starting, "12", "15")), singledDay.getMorningSecondHalf());
            timePeriodListMap.put(new TimePeriod(DateUtil.getInstantWithSpecialHourMinutes(starting, "13", "0"),
                DateUtil.getInstantWithSpecialHourMinutes(starting, "14", "30")), singledDay.getAfternoonFirstHalf());
            timePeriodListMap.put(new TimePeriod(DateUtil.getInstantWithSpecialHourMinutes(starting, "14", "45"),
                DateUtil.getInstantWithSpecialHourMinutes(starting, "16", "15")), singledDay.getAfternoonSecondHalf());
            timePeriodListMap.put(new TimePeriod(DateUtil.getInstantWithSpecialHourMinutes(starting, "16", "30"),
                DateUtil.getInstantWithSpecialHourMinutes(starting, "18", "00")), singledDay.getAfternoonThiredHalf());
            timePeriodListMap.put(new TimePeriod(DateUtil.getInstantWithSpecialHourMinutes(starting, "18", "30"),
                DateUtil.getInstantWithSpecialHourMinutes(starting, "20", "00")), singledDay.getEvenning());

            //find all the schedules in the day.
            List<ClassSchedule> schedules = classArrangementRepository.getAllSchedulesByRange(starting, ending);

            //find the schedules for the room
            for (ClassRoom classRoom : classRooms) {
                List<ClassSchedule> scheduleListInClassRoom = findClassInClassRoom(classRoom.getName(), schedules);

                boolean foundTeacher = false;
                for (Map.Entry<TimePeriod, List<ClassSchedule>> timePeriodListEntry : timePeriodListMap.entrySet()) {

                    TimePeriod timePeriod = timePeriodListEntry.getKey();
                    List<ClassSchedule> classNameElements = timePeriodListEntry.getValue();

                    ClassSchedule periodSchedule = findClassInRange(scheduleListInClassRoom, timePeriod.getStart(), timePeriod.getEnd());

                    if (periodSchedule == null) {
                        classNameElements.add(new ClassSchedule());
                    } else {

                        periodSchedule.setClickable(true);
                        classNameElements.add(periodSchedule);

                        if (!foundTeacher) {

                            singledDay.getTeachers().add(periodSchedule.getTeacherName());
                            singledDay.getCourses().add(periodSchedule.getCourseName());
                        }
                        foundTeacher = true;
                    }
                }

                if (!foundTeacher) {
                    singledDay.getTeachers().add("无");
                    singledDay.getCourses().add("无");
                }
            }

            elements.add(singledDay);
        }

        return elements;
    }

    private ClassSchedule findClassInRange(List<ClassSchedule> roomClasses, Instant classStart, Instant classEnd) {

        for (ClassSchedule roomClass : roomClasses) {

            Instant correctedTime = roomClass.getStart().plus(8, ChronoUnit.HOURS);
            if (correctedTime.isAfter(classStart.minusSeconds(1)) && correctedTime.isBefore(classEnd)) {
                return roomClass;
            }
        }

        return null;

    }

    private List<ClassSchedule> findClassInClassRoom(String classRoomName, List<ClassSchedule> schedules) {

        return schedules.stream().filter(schedule -> (schedule.getClassroomName().equals(classRoomName))).collect(Collectors.toList());
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

            ClassArrangementStatus initStatus = statusRepository.findByCode("notTaken");
            arrangement.setStatus(initStatus);

            classArrangements.add(arrangement);
        });

        log.debug("Generate classes: {}", classArrangements);

        classArrangementRepository.save(classArrangements);

    }

    private boolean checkDuplicateArrangement() {

        return false;
    }

    private class TimePeriod {

        public TimePeriod(Instant start, Instant end) {
            this.start = start;
            this.end = end;
        }

        private Instant start;
        private Instant end;

        public Instant getStart() {
            return start;
        }

        public void setStart(Instant start) {
            this.start = start;
        }

        public Instant getEnd() {
            return end;
        }

        public void setEnd(Instant end) {
            this.end = end;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TimePeriod)) return false;
            TimePeriod that = (TimePeriod) o;
            return Objects.equals(getStart(), that.getStart()) &&
                Objects.equals(getEnd(), that.getEnd());
        }

        @Override
        public int hashCode() {

            return Objects.hash(getStart(), getEnd());
        }
    }

}

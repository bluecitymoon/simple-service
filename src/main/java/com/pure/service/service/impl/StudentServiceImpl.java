package com.pure.service.service.impl;

import com.pure.service.domain.Contract;
import com.pure.service.domain.Student;
import com.pure.service.domain.StudentClassLog;
import com.pure.service.domain.StudentLeave;
import com.pure.service.repository.ContractRepository;
import com.pure.service.repository.StudentClassLogRepository;
import com.pure.service.repository.StudentLeaveRepository;
import com.pure.service.repository.StudentRepository;
import com.pure.service.service.ContractQueryService;
import com.pure.service.service.StudentClassLogQueryService;
import com.pure.service.service.StudentLeaveQueryService;
import com.pure.service.service.StudentService;
import com.pure.service.service.dto.ContractCriteria;
import com.pure.service.service.dto.StudentClassLogCriteria;
import com.pure.service.service.dto.StudentLeaveCriteria;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Service Implementation for managing Student.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService{

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    @Autowired
    private ContractQueryService contractQueryService;

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private StudentLeaveQueryService studentLeaveQueryService;

    @Autowired
    private StudentLeaveRepository studentLeaveRepository;

    @Autowired
    private StudentClassLogQueryService studentClassLogQueryService;

    @Autowired
    private StudentClassLogRepository studentClassLogRepository;


    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Save a student.
     *
     * @param student the entity to save
     * @return the persisted entity
     */
    @Override
    public Student save(Student student) {
        log.debug("Request to save Student : {}", student);

        if (student.getId() == null) {

            List<Student> students = studentRepository.findByNameAndPhone(student.getName(), student.getPhone());
            if (!CollectionUtils.isEmpty(students)) {
                throw new RuntimeException("该学员已存在，无法再次创建");
            }
        }
        return studentRepository.save(student);
    }

    /**
     *  Get all the students.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Student> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        return studentRepository.findAll(pageable);
    }

    /**
     *  Get one student by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Student findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findOne(id);
    }

    /**
     *  Delete the  student by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.delete(id);
    }

    @Override
    public Student mergeDuplicatedStudent() {

        Map<StudentKey, List<Student>> studentKeyListMap = new HashMap<>();
        List<Student> allStudents = studentRepository.findAll();
        for (Student student: allStudents) {

            StudentKey studentKey = new StudentKey();
            studentKey.setName(student.getName());
            studentKey.setPhone(student.getPhone());

            List<Student> existedStudents = studentKeyListMap.get(studentKey);

            if (existedStudents == null) {
                existedStudents = new ArrayList<>();
                studentKeyListMap.put(studentKey, existedStudents);
            }

            existedStudents.add(student);

        }

        for (List<Student> students : studentKeyListMap.values()) {

            if (students.size() < 2) { continue; }

            Student keepStudent = null;
            for (int i = 0; i < students.size(); i++) {

                Student student = students.get(i);
                if (i == 0) {
                    keepStudent = student;
                    continue;
                }

                LongFilter studentId = new LongFilter();
                studentId.setEquals(student.getId());

                mergeContractOwner(keepStudent, studentId);

                mergeLeaveOwner(keepStudent, studentId);

                mergeStudentClassLogOwner(keepStudent, studentId);

                studentRepository.delete(student);

            }
        }

        return null;
    }

    private void mergeStudentClassLogOwner(Student keepStudent, LongFilter studentId) {
        StudentClassLogCriteria studentClassLogCriteria = new StudentClassLogCriteria();
        studentClassLogCriteria.setStudentId(studentId);

        List<StudentClassLog> logs = studentClassLogQueryService.findByCriteria(studentClassLogCriteria);
        if (CollectionUtils.isEmpty(logs)) {
            return;
        }

        for (StudentClassLog studentClassLog : logs) {
            studentClassLog.setStudent(keepStudent);
        }

        studentClassLogRepository.save(logs);
    }

    private void mergeLeaveOwner(Student keepStudent, LongFilter studentId) {
        StudentLeaveCriteria studentLeaveCriteria = new StudentLeaveCriteria();
        studentLeaveCriteria.setStudentId(studentId);

        List<StudentLeave> studentLeaves = studentLeaveQueryService.findByCriteria(studentLeaveCriteria);

        if (CollectionUtils.isEmpty(studentLeaves)) {
            return;
        }

        for (StudentLeave studentLeaf : studentLeaves) {
            studentLeaf.setStudent(keepStudent);
        }

        studentLeaveRepository.save(studentLeaves);
    }

    private void mergeContractOwner(Student keepStudent, LongFilter studentId) {

        ContractCriteria contractCriteria = new ContractCriteria();
        contractCriteria.setStudentId(studentId);

        List<Contract> contracts = contractQueryService.findByCriteria(contractCriteria);

        if (CollectionUtils.isEmpty(contracts)) {
            return;
        }

        for (Contract contract : contracts) {
            contract.setStudent(keepStudent);
        }
        contractRepository.save(contracts);
    }

    private class StudentKey {
        private String name;
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof StudentKey)) return false;
            StudentKey that = (StudentKey) o;
            return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPhone(), that.getPhone());
        }

        @Override
        public int hashCode() {

            return Objects.hash(getName(), getPhone());
        }
    }
}

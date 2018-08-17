package com.pure.service.service.impl;

import com.pure.service.domain.Product;
import com.pure.service.domain.Student;
import com.pure.service.domain.StudentClass;
import com.pure.service.domain.StudentClassInOutLog;
import com.pure.service.repository.StudentClassInOutLogRepository;
import com.pure.service.repository.StudentClassRepository;
import com.pure.service.service.StudentClassService;
import com.pure.service.service.dto.dto.CommonResponse;
import com.pure.service.service.dto.request.StudentsClassRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing StudentClass.
 */
@Service
@Transactional
public class StudentClassServiceImpl implements StudentClassService{

    private final Logger log = LoggerFactory.getLogger(StudentClassServiceImpl.class);

    private final StudentClassRepository studentClassRepository;

    @Autowired
    private StudentClassInOutLogRepository inOutLogRepository;

    public StudentClassServiceImpl(StudentClassRepository studentClassRepository) {
        this.studentClassRepository = studentClassRepository;
    }

    /**
     * Save a studentClass.
     *
     * @param studentClass the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentClass save(StudentClass studentClass) {
        log.debug("Request to save StudentClass : {}", studentClass);
        return studentClassRepository.save(studentClass);
    }

    /**
     *  Get all the studentClasses.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentClass> findAll(Pageable pageable) {
        log.debug("Request to get all StudentClasses");
        return studentClassRepository.findAll(pageable);
    }

    /**
     *  Get one studentClass by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public StudentClass findOne(Long id) {
        log.debug("Request to get StudentClass : {}", id);
        return studentClassRepository.findOne(id);
    }

    /**
     *  Delete the  studentClass by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentClass : {}", id);
        studentClassRepository.delete(id);
    }

    @Override
    public CommonResponse batchAssign(StudentsClassRequest studentsClassRequest) {

        Product clazz = studentsClassRequest.getProduct();
        StringBuilder errorMessageBuilder = new StringBuilder();
        StringBuilder successMessageBuilder = new StringBuilder();

        for (Student student : studentsClassRequest.getStudents()) {

            StudentClass existedStudentClass = studentClassRepository.findByStudent_IdAndProduct_Id(student.getId(), clazz.getId());

            if (existedStudentClass != null) {
                errorMessageBuilder.append(student.getName() + " ");
                continue;
            }

            StudentClass studentClass = new StudentClass();
            studentClass = studentClass.student(student).product(clazz);

            StudentClass savedStudentClass = save(studentClass);

            successMessageBuilder.append(student.getName() + " ");

            StudentClassInOutLog inOutLog = new StudentClassInOutLog();
            inOutLog.setComments(student.getName() + "进班成功! 排班id = " + savedStudentClass.getId());
            inOutLog.setStudent(student);
            inOutLog.setNewClass(clazz);

            inOutLogRepository.save(inOutLog);
        }

        String errorMessage = errorMessageBuilder.toString().isEmpty()? "": errorMessageBuilder.toString() + "已在该班级里面；";
        String successMessage = successMessageBuilder.toString().isEmpty()? "": successMessageBuilder.toString() + "分配成功！";

        CommonResponse response = new CommonResponse();
        response.setMessage(errorMessage + successMessage);

        return response;
    }

    @Override
    public List<Student> findStudentsInClass(Long classId) {

        List<StudentClass> studentClasses = studentClassRepository.findByProduct_Id(classId);

        return studentClasses.stream().map(StudentClass::getStudent).collect(Collectors.toList());
    }
}

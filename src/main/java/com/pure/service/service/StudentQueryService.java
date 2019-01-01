package com.pure.service.service;


import com.pure.service.domain.Contract;
import com.pure.service.domain.Customer_;
import com.pure.service.domain.Student;
import com.pure.service.domain.Student_;
import com.pure.service.repository.ContractRepository;
import com.pure.service.repository.StudentRepository;
import com.pure.service.service.dto.StudentCriteria;
import com.pure.service.service.dto.dto.StudentClassCount;
import com.pure.service.service.dto.dto.StudentVo;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Service for executing complex queries for Student entities in the database.
 * The main input is a {@link StudentCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link Student} or a {@link Page} of {%link Student} which fulfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class StudentQueryService extends QueryService<Student> {

    private final Logger log = LoggerFactory.getLogger(StudentQueryService.class);


    private final StudentRepository studentRepository;

    @Autowired
    private ContractRepository contractRepository;

    public StudentQueryService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /**
     * Return a {@link List} of {%link Student} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Student> findByCriteria(StudentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Student> specification = createSpecification(criteria);
        return studentRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {%link Student} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Student> findByCriteria(StudentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Student> specification = createSpecification(criteria);
        return studentRepository.findAll(specification, page);
    }

    @Transactional(readOnly = true)
    public Page<StudentVo> findStudentVosByCriteria(StudentCriteria criteria, Pageable page) {

        log.debug("find by criteria : {}, page: {}", criteria, page);

        final Specifications<Student> specification = createSpecification(criteria);
        Page<Student> students = studentRepository.findAll(specification, page);
        List<StudentVo> studentVoList = new ArrayList<>();

        for (Student student : students.getContent()) {

            StudentVo studentVo = new StudentVo();

            BeanUtils.copyProperties(student, studentVo);
            getClassCounts(student, studentVo);

            studentVoList.add(studentVo);
        }

        Page<StudentVo> studentVoPage = new PageImpl<>(studentVoList, page, students.getTotalElements());

        return studentVoPage;
    }

    private void getClassCounts(Student student, StudentVo studentVo) {

        List<Contract> studentContracts = contractRepository.findByStudent_Id(student.getId());

        List<StudentClassCount> studentClassCounts = new ArrayList<>();
        studentVo.setStudentClassCounts(studentClassCounts);

        for (Contract contract : studentContracts) {

            StudentClassCount classCount = new StudentClassCount();
            classCount.setContractNumber(contract.getContractNumber());

            String type = contract.getContractNature() == null? "未知类型": contract.getContractNature().getName();
            classCount.setContractType(type);

            Integer totalCount = contract.getTotalHours() == null? 0: contract.getTotalHours();
            Integer takenCount = contract.getHoursTaken() == null? 0: contract.getHoursTaken();
            classCount.setTotal(totalCount);
            classCount.setTaken(takenCount);

            classCount.setBalance(totalCount - takenCount);

            studentClassCounts.add(classCount);
        }
    }

    /**
     * Function to convert StudentCriteria to a {@link Specifications}
     */
    private Specifications<Student> createSpecification(StudentCriteria criteria) {
        Specifications<Student> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getRegionId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegionId(), Student_.regionId));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Student_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Student_.name));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Student_.phone));
            }
            if (criteria.getGender() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGender(), Student_.gender));
            }
            if (criteria.getBirthday() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBirthday(), Student_.birthday));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Student_.address));
            }
            if (criteria.getSchool() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSchool(), Student_.school));
            }
            if (criteria.getQq() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQq(), Student_.qq));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), Student_.comments));
            }
            if (criteria.getCustomerId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getCustomerId(), Student_.customer, Customer_.id));
            }
            if (!StringUtils.isEmpty(criteria.getCustomerName())) {
                specification = specification.and(customerName(criteria.getCustomerName()));
            }

            if (!StringUtils.isEmpty(criteria.getCustomerPhoneNumber())) {
                specification = specification.and(customerPhoneNumber(criteria.getCustomerPhoneNumber()));
            }
        }
        return specification;
    }

    private Specification customerName(String customerName) {

        return (root, query, cb) -> cb.like(root.get(Student_.customer).get(Customer_.name), customerName);
    }
    private Specification customerPhoneNumber(String phoneNumber) {

        return (root, query, cb) -> cb.like(root.get(Student_.customer).get(Customer_.contactPhoneNumber), phoneNumber);
    }

}

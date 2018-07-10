package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;

import com.pure.service.domain.Student;
import com.pure.service.repository.StudentRepository;
import com.pure.service.service.StudentService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
import com.pure.service.service.dto.StudentCriteria;
import com.pure.service.service.StudentQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the StudentResource REST controller.
 *
 * @see StudentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class StudentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDAY = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDAY = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_SCHOOL = "AAAAAAAAAA";
    private static final String UPDATED_SCHOOL = "BBBBBBBBBB";

    private static final String DEFAULT_QQ = "AAAAAAAAAA";
    private static final String UPDATED_QQ = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentQueryService studentQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restStudentMockMvc;

    private Student student;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StudentResource studentResource = new StudentResource(studentService, studentQueryService);
        this.restStudentMockMvc = MockMvcBuilders.standaloneSetup(studentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Student createEntity(EntityManager em) {
        Student student = new Student()
            .name(DEFAULT_NAME)
            .phone(DEFAULT_PHONE)
            .gender(DEFAULT_GENDER)
            .birthday(DEFAULT_BIRTHDAY)
            .address(DEFAULT_ADDRESS)
            .school(DEFAULT_SCHOOL)
            .qq(DEFAULT_QQ)
            .comments(DEFAULT_COMMENTS);
        return student;
    }

    @Before
    public void initTest() {
        student = createEntity(em);
    }

    @Test
    @Transactional
    public void createStudent() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate + 1);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStudent.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testStudent.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testStudent.getBirthday()).isEqualTo(DEFAULT_BIRTHDAY);
        assertThat(testStudent.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testStudent.getSchool()).isEqualTo(DEFAULT_SCHOOL);
        assertThat(testStudent.getQq()).isEqualTo(DEFAULT_QQ);
        assertThat(testStudent.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createStudentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = studentRepository.findAll().size();

        // Create the Student with an existing ID
        student.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStudentMockMvc.perform(post("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isBadRequest());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllStudents() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList
        restStudentMockMvc.perform(get("/api/students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL)))
            .andExpect(jsonPath("$.[*].qq").value(hasItem(DEFAULT_QQ)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    @Test
    @Transactional
    public void getStudent() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", student.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(student.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.birthday").value(DEFAULT_BIRTHDAY.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.school").value(DEFAULT_SCHOOL))
            .andExpect(jsonPath("$.qq").value(DEFAULT_QQ))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }

    @Test
    @Transactional
    public void getAllStudentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where name equals to DEFAULT_NAME
        defaultStudentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the studentList where name equals to UPDATED_NAME
        defaultStudentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStudentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the studentList where name equals to UPDATED_NAME
        defaultStudentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStudentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where name is not null
        defaultStudentShouldBeFound("name.specified=true");

        // Get all the studentList where name is null
        defaultStudentShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where phone equals to DEFAULT_PHONE
        defaultStudentShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the studentList where phone equals to UPDATED_PHONE
        defaultStudentShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllStudentsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultStudentShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the studentList where phone equals to UPDATED_PHONE
        defaultStudentShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllStudentsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where phone is not null
        defaultStudentShouldBeFound("phone.specified=true");

        // Get all the studentList where phone is null
        defaultStudentShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where gender equals to DEFAULT_GENDER
        defaultStudentShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the studentList where gender equals to UPDATED_GENDER
        defaultStudentShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllStudentsByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultStudentShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the studentList where gender equals to UPDATED_GENDER
        defaultStudentShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllStudentsByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where gender is not null
        defaultStudentShouldBeFound("gender.specified=true");

        // Get all the studentList where gender is null
        defaultStudentShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByBirthdayIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where birthday equals to DEFAULT_BIRTHDAY
        defaultStudentShouldBeFound("birthday.equals=" + DEFAULT_BIRTHDAY);

        // Get all the studentList where birthday equals to UPDATED_BIRTHDAY
        defaultStudentShouldNotBeFound("birthday.equals=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllStudentsByBirthdayIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where birthday in DEFAULT_BIRTHDAY or UPDATED_BIRTHDAY
        defaultStudentShouldBeFound("birthday.in=" + DEFAULT_BIRTHDAY + "," + UPDATED_BIRTHDAY);

        // Get all the studentList where birthday equals to UPDATED_BIRTHDAY
        defaultStudentShouldNotBeFound("birthday.in=" + UPDATED_BIRTHDAY);
    }

    @Test
    @Transactional
    public void getAllStudentsByBirthdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where birthday is not null
        defaultStudentShouldBeFound("birthday.specified=true");

        // Get all the studentList where birthday is null
        defaultStudentShouldNotBeFound("birthday.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where address equals to DEFAULT_ADDRESS
        defaultStudentShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the studentList where address equals to UPDATED_ADDRESS
        defaultStudentShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllStudentsByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultStudentShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the studentList where address equals to UPDATED_ADDRESS
        defaultStudentShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    public void getAllStudentsByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where address is not null
        defaultStudentShouldBeFound("address.specified=true");

        // Get all the studentList where address is null
        defaultStudentShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsBySchoolIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where school equals to DEFAULT_SCHOOL
        defaultStudentShouldBeFound("school.equals=" + DEFAULT_SCHOOL);

        // Get all the studentList where school equals to UPDATED_SCHOOL
        defaultStudentShouldNotBeFound("school.equals=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllStudentsBySchoolIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where school in DEFAULT_SCHOOL or UPDATED_SCHOOL
        defaultStudentShouldBeFound("school.in=" + DEFAULT_SCHOOL + "," + UPDATED_SCHOOL);

        // Get all the studentList where school equals to UPDATED_SCHOOL
        defaultStudentShouldNotBeFound("school.in=" + UPDATED_SCHOOL);
    }

    @Test
    @Transactional
    public void getAllStudentsBySchoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where school is not null
        defaultStudentShouldBeFound("school.specified=true");

        // Get all the studentList where school is null
        defaultStudentShouldNotBeFound("school.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByQqIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where qq equals to DEFAULT_QQ
        defaultStudentShouldBeFound("qq.equals=" + DEFAULT_QQ);

        // Get all the studentList where qq equals to UPDATED_QQ
        defaultStudentShouldNotBeFound("qq.equals=" + UPDATED_QQ);
    }

    @Test
    @Transactional
    public void getAllStudentsByQqIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where qq in DEFAULT_QQ or UPDATED_QQ
        defaultStudentShouldBeFound("qq.in=" + DEFAULT_QQ + "," + UPDATED_QQ);

        // Get all the studentList where qq equals to UPDATED_QQ
        defaultStudentShouldNotBeFound("qq.in=" + UPDATED_QQ);
    }

    @Test
    @Transactional
    public void getAllStudentsByQqIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where qq is not null
        defaultStudentShouldBeFound("qq.specified=true");

        // Get all the studentList where qq is null
        defaultStudentShouldNotBeFound("qq.specified=false");
    }

    @Test
    @Transactional
    public void getAllStudentsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where comments equals to DEFAULT_COMMENTS
        defaultStudentShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the studentList where comments equals to UPDATED_COMMENTS
        defaultStudentShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStudentsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultStudentShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the studentList where comments equals to UPDATED_COMMENTS
        defaultStudentShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStudentsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        studentRepository.saveAndFlush(student);

        // Get all the studentList where comments is not null
        defaultStudentShouldBeFound("comments.specified=true");

        // Get all the studentList where comments is null
        defaultStudentShouldNotBeFound("comments.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultStudentShouldBeFound(String filter) throws Exception {
        restStudentMockMvc.perform(get("/api/students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(student.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].birthday").value(hasItem(DEFAULT_BIRTHDAY.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].school").value(hasItem(DEFAULT_SCHOOL)))
            .andExpect(jsonPath("$.[*].qq").value(hasItem(DEFAULT_QQ)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultStudentShouldNotBeFound(String filter) throws Exception {
        restStudentMockMvc.perform(get("/api/students?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingStudent() throws Exception {
        // Get the student
        restStudentMockMvc.perform(get("/api/students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudent() throws Exception {
        // Initialize the database
        studentService.save(student);

        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Update the student
        Student updatedStudent = studentRepository.findOne(student.getId());
        updatedStudent
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .gender(UPDATED_GENDER)
            .birthday(UPDATED_BIRTHDAY)
            .address(UPDATED_ADDRESS)
            .school(UPDATED_SCHOOL)
            .qq(UPDATED_QQ)
            .comments(UPDATED_COMMENTS);

        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStudent)))
            .andExpect(status().isOk());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate);
        Student testStudent = studentList.get(studentList.size() - 1);
        assertThat(testStudent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStudent.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testStudent.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testStudent.getBirthday()).isEqualTo(UPDATED_BIRTHDAY);
        assertThat(testStudent.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testStudent.getSchool()).isEqualTo(UPDATED_SCHOOL);
        assertThat(testStudent.getQq()).isEqualTo(UPDATED_QQ);
        assertThat(testStudent.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingStudent() throws Exception {
        int databaseSizeBeforeUpdate = studentRepository.findAll().size();

        // Create the Student

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restStudentMockMvc.perform(put("/api/students")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(student)))
            .andExpect(status().isCreated());

        // Validate the Student in the database
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteStudent() throws Exception {
        // Initialize the database
        studentService.save(student);

        int databaseSizeBeforeDelete = studentRepository.findAll().size();

        // Get the student
        restStudentMockMvc.perform(delete("/api/students/{id}", student.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Student> studentList = studentRepository.findAll();
        assertThat(studentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Student.class);
        Student student1 = new Student();
        student1.setId(1L);
        Student student2 = new Student();
        student2.setId(student1.getId());
        assertThat(student1).isEqualTo(student2);
        student2.setId(2L);
        assertThat(student1).isNotEqualTo(student2);
        student1.setId(null);
        assertThat(student1).isNotEqualTo(student2);
    }
}

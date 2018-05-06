package com.pure.service.web.rest;

import com.pure.service.SimpleServiceApp;
import com.pure.service.domain.ClassRoom;
import com.pure.service.repository.ClassRoomRepository;
import com.pure.service.service.ClassRoomQueryService;
import com.pure.service.service.ClassRoomService;
import com.pure.service.web.rest.errors.ExceptionTranslator;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the ClassRoomResource REST controller.
 *
 * @see ClassRoomResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SimpleServiceApp.class)
public class ClassRoomResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ROOM_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ROOM_NUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_ACREAGE = 1F;
    private static final Float UPDATED_ACREAGE = 2F;

    private static final Integer DEFAULT_MAX_STUDENT_CAPACITY = 1;
    private static final Integer UPDATED_MAX_STUDENT_CAPACITY = 2;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_AVALIABLE = false;
    private static final Boolean UPDATED_AVALIABLE = true;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private ClassRoomService classRoomService;

    @Autowired
    private ClassRoomQueryService classRoomQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClassRoomMockMvc;

    private ClassRoom classRoom;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClassRoomResource classRoomResource = new ClassRoomResource(classRoomService, classRoomQueryService);
        this.restClassRoomMockMvc = MockMvcBuilders.standaloneSetup(classRoomResource)
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
    public static ClassRoom createEntity(EntityManager em) {
        ClassRoom classRoom = new ClassRoom()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .roomNumber(DEFAULT_ROOM_NUMBER)
            .acreage(DEFAULT_ACREAGE)
            .maxStudentCapacity(DEFAULT_MAX_STUDENT_CAPACITY)
            .comments(DEFAULT_COMMENTS)
            .avaliable(DEFAULT_AVALIABLE);
        return classRoom;
    }

    @Before
    public void initTest() {
        classRoom = createEntity(em);
    }

    @Test
    @Transactional
    public void createClassRoom() throws Exception {
        int databaseSizeBeforeCreate = classRoomRepository.findAll().size();

        // Create the ClassRoom
        restClassRoomMockMvc.perform(post("/api/class-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classRoom)))
            .andExpect(status().isCreated());

        // Validate the ClassRoom in the database
        List<ClassRoom> classRoomList = classRoomRepository.findAll();
        assertThat(classRoomList).hasSize(databaseSizeBeforeCreate + 1);
        ClassRoom testClassRoom = classRoomList.get(classRoomList.size() - 1);
        assertThat(testClassRoom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClassRoom.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testClassRoom.getRoomNumber()).isEqualTo(DEFAULT_ROOM_NUMBER);
        assertThat(testClassRoom.getAcreage()).isEqualTo(DEFAULT_ACREAGE);
        assertThat(testClassRoom.getMaxStudentCapacity()).isEqualTo(DEFAULT_MAX_STUDENT_CAPACITY);
        assertThat(testClassRoom.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testClassRoom.isAvaliable()).isEqualTo(DEFAULT_AVALIABLE);
    }

    @Test
    @Transactional
    public void createClassRoomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = classRoomRepository.findAll().size();

        // Create the ClassRoom with an existing ID
        classRoom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassRoomMockMvc.perform(post("/api/class-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classRoom)))
            .andExpect(status().isBadRequest());

        // Validate the ClassRoom in the database
        List<ClassRoom> classRoomList = classRoomRepository.findAll();
        assertThat(classRoomList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClassRooms() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList
        restClassRoomMockMvc.perform(get("/api/class-rooms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].roomNumber").value(hasItem(DEFAULT_ROOM_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].acreage").value(hasItem(DEFAULT_ACREAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxStudentCapacity").value(hasItem(DEFAULT_MAX_STUDENT_CAPACITY)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].avaliable").value(hasItem(DEFAULT_AVALIABLE.booleanValue())));
    }

    @Test
    @Transactional
    public void getClassRoom() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get the classRoom
        restClassRoomMockMvc.perform(get("/api/class-rooms/{id}", classRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(classRoom.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.roomNumber").value(DEFAULT_ROOM_NUMBER.toString()))
            .andExpect(jsonPath("$.acreage").value(DEFAULT_ACREAGE.doubleValue()))
            .andExpect(jsonPath("$.maxStudentCapacity").value(DEFAULT_MAX_STUDENT_CAPACITY))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.avaliable").value(DEFAULT_AVALIABLE.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllClassRoomsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where name equals to DEFAULT_NAME
        defaultClassRoomShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the classRoomList where name equals to UPDATED_NAME
        defaultClassRoomShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where name in DEFAULT_NAME or UPDATED_NAME
        defaultClassRoomShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the classRoomList where name equals to UPDATED_NAME
        defaultClassRoomShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where name is not null
        defaultClassRoomShouldBeFound("name.specified=true");

        // Get all the classRoomList where name is null
        defaultClassRoomShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassRoomsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where code equals to DEFAULT_CODE
        defaultClassRoomShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the classRoomList where code equals to UPDATED_CODE
        defaultClassRoomShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where code in DEFAULT_CODE or UPDATED_CODE
        defaultClassRoomShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the classRoomList where code equals to UPDATED_CODE
        defaultClassRoomShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where code is not null
        defaultClassRoomShouldBeFound("code.specified=true");

        // Get all the classRoomList where code is null
        defaultClassRoomShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassRoomsByRoomNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where roomNumber equals to DEFAULT_ROOM_NUMBER
        defaultClassRoomShouldBeFound("roomNumber.equals=" + DEFAULT_ROOM_NUMBER);

        // Get all the classRoomList where roomNumber equals to UPDATED_ROOM_NUMBER
        defaultClassRoomShouldNotBeFound("roomNumber.equals=" + UPDATED_ROOM_NUMBER);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByRoomNumberIsInShouldWork() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where roomNumber in DEFAULT_ROOM_NUMBER or UPDATED_ROOM_NUMBER
        defaultClassRoomShouldBeFound("roomNumber.in=" + DEFAULT_ROOM_NUMBER + "," + UPDATED_ROOM_NUMBER);

        // Get all the classRoomList where roomNumber equals to UPDATED_ROOM_NUMBER
        defaultClassRoomShouldNotBeFound("roomNumber.in=" + UPDATED_ROOM_NUMBER);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByRoomNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where roomNumber is not null
        defaultClassRoomShouldBeFound("roomNumber.specified=true");

        // Get all the classRoomList where roomNumber is null
        defaultClassRoomShouldNotBeFound("roomNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassRoomsByAcreageIsEqualToSomething() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where acreage equals to DEFAULT_ACREAGE
        defaultClassRoomShouldBeFound("acreage.equals=" + DEFAULT_ACREAGE);

        // Get all the classRoomList where acreage equals to UPDATED_ACREAGE
        defaultClassRoomShouldNotBeFound("acreage.equals=" + UPDATED_ACREAGE);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByAcreageIsInShouldWork() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where acreage in DEFAULT_ACREAGE or UPDATED_ACREAGE
        defaultClassRoomShouldBeFound("acreage.in=" + DEFAULT_ACREAGE + "," + UPDATED_ACREAGE);

        // Get all the classRoomList where acreage equals to UPDATED_ACREAGE
        defaultClassRoomShouldNotBeFound("acreage.in=" + UPDATED_ACREAGE);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByAcreageIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where acreage is not null
        defaultClassRoomShouldBeFound("acreage.specified=true");

        // Get all the classRoomList where acreage is null
        defaultClassRoomShouldNotBeFound("acreage.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassRoomsByMaxStudentCapacityIsEqualToSomething() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where maxStudentCapacity equals to DEFAULT_MAX_STUDENT_CAPACITY
        defaultClassRoomShouldBeFound("maxStudentCapacity.equals=" + DEFAULT_MAX_STUDENT_CAPACITY);

        // Get all the classRoomList where maxStudentCapacity equals to UPDATED_MAX_STUDENT_CAPACITY
        defaultClassRoomShouldNotBeFound("maxStudentCapacity.equals=" + UPDATED_MAX_STUDENT_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByMaxStudentCapacityIsInShouldWork() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where maxStudentCapacity in DEFAULT_MAX_STUDENT_CAPACITY or UPDATED_MAX_STUDENT_CAPACITY
        defaultClassRoomShouldBeFound("maxStudentCapacity.in=" + DEFAULT_MAX_STUDENT_CAPACITY + "," + UPDATED_MAX_STUDENT_CAPACITY);

        // Get all the classRoomList where maxStudentCapacity equals to UPDATED_MAX_STUDENT_CAPACITY
        defaultClassRoomShouldNotBeFound("maxStudentCapacity.in=" + UPDATED_MAX_STUDENT_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByMaxStudentCapacityIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where maxStudentCapacity is not null
        defaultClassRoomShouldBeFound("maxStudentCapacity.specified=true");

        // Get all the classRoomList where maxStudentCapacity is null
        defaultClassRoomShouldNotBeFound("maxStudentCapacity.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassRoomsByMaxStudentCapacityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where maxStudentCapacity greater than or equals to DEFAULT_MAX_STUDENT_CAPACITY
        defaultClassRoomShouldBeFound("maxStudentCapacity.greaterOrEqualThan=" + DEFAULT_MAX_STUDENT_CAPACITY);

        // Get all the classRoomList where maxStudentCapacity greater than or equals to UPDATED_MAX_STUDENT_CAPACITY
        defaultClassRoomShouldNotBeFound("maxStudentCapacity.greaterOrEqualThan=" + UPDATED_MAX_STUDENT_CAPACITY);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByMaxStudentCapacityIsLessThanSomething() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where maxStudentCapacity less than or equals to DEFAULT_MAX_STUDENT_CAPACITY
        defaultClassRoomShouldNotBeFound("maxStudentCapacity.lessThan=" + DEFAULT_MAX_STUDENT_CAPACITY);

        // Get all the classRoomList where maxStudentCapacity less than or equals to UPDATED_MAX_STUDENT_CAPACITY
        defaultClassRoomShouldBeFound("maxStudentCapacity.lessThan=" + UPDATED_MAX_STUDENT_CAPACITY);
    }


    @Test
    @Transactional
    public void getAllClassRoomsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where comments equals to DEFAULT_COMMENTS
        defaultClassRoomShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the classRoomList where comments equals to UPDATED_COMMENTS
        defaultClassRoomShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultClassRoomShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the classRoomList where comments equals to UPDATED_COMMENTS
        defaultClassRoomShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where comments is not null
        defaultClassRoomShouldBeFound("comments.specified=true");

        // Get all the classRoomList where comments is null
        defaultClassRoomShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    public void getAllClassRoomsByAvaliableIsEqualToSomething() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where avaliable equals to DEFAULT_AVALIABLE
        defaultClassRoomShouldBeFound("avaliable.equals=" + DEFAULT_AVALIABLE);

        // Get all the classRoomList where avaliable equals to UPDATED_AVALIABLE
        defaultClassRoomShouldNotBeFound("avaliable.equals=" + UPDATED_AVALIABLE);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByAvaliableIsInShouldWork() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where avaliable in DEFAULT_AVALIABLE or UPDATED_AVALIABLE
        defaultClassRoomShouldBeFound("avaliable.in=" + DEFAULT_AVALIABLE + "," + UPDATED_AVALIABLE);

        // Get all the classRoomList where avaliable equals to UPDATED_AVALIABLE
        defaultClassRoomShouldNotBeFound("avaliable.in=" + UPDATED_AVALIABLE);
    }

    @Test
    @Transactional
    public void getAllClassRoomsByAvaliableIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRoomRepository.saveAndFlush(classRoom);

        // Get all the classRoomList where avaliable is not null
        defaultClassRoomShouldBeFound("avaliable.specified=true");

        // Get all the classRoomList where avaliable is null
        defaultClassRoomShouldNotBeFound("avaliable.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClassRoomShouldBeFound(String filter) throws Exception {
        restClassRoomMockMvc.perform(get("/api/class-rooms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].roomNumber").value(hasItem(DEFAULT_ROOM_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].acreage").value(hasItem(DEFAULT_ACREAGE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxStudentCapacity").value(hasItem(DEFAULT_MAX_STUDENT_CAPACITY)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].avaliable").value(hasItem(DEFAULT_AVALIABLE.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClassRoomShouldNotBeFound(String filter) throws Exception {
        restClassRoomMockMvc.perform(get("/api/class-rooms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingClassRoom() throws Exception {
        // Get the classRoom
        restClassRoomMockMvc.perform(get("/api/class-rooms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClassRoom() throws Exception {
        // Initialize the database
        classRoomService.save(classRoom);

        int databaseSizeBeforeUpdate = classRoomRepository.findAll().size();

        // Update the classRoom
        ClassRoom updatedClassRoom = classRoomRepository.findOne(classRoom.getId());
        updatedClassRoom
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .roomNumber(UPDATED_ROOM_NUMBER)
            .acreage(UPDATED_ACREAGE)
            .maxStudentCapacity(UPDATED_MAX_STUDENT_CAPACITY)
            .comments(UPDATED_COMMENTS)
            .avaliable(UPDATED_AVALIABLE);

        restClassRoomMockMvc.perform(put("/api/class-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClassRoom)))
            .andExpect(status().isOk());

        // Validate the ClassRoom in the database
        List<ClassRoom> classRoomList = classRoomRepository.findAll();
        assertThat(classRoomList).hasSize(databaseSizeBeforeUpdate);
        ClassRoom testClassRoom = classRoomList.get(classRoomList.size() - 1);
        assertThat(testClassRoom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClassRoom.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testClassRoom.getRoomNumber()).isEqualTo(UPDATED_ROOM_NUMBER);
        assertThat(testClassRoom.getAcreage()).isEqualTo(UPDATED_ACREAGE);
        assertThat(testClassRoom.getMaxStudentCapacity()).isEqualTo(UPDATED_MAX_STUDENT_CAPACITY);
        assertThat(testClassRoom.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testClassRoom.isAvaliable()).isEqualTo(UPDATED_AVALIABLE);
    }

    @Test
    @Transactional
    public void updateNonExistingClassRoom() throws Exception {
        int databaseSizeBeforeUpdate = classRoomRepository.findAll().size();

        // Create the ClassRoom

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClassRoomMockMvc.perform(put("/api/class-rooms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(classRoom)))
            .andExpect(status().isCreated());

        // Validate the ClassRoom in the database
        List<ClassRoom> classRoomList = classRoomRepository.findAll();
        assertThat(classRoomList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteClassRoom() throws Exception {
        // Initialize the database
        classRoomService.save(classRoom);

        int databaseSizeBeforeDelete = classRoomRepository.findAll().size();

        // Get the classRoom
        restClassRoomMockMvc.perform(delete("/api/class-rooms/{id}", classRoom.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ClassRoom> classRoomList = classRoomRepository.findAll();
        assertThat(classRoomList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClassRoom.class);
        ClassRoom classRoom1 = new ClassRoom();
        classRoom1.setId(1L);
        ClassRoom classRoom2 = new ClassRoom();
        classRoom2.setId(classRoom1.getId());
        assertThat(classRoom1).isEqualTo(classRoom2);
        classRoom2.setId(2L);
        assertThat(classRoom1).isNotEqualTo(classRoom2);
        classRoom1.setId(null);
        assertThat(classRoom1).isNotEqualTo(classRoom2);
    }
}

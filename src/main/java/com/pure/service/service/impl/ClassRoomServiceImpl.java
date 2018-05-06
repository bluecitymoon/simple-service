package com.pure.service.service.impl;

import com.pure.service.service.ClassRoomService;
import com.pure.service.domain.ClassRoom;
import com.pure.service.repository.ClassRoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ClassRoom.
 */
@Service
@Transactional
public class ClassRoomServiceImpl implements ClassRoomService{

    private final Logger log = LoggerFactory.getLogger(ClassRoomServiceImpl.class);

    private final ClassRoomRepository classRoomRepository;

    public ClassRoomServiceImpl(ClassRoomRepository classRoomRepository) {
        this.classRoomRepository = classRoomRepository;
    }

    /**
     * Save a classRoom.
     *
     * @param classRoom the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassRoom save(ClassRoom classRoom) {
        log.debug("Request to save ClassRoom : {}", classRoom);
        return classRoomRepository.save(classRoom);
    }

    /**
     *  Get all the classRooms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassRoom> findAll(Pageable pageable) {
        log.debug("Request to get all ClassRooms");
        return classRoomRepository.findAll(pageable);
    }

    /**
     *  Get one classRoom by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClassRoom findOne(Long id) {
        log.debug("Request to get ClassRoom : {}", id);
        return classRoomRepository.findOne(id);
    }

    /**
     *  Delete the  classRoom by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassRoom : {}", id);
        classRoomRepository.delete(id);
    }
}

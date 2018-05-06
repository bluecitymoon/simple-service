package com.pure.service.service;

import com.pure.service.domain.ClassRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ClassRoom.
 */
public interface ClassRoomService {

    /**
     * Save a classRoom.
     *
     * @param classRoom the entity to save
     * @return the persisted entity
     */
    ClassRoom save(ClassRoom classRoom);

    /**
     *  Get all the classRooms.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ClassRoom> findAll(Pageable pageable);

    /**
     *  Get the "id" classRoom.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ClassRoom findOne(Long id);

    /**
     *  Delete the "id" classRoom.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

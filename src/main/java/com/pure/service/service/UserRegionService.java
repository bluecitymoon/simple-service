package com.pure.service.service;

import com.pure.service.domain.User;
import com.pure.service.domain.UserRegion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Service Interface for managing UserRegion.
 */
public interface UserRegionService {

    /**
     * Save a userRegion.
     *
     * @param userRegion the entity to save
     * @return the persisted entity
     */
    UserRegion save(UserRegion userRegion);

    /**
     *  Get all the userRegions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserRegion> findAll(Pageable pageable);

    /**
     *  Get the "id" userRegion.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserRegion findOne(Long id);

    /**
     *  Delete the "id" userRegion.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    List<User> getAllUsersInRegion(Long regionId);
}

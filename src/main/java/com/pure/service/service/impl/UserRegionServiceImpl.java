package com.pure.service.service.impl;

import com.pure.service.domain.User;
import com.pure.service.domain.UserRegion;
import com.pure.service.repository.UserRegionRepository;
import com.pure.service.service.UserRegionQueryService;
import com.pure.service.service.UserRegionService;
import com.pure.service.service.dto.UserRegionCriteria;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing UserRegion.
 */
@Service
@Transactional
public class UserRegionServiceImpl implements UserRegionService{

    private final Logger log = LoggerFactory.getLogger(UserRegionServiceImpl.class);

    private final UserRegionRepository userRegionRepository;

    @Autowired
    private UserRegionQueryService userRegionQueryService;

    public UserRegionServiceImpl(UserRegionRepository userRegionRepository) {
        this.userRegionRepository = userRegionRepository;
    }

    /**
     * Save a userRegion.
     *
     * @param userRegion the entity to save
     * @return the persisted entity
     */
    @Override
    public UserRegion save(UserRegion userRegion) {
        log.debug("Request to save UserRegion : {}", userRegion);

        UserRegionCriteria userRegionCriteria = new UserRegionCriteria();
        LongFilter regionIdFilter = new LongFilter();
        regionIdFilter.setEquals(userRegion.getRegion().getId());

        LongFilter userIdFilter = new LongFilter();
        userIdFilter.setEquals(userRegion.getUser().getId());

        userRegionCriteria.setRegionId(regionIdFilter);
        userRegionCriteria.setUserId(userIdFilter);

        List<UserRegion> userRegions = userRegionQueryService.findByCriteria(userRegionCriteria);
        if (!CollectionUtils.isEmpty(userRegions)) {
            throw new RuntimeException("该用户已经在该区域了");
        }


        return userRegionRepository.save(userRegion);
    }

    /**
     *  Get all the userRegions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserRegion> findAll(Pageable pageable) {
        log.debug("Request to get all UserRegions");
        return userRegionRepository.findAll(pageable);
    }

    /**
     *  Get one userRegion by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserRegion findOne(Long id) {
        log.debug("Request to get UserRegion : {}", id);
        return userRegionRepository.findOne(id);
    }

    /**
     *  Delete the  userRegion by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserRegion : {}", id);
        userRegionRepository.delete(id);
    }

    @Override
    public List<User> getAllUsersInRegion(Long regionId) {

        UserRegionCriteria userRegionCriteria = new UserRegionCriteria();
        LongFilter regionIdFilter = new LongFilter();
        regionIdFilter.setEquals(regionId);

        userRegionCriteria.setRegionId(regionIdFilter);

        List<UserRegion> regions = userRegionQueryService.findByCriteria(userRegionCriteria);

        return regions.stream().map(UserRegion::getUser).collect(Collectors.toList());
    }

    @Override
    public Long removeUserRegion(Long userId, Long regionId) {

        UserRegionCriteria userRegionCriteria = new UserRegionCriteria();
        LongFilter regionIdFilter = new LongFilter();
        regionIdFilter.setEquals(regionId);

        LongFilter userIdFilter = new LongFilter();
        userIdFilter.setEquals(userId);

        userRegionCriteria.setRegionId(regionIdFilter);
        userRegionCriteria.setUserId(userIdFilter);

        List<UserRegion> userRegions = userRegionQueryService.findByCriteria(userRegionCriteria);

        if (CollectionUtils.isEmpty(userRegions)) {
            return new Long("-1");
        }

        userRegionRepository.delete(userRegions);

        return userRegions.get(0).getId();
    }
}

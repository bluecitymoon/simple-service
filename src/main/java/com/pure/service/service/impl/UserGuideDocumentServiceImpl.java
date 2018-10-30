package com.pure.service.service.impl;

import com.pure.service.domain.Authority;
import com.pure.service.domain.AuthorityUserGuideDocument;
import com.pure.service.domain.UserGuideDocument;
import com.pure.service.repository.AuthorityRepository;
import com.pure.service.repository.AuthorityUserGuideDocumentRepository;
import com.pure.service.repository.UserGuideDocumentRepository;
import com.pure.service.security.SecurityUtils;
import com.pure.service.service.AuthorityUserGuideDocumentQueryService;
import com.pure.service.service.UserGuideDocumentQueryService;
import com.pure.service.service.UserGuideDocumentService;
import com.pure.service.service.dto.AuthorityUserGuideDocumentCriteria;
import com.pure.service.service.dto.UserGuideDocumentCriteria;
import com.pure.service.service.dto.dto.UserGuideDocumentDto;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing UserGuideDocument.
 */
@Service
@Transactional
public class UserGuideDocumentServiceImpl implements UserGuideDocumentService{

    private final Logger log = LoggerFactory.getLogger(UserGuideDocumentServiceImpl.class);

    private final UserGuideDocumentRepository userGuideDocumentRepository;

    @Autowired
    private AuthorityUserGuideDocumentRepository documentRepository;

    @Autowired
    private AuthorityUserGuideDocumentQueryService authorityUserGuideDocumentQueryService;

    @Autowired
    private UserGuideDocumentQueryService userGuideDocumentQueryService;

    @Autowired
    private AuthorityRepository authorityRepository;

    public UserGuideDocumentServiceImpl(UserGuideDocumentRepository userGuideDocumentRepository) {
        this.userGuideDocumentRepository = userGuideDocumentRepository;
    }

    /**
     * Save a userGuideDocument.
     *
     * @param userGuideDocument the entity to save
     * @return the persisted entity
     */
    @Override
    public UserGuideDocument save(UserGuideDocument userGuideDocument) {
        log.debug("Request to save UserGuideDocument : {}", userGuideDocument);
        return userGuideDocumentRepository.save(userGuideDocument);
    }

    @Override
    public UserGuideDocument saveWithAuthorities(UserGuideDocumentDto userGuideDocument) {

        UserGuideDocument entity;
        if (userGuideDocument.getId() != null) {

            AuthorityUserGuideDocumentCriteria criteria = new AuthorityUserGuideDocumentCriteria();

            LongFilter documentIdFilter = new LongFilter();
            documentIdFilter.setEquals(userGuideDocument.getId());

            criteria.setUserGuideDocumentId(documentIdFilter);

            List<AuthorityUserGuideDocument> existedDocuments = authorityUserGuideDocumentQueryService.findByCriteria(criteria);

            if (!CollectionUtils.isEmpty(existedDocuments)) {
                documentRepository.delete(existedDocuments);

            }

            entity = findOne(userGuideDocument.getId());
        } else {
            entity = new UserGuideDocument();
        }

        BeanUtils.copyProperties(userGuideDocument, entity);
        UserGuideDocument document = save(entity);

        saveDocumentAuthorities(document, userGuideDocument.getAuthorities());

        return document;
    }

    private void saveDocumentAuthorities(UserGuideDocument document, List<Authority> authorities) {

        List<AuthorityUserGuideDocument> authorityUserGuideDocuments = new ArrayList<>();

        for (Authority authority : authorities) {
            AuthorityUserGuideDocument authorityUserGuideDocument = new AuthorityUserGuideDocument();
            authorityUserGuideDocument.setAuthority(authority);
            authorityUserGuideDocument.setUserGuideDocument(document);

            authorityUserGuideDocuments.add(authorityUserGuideDocument);
        }

        documentRepository.save(authorityUserGuideDocuments);
    }
    /**
     *  Get all the userGuideDocuments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserGuideDocument> findAll(Pageable pageable) {
        log.debug("Request to get all UserGuideDocuments");
        return userGuideDocumentRepository.findAll(pageable);
    }

    /**
     *  Get one userGuideDocument by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserGuideDocument findOne(Long id) {
        log.debug("Request to get UserGuideDocument : {}", id);
        return userGuideDocumentRepository.findOne(id);
    }

    /**
     *  Delete the  userGuideDocument by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserGuideDocument : {}", id);

        AuthorityUserGuideDocumentCriteria authorityUserGuideDocumentCriteria = new  AuthorityUserGuideDocumentCriteria();
        LongFilter userGuideIdFilter = new LongFilter();
        userGuideIdFilter.setEquals(id);

        authorityUserGuideDocumentCriteria.setUserGuideDocumentId(userGuideIdFilter);

        List<AuthorityUserGuideDocument> authorityUserGuideDocuments = authorityUserGuideDocumentQueryService.findByCriteria(authorityUserGuideDocumentCriteria);

        documentRepository.delete(authorityUserGuideDocuments);

        userGuideDocumentRepository.delete(id);
    }

    @Override
    public Page<UserGuideDocumentDto> searchDocumentsWithAuthorites(UserGuideDocumentCriteria criteria, Pageable pageable) {

        Page<UserGuideDocument> userGuideDocuments = userGuideDocumentQueryService.findByCriteria(criteria, pageable);

        List<UserGuideDocumentDto> documentDtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(userGuideDocuments.getContent())) {
            return null;
        }

        for (UserGuideDocument document : userGuideDocuments.getContent()) {

            UserGuideDocumentDto documentDto = new UserGuideDocumentDto();
            BeanUtils.copyProperties(document, documentDto);

            AuthorityUserGuideDocumentCriteria authorityUserGuideDocumentCriteria = new  AuthorityUserGuideDocumentCriteria();
            LongFilter userGuideIdFilter = new LongFilter();
            userGuideIdFilter.setEquals(document.getId());

            authorityUserGuideDocumentCriteria.setUserGuideDocumentId(userGuideIdFilter);

            List<AuthorityUserGuideDocument> authorityUserGuideDocuments = authorityUserGuideDocumentQueryService.findByCriteria(authorityUserGuideDocumentCriteria);
            List<Authority> authorities = authorityUserGuideDocuments.stream().map(AuthorityUserGuideDocument::getAuthority).collect(Collectors.toList());

            documentDto.setAuthorities(authorities);

            documentDtos.add(documentDto);
        }

        Page<UserGuideDocumentDto> page = new PageImpl<>(documentDtos, pageable, userGuideDocuments.getTotalElements());

        return page;
    }

    @Override
    public List<UserGuideDocument> getCurrentUserDocuments() {

        List<String> currentUserAuthorities = SecurityUtils.getCurrentUserAuthorities();

        if (CollectionUtils.isEmpty(currentUserAuthorities)) return new ArrayList<>();

        List<Authority> authorities = authorityRepository.findByNameIn(currentUserAuthorities);

        return null;
    }
}

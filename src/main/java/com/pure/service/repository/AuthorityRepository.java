package com.pure.service.repository;

import com.pure.service.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {

    List<Authority> findByNameIn(List<String> authorityNames);
}

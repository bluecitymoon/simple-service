package com.pure.service.service.impl;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.Product;
import com.pure.service.region.RegionUtils;
import com.pure.service.repository.ProductRepository;
import com.pure.service.service.ClassArrangementQueryService;
import com.pure.service.service.ClassArrangementService;
import com.pure.service.service.ProductQueryService;
import com.pure.service.service.ProductService;
import com.pure.service.service.dto.ClassArrangementCriteria;
import com.pure.service.service.dto.ProductCriteria;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;


/**
 * Service Implementation for managing Product.
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService{

    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;

    @Autowired
    private ProductQueryService productQueryService;

    @Autowired
    private ClassArrangementQueryService classArrangementQueryService;

    @Autowired
    private ClassArrangementService classArrangementService;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Save a product.
     *
     * @param product the entity to save
     * @return the persisted entity
     */
    @Override
    public Product save(Product product) {

        log.debug("Request to save Product : {}", product);

        if (product.getId() == null) {

            ProductCriteria productCriteria = new ProductCriteria();

            StringFilter classCodeFilter = new StringFilter();
            classCodeFilter.setEquals(product.getCode());

            productCriteria.setCode(classCodeFilter);

            LongFilter longFilter = new LongFilter();
            longFilter.setEquals(RegionUtils.getRegionIdForCurrentUser());

            productCriteria.setRegionId(longFilter);

            List<Product> existedCodeClass = productQueryService.findByCriteria(productCriteria);

            if (!CollectionUtils.isEmpty(existedCodeClass)) {
                throw new RuntimeException("该编号已存在");
            }

        }

        else {

            Product originalClass = findOne(product.getId());

            //班级老师发生变化时，需要将排课老师更新
            if (originalClass.getTeacher() != null && product.getTeacher() != null && originalClass.getTeacher() != product.getTeacher()) {

                ClassArrangementCriteria classArrangementCriteria = new ClassArrangementCriteria();

                LongFilter clazzIdFilter = new LongFilter();
                clazzIdFilter.setEquals(product.getId());

                classArrangementCriteria.setClazzId(clazzIdFilter);

                List<ClassArrangement> classArrangements = classArrangementQueryService.findByCriteria(classArrangementCriteria);
                for (ClassArrangement classArrangement : classArrangements) {

                    if (classArrangement.getActualTeacher() == null) {

                        classArrangement.setPlanedTeacher(product.getTeacher());
                        classArrangementService.save(classArrangement);
                    }
                }
            }
        }

        return productRepository.save(product);
    }

    /**
     *  Get all the products.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Product> findAll(Pageable pageable) {
        log.debug("Request to get all Products");
        return productRepository.findAll(pageable);
    }

    /**
     *  Get one product by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Product findOne(Long id) {
        log.debug("Request to get Product : {}", id);
        return productRepository.findOne(id);
    }

    /**
     *  Delete the  product by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Product : {}", id);
        productRepository.delete(id);
    }
}

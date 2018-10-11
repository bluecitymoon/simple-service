package com.pure.service.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A ContractPackage.
 */
@Entity
@Table(name = "contract_package")
public class ContractPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "comments")
    private String comments;

    @OneToMany(mappedBy = "contractPackage")
//    @JsonIgnore
    private Set<ContractTemplate> contractTemplates = new HashSet<>();

    @ManyToOne
    private CustomerCardType customerCardType;

    @ManyToOne
    private ContractPackageType type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ContractPackage name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public ContractPackage code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getComments() {
        return comments;
    }

    public ContractPackage comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<ContractTemplate> getContractTemplates() {
        return contractTemplates;
    }

    public ContractPackage contractTemplates(Set<ContractTemplate> contractTemplates) {
        this.contractTemplates = contractTemplates;
        return this;
    }

    public ContractPackage addContractTemplate(ContractTemplate contractTemplate) {
        this.contractTemplates.add(contractTemplate);
        contractTemplate.setContractPackage(this);
        return this;
    }

    public ContractPackage removeContractTemplate(ContractTemplate contractTemplate) {
        this.contractTemplates.remove(contractTemplate);
        contractTemplate.setContractPackage(null);
        return this;
    }

    public void setContractTemplates(Set<ContractTemplate> contractTemplates) {
        this.contractTemplates = contractTemplates;
    }

    public CustomerCardType getCustomerCardType() {
        return customerCardType;
    }

    public ContractPackage customerCardType(CustomerCardType customerCardType) {
        this.customerCardType = customerCardType;
        return this;
    }

    public void setCustomerCardType(CustomerCardType customerCardType) {
        this.customerCardType = customerCardType;
    }

    public ContractPackageType getType() {
        return type;
    }

    public ContractPackage type(ContractPackageType contractPackageType) {
        this.type = contractPackageType;
        return this;
    }

    public void setType(ContractPackageType contractPackageType) {
        this.type = contractPackageType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ContractPackage contractPackage = (ContractPackage) o;
        if (contractPackage.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contractPackage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContractPackage{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", comments='" + getComments() + "'" +
            "}";
    }
}

package com.pure.service.service.dto.request;

import com.pure.service.domain.ClassArrangement;
import com.pure.service.domain.Product;

import java.io.Serializable;
import java.util.List;

public class BatchReassignClassArrangement implements Serializable {

    private Product newClass;
    private List<ClassArrangement>  arrangements;

    public Product getNewClass() {
        return newClass;
    }

    public void setNewClass(Product newClass) {
        this.newClass = newClass;
    }

    public List<ClassArrangement> getArrangements() {
        return arrangements;
    }

    public void setArrangements(List<ClassArrangement> arrangements) {
        this.arrangements = arrangements;
    }
}

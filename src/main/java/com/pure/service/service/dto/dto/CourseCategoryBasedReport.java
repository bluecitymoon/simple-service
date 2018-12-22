package com.pure.service.service.dto.dto;

import com.pure.service.domain.Contract;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CourseCategoryBasedReport implements Serializable {

    private List<String> categories = new ArrayList<>();
    private List<Integer> counter = new ArrayList<>();
    private List<List<Contract>> contracts = new ArrayList<>();

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Integer> getCounter() {
        return counter;
    }

    public void setCounter(List<Integer> counter) {
        this.counter = counter;
    }

    public List<List<Contract>> getContracts() {
        return contracts;
    }

    public void setContracts(List<List<Contract>> contracts) {
        this.contracts = contracts;
    }
}

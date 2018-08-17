package com.pure.service.service.dto.request;

import com.pure.service.domain.Product;
import com.pure.service.domain.Student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class StudentsClassRequest implements Serializable {

    private Product product;
    private List<Student> students = new ArrayList<>();

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

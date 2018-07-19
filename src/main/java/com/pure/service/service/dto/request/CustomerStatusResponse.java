package com.pure.service.service.dto.request;

import java.io.Serializable;
import java.util.List;

public class CustomerStatusResponse implements Serializable {

    private List<ReportElement> elements;

    public List<ReportElement> getElements() {
        return elements;
    }

    public void setElements(List<ReportElement> elements) {
        this.elements = elements;
    }
}

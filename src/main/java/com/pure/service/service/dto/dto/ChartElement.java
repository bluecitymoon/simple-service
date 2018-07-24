package com.pure.service.service.dto.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChartElement implements Serializable {

    private String chartName;
    private String chartCode;
    private List<String> xValues = new ArrayList<>();
    private List<Integer> yValues = new ArrayList<>();

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }

    public List<String> getxValues() {
        return xValues;
    }

    public void setxValues(List<String> xValues) {
        this.xValues = xValues;
    }

    public List<Integer> getyValues() {
        return yValues;
    }

    public void setyValues(List<Integer> yValues) {
        this.yValues = yValues;
    }

    public String getChartCode() {
        return chartCode;
    }

    public void setChartCode(String chartCode) {
        this.chartCode = chartCode;
    }
}

package com.pure.service.service.dto.dto;

import com.pure.service.service.dto.request.ReportElement;

import java.io.Serializable;
import java.util.List;

public class CombinedReport implements Serializable {

    private List<ReportElement> data;
    private List<ChartElement> chart;

    public List<ReportElement> getData() {
        return data;
    }

    public void setData(List<ReportElement> data) {
        this.data = data;
    }

    public List<ChartElement> getChart() {
        return chart;
    }

    public void setChart(List<ChartElement> chart) {
        this.chart = chart;
    }
}

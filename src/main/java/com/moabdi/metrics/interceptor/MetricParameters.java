package com.moabdi.metrics.interceptor;

import com.moabdi.metrics.annotation.Metric.CounterType;

import java.util.ArrayList;
import java.util.List;


class MetricParameters {

    private String name;
    private String description;
    private String[] labelNames = {};
    private String[] labelValues = {};
    private List<CounterType> counterTypes = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getLabelNames() {
        return labelNames;
    }

    public void setLabelNames(String[] labelNames) {
        this.labelNames = labelNames;
    }

    public String[] getLabelValues() {
        return labelValues;
    }

    public void setLabelValues(String[] labelValues) {
        this.labelValues = labelValues;
    }

    public List<CounterType> getCounterTypes() {
        return counterTypes;
    }

    public void setCounterTypes(List<CounterType> counterTypes) {
        this.counterTypes = counterTypes;
    }
}

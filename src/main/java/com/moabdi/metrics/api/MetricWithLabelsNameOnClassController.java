package com.moabdi.metrics.api;

import com.moabdi.metrics.annotation.Metric;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("classLabelName")
@Metric(value = "metric_test_class_with_labels", labelNames = "endpoint")
public class MetricWithLabelsNameOnClassController {

    @GetMapping("/first")
    @ResponseBody
    @Metric(labelValues = "testFirstMethod")
    public String firstMethod() {
        return "firstMethod";
    }

    @GetMapping("/second")
    @ResponseBody
    @Metric(labelValues = "testSecondMethod")
    public String secondMethod() {
        return "secondMethod";
    }

}

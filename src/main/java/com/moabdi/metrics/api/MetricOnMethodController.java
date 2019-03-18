package com.moabdi.metrics.api;

import com.moabdi.metrics.annotation.Metric;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("custom")
public class MetricOnMethodController {

    @GetMapping("/first")
    @ResponseBody
    @Metric(name = "metric_test_method_with_labels",
            labelNames = "endpoint", labelValues = "testFirstMethod",
            counterType = Metric.CounterType.TOTAL)
    public String firstMethod() {
        return "firstMethod";
    }

    @GetMapping("/second")
    @ResponseBody
    public String secondMethod() {
        return "secondMethod";
    }

}

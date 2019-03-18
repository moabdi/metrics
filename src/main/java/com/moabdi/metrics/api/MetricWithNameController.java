package com.moabdi.metrics.api;

import com.moabdi.metrics.annotation.Metric;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("withName")
@Metric("metric_test_metric_with_name_only")
public class MetricWithNameController {

    @GetMapping("/first")
    @ResponseBody
    public String firtMethod() {
        return "firstMethod";
    }

    @GetMapping("/second")
    @ResponseBody
    public String testMethod() {
        return "secondMethod";
    }
}

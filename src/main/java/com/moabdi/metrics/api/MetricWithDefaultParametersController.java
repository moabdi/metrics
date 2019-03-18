package com.moabdi.metrics.api;

import com.moabdi.metrics.annotation.Metric;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("default")
@Metric
public class MetricWithDefaultParametersController {

    @GetMapping("first")
    @ResponseBody
    public String firtMethod() {
        return "firstMethod";
    }

    @GetMapping("/second")
    @ResponseBody
    public String secondMethod() {
        return "secondMethod";
    }

    private String thirdMethod() {
        return "thirdMethod";
    }
}

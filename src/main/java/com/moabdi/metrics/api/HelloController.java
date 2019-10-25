package com.moabdi.metrics.api;

import com.moabdi.metrics.annotation.Metric;
import org.springframework.web.bind.annotation.*;

import static com.moabdi.metrics.annotation.Metric.CounterType.SUCCESS;

@RestController
@RequestMapping(value = "/hello")
@Metric(value = "metric_hello", labelNames = "category")
public class HelloController {

    @GetMapping("/point")
    @ResponseBody
    @Metric(labelValues = "point")
    public String point() {
        return "point";
    }

    @GetMapping("/test")
    @ResponseBody
    @Metric(labelValues = "test")
    public String test() {
        return "test";
    }

    @DeleteMapping
    @ResponseBody
    @Metric(name = "delete_metric", counterType = SUCCESS)
    public String delete() {
        return "deleted";
    }

}

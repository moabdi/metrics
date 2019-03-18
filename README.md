# metrics
Use Prometheus Metrics with annothation custom annotation

If you use Prometheus, you can simply use annotation Metric to count total requests, succussful requests and/or time.

Counters go up, and reset when the process restarts.


#### Default parameters

```java
import com.moabdi.metrics.annotation.Metric;

@Metric
class YourClass {

  public void processRequest() {
    // Your code here.
  }
}
```
this code will produce three metrics by default 
*  your_class_request_total(method="processRequest",} 
*  your_class_request_succussful(method="processRequest",}  
*  your_class_request_time(method="processRequest",}


the flowing example produce the same result
```java
import com.moabdi.metrics.annotation.Metric;
class YourClass {

  @Metric
  public void processRequest() {
    // Your code here.
  }
}
```

#### Custom parameters

```java
import com.moabdi.metrics.annotation.Metric;

@Metric(name="test", labelNames="endpoint"}
class YourClass {
  
  @Metric(labelValues="process"}
  public void processRequest() {
    // Your code here.
  }
}
```

Or

```java
import com.moabdi.metrics.annotation.Metric;

class YourClass {
  
  @Metric(name="test", labelNames="endpoint", labelValues="process"}
  public void processRequest() {
    // Your code here.
  }
}
```


The result will be like this: 
*  test_request_total(endpoint="process",} 
*  test_request_succussful(endpoint="process",}  
*  test_request_time(endpoint="process",}


#### Others possibilities

```java
import com.moabdi.metrics.annotation.Metric;

@Metric(name="test", labelNames="endpoint"}
class YourClass {
  
  @Metric(labelValues="first"}
  public void first() {
    // Your code here.
  }
  @Metric(labelValues="second"}
  public void second() {
    // Your code here.
  }
  
  @Metric(name="test_method_third", labelNames="api", labelValues="play"}
  public void third() {
    // Your code here.
  }
  
  
  @Metric(name="test_single_counter", labelNames="function", labelValues="process", counterType=Metric.CounterType.SUCCESSFUL}
  public void fourth() {
    // Your code here.
  }
}
```


The result will be like this: 
*  test_request_total(endpoint="first",} 
*  test_request_total(endpoint="second",} 
*  test_request_succussful(endpoint="first",}  
*  test_request_succussful(endpoint="second",}  
*  test_request_time(endpoint="first",}
*  test_request_time(endpoint="second",}

*  test_method_third_request_total(api="play",} 
*  test_method_third_succussful(api="play",}  
*  test_method_third_time(api="play",}

*  test_single_counter{function="process",} 



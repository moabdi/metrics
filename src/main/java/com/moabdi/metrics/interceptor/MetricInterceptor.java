package com.moabdi.metrics.interceptor;

import com.moabdi.metrics.annotation.Metric;
import com.moabdi.metrics.annotation.Metric.CounterType;
import com.moabdi.metrics.common.ArrayUtils;
import io.prometheus.client.Counter;
import jdk.nashorn.internal.objects.NativeString;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.springframework.util.StringUtils.isEmpty;

@Aspect
@Component
public class MetricInterceptor {

    private static Map<String, Counter> METRICS = new HashMap();

    @Around(value = "@within(com.moabdi.metrics.annotation.Metric) " +
            "|| @annotation(com.moabdi.metrics.annotation.Metric)")
    public Object
    metrics(ProceedingJoinPoint joinPoint) throws Throwable {
        MetricParameters metricParameters = getMetricParameters(joinPoint);
        if (metricParameters.getCounterTypes().contains(CounterType.TOTAL)) {
            getCounter(metricParameters, CounterType.TOTAL).inc();
        }

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();
        long time = end - start;

        if (metricParameters.getCounterTypes().contains(CounterType.TIME)) {
            getCounter(metricParameters, CounterType.TIME).inc(time);
        }
        if (metricParameters.getCounterTypes().contains(CounterType.SUCCESS)) {
            getCounter(metricParameters, CounterType.SUCCESS).inc();
        }

        return result;
    }


    private Counter.Child getCounter(MetricParameters metricParameters, CounterType counterType) {

        String metricName = metricParameters.getCounterTypes().size() > 1 ? metricParameters.getName()
                + "_request_" + NativeString.toLowerCase(counterType.name())
                : metricParameters.getName();

        if (!METRICS.containsKey(metricName)) {
            METRICS.put(metricName, Counter.build()
                    .name(metricName)
                    .labelNames(metricParameters.getLabelNames())
                    .help(metricParameters.getDescription()).register());
        }

        return METRICS.get(metricName).labels(metricParameters.getLabelValues());
    }

    private MetricParameters getMetricParameters(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Metric metricClass = method.getDeclaringClass().getAnnotation(Metric.class);
        Metric metricMethod = method.getAnnotation(Metric.class);
        MetricParameters parameters = new MetricParameters();

        if (nonNull(metricMethod) && (isNull(metricClass) || !isEmpty(metricMethod.name()))) {
            initParameters(metricMethod, parameters);
        } else if (nonNull(metricClass)) {
            initParameters(metricClass, parameters);
            if (nonNull(metricMethod)) {
                combineParametres(metricMethod, parameters);
            }
        }

        setDefaultParameterValues(method, parameters);
        checkMetricParametres(parameters);

        return parameters;
    }

    private void initParameters(Metric metric, MetricParameters parameters) {
        parameters.setName(metric.name());
        parameters.setLabelNames(metric.labelNames());
        parameters.setLabelValues(metric.labelValues());
        parameters.setCounterTypes(asList(metric.counterType()));
        parameters.setDescription(metric.description());
    }

    private void combineParametres(Metric metricMethod, MetricParameters parameters) {
        if (ArrayUtils.isNotEmpty(metricMethod.labelNames())) {
            throw new IllegalArgumentException("labelNames must specified with name for metric: "
                    + parameters.getName());
        }
        parameters.setCounterTypes(asList(metricMethod.counterType()));
        parameters.setLabelNames(parameters.getLabelNames());
        parameters.setLabelValues(metricMethod.labelValues());
    }

    private void setDefaultParameterValues(Method method, MetricParameters parameters) {
        if (isEmpty(parameters.getName())) {
            parameters.setName(method.getDeclaringClass()
                    .getSimpleName()
                    .replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase());

            if (parameters.getLabelNames().length == 0) {
                parameters.setLabelNames(new String[]{"method"});
                parameters.setLabelValues(new String[]{method.getName()});
            }
        }

        if (isEmpty(parameters.getDescription())) {
            String description = method.getDeclaringClass()
                    .getSimpleName()
                    .replaceAll("(.)(\\p{Upper})", "$1 $2").toLowerCase();
            parameters.setDescription(description);
        }
    }

    private void checkMetricParametres(MetricParameters metricParameters) {

        if (metricParameters.getLabelValues().length != metricParameters.getLabelNames().length) {
            throw new IllegalArgumentException("Incorrect number of labels for metric: "
                    + metricParameters.getName());
        }
    }
}

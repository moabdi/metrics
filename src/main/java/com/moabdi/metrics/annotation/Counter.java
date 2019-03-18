package com.moabdi.metrics.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD})
@Retention(RUNTIME)
public @interface Counter {

    enum Type {TOTAL, SUCCESSFUL, TIME}

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String description() default "";

    String[] labelValues() default {};

    String[] labelNames() default {};

    Type[] type() default {Counter.Type.TOTAL, Counter.Type.SUCCESSFUL, Counter.Type.TIME};

}

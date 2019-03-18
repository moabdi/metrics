package com.moabdi.metrics.common;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public interface ArrayUtils {


    static String[] toArray(String[]... object) {
        List<String> list = new ArrayList<>();
        for (String[] i : object) {
            list.addAll(asList(i));
        }
        return list.toArray(new String[list.size()]);
    }

    static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }

    static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

}

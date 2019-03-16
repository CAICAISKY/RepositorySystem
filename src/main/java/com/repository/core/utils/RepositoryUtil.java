package com.repository.core.utils;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

/**
 * 持久层操作相关工具类
 * @author schuyler
 */
public class RepositoryUtil {

    public static <T> Example<T> getExactExample(T t) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll();
        return Example.of(t, exampleMatcher);
    }
}

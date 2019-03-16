package com.repository.core.constants;

/**
 * Redis常量
 * @author schuyler
 */
public interface RedisConstant {

    /**
     * redis的健前缀
     */
    String REDIS_TOKEN = "token_%s";

    Long REDIS_EXPIRE = 72000L;
}

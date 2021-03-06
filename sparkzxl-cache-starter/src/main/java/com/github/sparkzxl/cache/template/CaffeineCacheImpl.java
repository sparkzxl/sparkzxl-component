package com.github.sparkzxl.cache.template;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

/**
 * description: Caffeine本地缓存实现
 *
 * @author zhouxinlei
 */
@SuppressWarnings("unchecked")
public class CaffeineCacheImpl implements GeneralCacheService {

    long maxSize = 1000L;
    private final Cache<String, Cache<String, Object>> cacheMap;

    public CaffeineCacheImpl() {
        this.cacheMap = Caffeine.newBuilder().maximumSize(this.maxSize).build();
    }

    @Override
    public void set(String key, Object value) {
        set(key, value, null, null);
    }

    @Override
    public void set(String key, Object value, Long expireTime, TimeUnit timeUnit) {
        Cache<String, Object> cache;
        if (expireTime == null) {
            cache = Caffeine.newBuilder().maximumSize(this.maxSize).build();
        } else {
            cache = Caffeine.newBuilder().expireAfterWrite(expireTime, timeUnit).maximumSize(this.maxSize).build();
        }
        cache.put(key, value);
        this.cacheMap.put(key, cache);
    }

    @Override
    public boolean setIfAbsent(String key, Object value, Long expireTime, TimeUnit timeUnit) {
        Cache<String, Object> ifPresent = this.cacheMap.getIfPresent(key);
        if (ObjectUtils.isEmpty(ifPresent)) {
            set(key, value, expireTime, timeUnit);
            return true;
        }
        return false;
    }

    @Override
    public boolean setIfAbsent(String key, Object value) {
        Cache<String, Object> ifPresent = this.cacheMap.getIfPresent(key);
        if (ObjectUtils.isEmpty(ifPresent)) {
            set(key, value);
            return true;
        }
        return false;
    }

    @Override
    public Long increment(String key) {
        Function<Long, Long> function = x -> {
            LongAdder longAdder = new LongAdder();
            longAdder.increment();
            return longAdder.longValue();
        };
        return get(key, function, 0L, null, null);
    }


    @Override
    public Long increment(String key, long delta) {
        Function<Long, Long> function = x -> {
            LongAdder longAdder = new LongAdder();
            longAdder.add(delta);
            return longAdder.longValue();
        };
        return get(key, function, delta, null, null);
    }

    @Override
    public Long decrement(String key) {
        Function<Long, Long> function = x -> {
            LongAdder longAdder = new LongAdder();
            longAdder.decrement();
            return longAdder.longValue();
        };
        return get(key, function, 0L, null, null);
    }

    @Override
    public Long decrement(String key, long delta) {
        Function<Long, Long> function = x -> {
            LongAdder longAdder = new LongAdder();
            longAdder.add(-delta);
            return longAdder.longValue();
        };
        return get(key, function, delta, null, null);
    }

    @Override
    public void remove(String... keys) {
        for (String key : keys) {
            this.cacheMap.invalidate(key);
        }
    }

    @Override
    public <T> T get(String key) {
        return get(key, null, null, null, null);
    }

    @Override
    public <T> T get(String key, Function<String, T> function) {
        return get(key, function, key, null, null);
    }

    @Override
    public <T, M> T get(String key, Function<M, T> function, M funcParam) {
        return get(key, function, funcParam, null, null);
    }

    @Override
    public <T> T get(String key, Function<String, T> function, Long expireTime, TimeUnit timeUnit) {
        return get(key, function, key, expireTime, timeUnit);
    }

    @Override
    public <T, M> T get(String key, Function<M, T> function, M funcParam, Long expireTime, TimeUnit timeUnit) {
        T obj = null;
        if (StringUtils.isEmpty(key)) {
            return null;
        }
        Cache<String, Object> ifPresent = this.cacheMap.getIfPresent(key);
        if (ifPresent == null && function != null) {
            obj = function.apply(funcParam);
            if (obj != null) {
                set(key, obj, expireTime, timeUnit);
            }
        } else if (ifPresent != null) {
            obj = (T) ifPresent.getIfPresent(key);
        }
        return obj;
    }


    @Override
    public void flushDb() {
        this.cacheMap.invalidateAll();
    }

    @Override
    public boolean exists(String key) {
        Cache cache = this.cacheMap.getIfPresent(key);
        if (cache == null) {
            return false;
        } else {
            cache.cleanUp();
            return cache.estimatedSize() > 0L;
        }
    }

}

package com.airport.airport.controller;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CacheController {

    private final CacheManager cacheManager;

    public CacheController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @GetMapping(value = "/inspectCache")
    public void inspectCache(@RequestParam String cacheName) {
        // Retrieve the CaffeineCache from the CacheManager
        Cache cache = cacheManager.getCache(cacheName);

        if (cache != null) {
            // Check if the cache implementation is CaffeineCache
            if (cache instanceof CaffeineCache caffeineCache) {

                // Get the native Caffeine Cache
                com.github.benmanes.caffeine.cache.Cache<Object, Object> nativeCache =
                        caffeineCache.getNativeCache();

                // Iterate over the entries in the cache and print key-value pairs
                for (Map.Entry<Object, Object> entry : nativeCache.asMap().entrySet()) {
                    System.out.println("Key = " + entry.getKey());
                    System.out.println("Value = " + entry.getValue());
                }
            } else {
                System.out.println("Cache is not a CaffeineCache: " + cacheName);
            }
        } else {
            System.out.println("Cache not found: " + cacheName);
        }
    }
}



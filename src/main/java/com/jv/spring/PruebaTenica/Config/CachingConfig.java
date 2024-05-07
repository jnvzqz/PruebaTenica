package com.jv.spring.PruebaTenica.Config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CachingConfig {
    @Bean(name = "springCM") 
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("entity");
    }
}
package no.nav.fo.nightking.config;


import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;

import static net.sf.ehcache.store.MemoryStoreEvictionPolicy.LRU;
import static no.nav.sbl.dialogarena.common.abac.pep.context.AbacContext.ABAC_CACHE;

@EnableCaching
public class CacheConfig {

    public static final String ARENA = "arena";

    private static final CacheConfiguration ARENA_CACHE = kortCache(ARENA);

    @Bean
    public CacheManager cacheManager() {
        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(ABAC_CACHE);
        config.addCache(ARENA_CACHE);
        return new EhCacheCacheManager(net.sf.ehcache.CacheManager.newInstance(config));
    }

    private static CacheConfiguration kortCache(String navn) {
        return cache(navn, 10);
    }

    private static CacheConfiguration cache(String navn, int varighetSekunder) {
        return new CacheConfiguration(navn, 10000)
                .memoryStoreEvictionPolicy(LRU)
                .timeToIdleSeconds(varighetSekunder)
                .timeToLiveSeconds(varighetSekunder);
    }

}


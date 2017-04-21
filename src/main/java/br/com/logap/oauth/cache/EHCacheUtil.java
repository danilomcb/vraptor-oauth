package br.com.logap.oauth.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

class EHCacheUtil {

    private static CacheManager cacheMgr = null;
    private static Ehcache ehcache = null;

    static Ehcache getCache(String cacheName, boolean eterno, int tempoVidaSegundos, int tempoVidaDesdeUltimaMudanca, int maxRegistroHeap, int maxRegistroDisco) {
        if (cacheMgr == null) {
            cacheMgr = CacheManager.create();
        }

        if (cacheMgr != null) {
            if (!cacheMgr.cacheExists(cacheName)) {
                Cache testCache = new Cache(getCacheConfiguration(cacheName, maxRegistroHeap, eterno, maxRegistroDisco, tempoVidaSegundos, tempoVidaDesdeUltimaMudanca));
                cacheMgr.addCache(testCache);
            }

            ehcache = cacheMgr.getEhcache(cacheName);
        }

        return ehcache;
    }

    private static CacheConfiguration getCacheConfiguration(String cacheName, int maxRecordHeap, boolean eternal, int maxRecordDisk, int lifeTimeSeconds, int lifeTimeSinceLastChange) {
        return new CacheConfiguration(cacheName, maxRecordHeap)
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
                .eternal(eternal)
                .maxEntriesLocalDisk(maxRecordDisk)
                .timeToIdleSeconds(lifeTimeSinceLastChange)
                .timeToLiveSeconds(lifeTimeSeconds)
                .diskExpiryThreadIntervalSeconds(86400)
                .persistence(new PersistenceConfiguration().strategy(Strategy.NONE));
    }
}

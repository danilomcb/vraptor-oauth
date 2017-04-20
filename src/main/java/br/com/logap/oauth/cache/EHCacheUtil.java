package br.com.logap.oauth.cache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

/**
 * 
 * @author LogAp
 *
 */
public class EHCacheUtil {

    private static CacheManager cacheMgr = null;
    private static Ehcache ehcache = null;

    public static Ehcache getCache(String cacheName, boolean eterno, int tempoVidaSegundos, int tempoVidaDesdeUltimaMudanca, int maxRegistroHeap, int maxRegistroDisco) {
        if (cacheMgr == null) {
            cacheMgr = CacheManager.create();
        }

        if (cacheMgr != null) {
            if (!cacheMgr.cacheExists(cacheName)) {
                Cache testCache = new Cache(getConfiguracaoCache(cacheName, maxRegistroHeap, eterno, maxRegistroDisco, tempoVidaSegundos, tempoVidaDesdeUltimaMudanca));
                cacheMgr.addCache(testCache);
            }

            ehcache = cacheMgr.getEhcache(cacheName);
        }

        return ehcache;
    }

    private static CacheConfiguration getConfiguracaoCache(String cacheName, int maxRegistroHeap, boolean eterno, int maxRegistroDisco, int tempoVidaSegundos, int tempoVidaDesdeUltimaMudanca) {
        return new CacheConfiguration(cacheName, maxRegistroHeap)
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
                .eternal(eterno)
                .maxEntriesLocalDisk(maxRegistroDisco)
                .timeToIdleSeconds(tempoVidaDesdeUltimaMudanca)
                .timeToLiveSeconds(tempoVidaSegundos)
                .diskExpiryThreadIntervalSeconds(86400)
                .persistence(new PersistenceConfiguration().strategy(Strategy.NONE));
    }
}

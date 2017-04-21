package br.com.logap.oauth.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import java.util.HashSet;
import java.util.Set;

public class TemporaryCacheForChange<K, V> implements Cache<K, V> {

    private final Ehcache cache;

    static TemporaryCacheForChange create(int lifeTimeSinceLastChange, int maxRecordsHeap, int maxRecordsDisk) {
        return new TemporaryCacheForChange("cacheTokens", false, 0, lifeTimeSinceLastChange, maxRecordsHeap, maxRecordsDisk);
    }

    private TemporaryCacheForChange(String name, boolean eternal, int lifeTimeSeconds, int lifeTimeSinceLastChange, int maxRecordsHeap, int maxRecordsDisk) {
        cache = EHCacheUtil.getCache(name, eternal, lifeTimeSeconds, lifeTimeSinceLastChange, maxRecordsHeap, maxRecordsDisk);
    }

    @Override
    public void insert(K key, V value) {
        getCache().put(new Element(key, value));
    }

    @Override
    public V getToken(K key) {
        return (V) getCache().get(key).getObjectValue();
    }

    @Override
    public boolean containsKey(K key) {
        return getCache().isKeyInCache(key);
    }

    @Override
    public boolean removeToken(K key) {
        return getCache().remove(key);
    }

    @Override
    public boolean containsToken(K key) {
        return getCache().get(key) != null;
    }

    private Ehcache getCache() {
        return cache;
    }

    @Override
    public Set<K> getKeys() {
        return new HashSet<>(cache.getKeys());
    }

}

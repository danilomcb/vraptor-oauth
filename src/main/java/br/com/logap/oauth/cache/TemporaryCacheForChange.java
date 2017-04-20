package br.com.logap.oauth.cache;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author LogAp
 *
 * @param <K>
 * @param <V>
 */
public class TemporaryCacheForChange<K, V> implements Cache<K, V> {

    private final Ehcache cache;

	public static TemporaryCacheForChange create(String nome, int tempoVidaDesdeUltimaMudanca, int maxRegistroHeap, int maxRegistroDisco) {
        return new TemporaryCacheForChange(nome, false, 0,tempoVidaDesdeUltimaMudanca, maxRegistroHeap, maxRegistroDisco);
    }

    private TemporaryCacheForChange(String nome, boolean eterno, int tempoVidaSegundos, int tempoVidaDesdeUltimaMudanca, int maxRegistroHeap, int maxRegistroDisco) {
        cache = EHCacheUtil.getCache(nome, eterno, tempoVidaSegundos, tempoVidaDesdeUltimaMudanca, maxRegistroHeap, maxRegistroDisco);
    }

    @Override
    public void insert(K chave, V valor) {
        getCache().put(new Element(chave, valor));
    }

    @Override
    public V getToken(K chave){
    	return (V) getCache().get(chave).getObjectValue();
    }

    @Override
    public boolean containsKey(K chave) {
        return getCache().isKeyInCache(chave);
    }
    
    @Override
	public boolean removeToken(K chave) {
		return getCache().remove(chave);
	}
    
    @Override
    public boolean containsToken(K chave) {
        return getCache().get(chave) != null;
    }

    private Ehcache getCache() {
        return cache;
    }

	@Override
    public Set<K> getKeys() {
        return new HashSet<>(cache.getKeys());
    }

}

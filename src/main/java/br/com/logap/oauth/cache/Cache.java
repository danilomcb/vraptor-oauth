package br.com.logap.oauth.cache;

import java.util.Set;

public interface Cache<K, V> {

    void insert(K chave, V valor);

    V getToken(K chave);

    boolean containsKey(K chave);
    
    boolean removeToken(K chave);

    boolean containsToken(K chave);

    Set<K> getKeys();

}

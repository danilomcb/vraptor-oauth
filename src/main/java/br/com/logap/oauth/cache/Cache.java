package br.com.logap.oauth.cache;

import java.util.Set;

/**
 * 
 * @author LogAp
 *
 * @param <K>
 * @param <V>
 */
public interface Cache<K, V> {

    /**
     * Insere um novo valor no cache
     *
     * @param chave Chave única que representa o valor
     * @param valor Valor a ser armazenado.
     */
    void insert(K chave, V valor);

    /**
     * Recupera um valor a partir da chave única.
     *
     * @param chave Chave do valor.
     * @return Valor armazenado ou null em caso do valor não existir no cache.
     */
    V getToken(K chave);

    /**
     * Retorna se uma determinada chave está presente no cache.
     *
     * @param chave Chave a ser verificada.
     * @return <code>true</code> caso esteja no cache e <code>false</code> caso contrário.
     */
    boolean containsKey(K chave);
    
    /**
     * Remove um elemento da cache.
     *
     * @param chave Chave a ser removida.
     * @return <code>true</code> caso seja removido com sucesso e <code>false</code> caso contrário.
     */
    boolean removeToken(K chave);
    
    /**
     * Retorna se uma determinada chave possui elemento.
     * @param chave Chave a ser verificada.
     * @return <code>true</code> caso o elemento exista e <code>false</code> caso contrário.
     */
    boolean containsToken(K chave);

    /**
     * Recupera as chaves do cache.
     *
     * @return Um set com todas as chaves do cache
     */
    Set<K> getKeys();

}

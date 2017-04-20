package br.com.logap.oauth.cache;

/**
 * Created by danilo-barros on 20/04/17.
 */
public interface ConfigurationCache {

    int getTokenLifeTime();
    int getMaxNumberElementsInMemory();
    int getMaxNumberElementsInDisk();

}

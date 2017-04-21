package br.com.logap.oauth.cache;

import java.io.Serializable;

/**
 * Created by danilo-barros on 20/04/17.
 */
public interface ConfigurationCache extends Serializable {

    int getTokenLifeTime();
    int getMaxNumberElementsInMemory();
    int getMaxNumberElementsInDisk();

}

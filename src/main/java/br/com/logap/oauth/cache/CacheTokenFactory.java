package br.com.logap.oauth.cache;

import br.com.logap.oauth.Token;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 
 * @author LogAp
 *
 */
@Singleton
public class CacheTokenFactory {

	private final TemporaryCacheForChange<String, Token> cache;

	/**
	 * @deprecated CDI eyes only.
	 */
	public CacheTokenFactory() {
		this(null);
	}

	@Inject
	public CacheTokenFactory(ConfigurationCache configuration) {
		cache = TemporaryCacheForChange.create("cacheTokens",
				configuration.getTokenLifeTime(),
				configuration.getMaxNumberElementsInMemory(),
				configuration.getMaxNumberElementsInDisk());
	}

	public TemporaryCacheForChange<String, Token> getCache() {
		return cache;
	}

}

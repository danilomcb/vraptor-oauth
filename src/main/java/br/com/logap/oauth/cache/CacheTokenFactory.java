package br.com.logap.oauth.cache;

import br.com.logap.oauth.Token;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.Serializable;

@Singleton
public class CacheTokenFactory implements Serializable {

	private final TemporaryCacheForChange<String, Token> cache;

	public CacheTokenFactory() {
		this(null);
	}

	@Inject
	public CacheTokenFactory(ConfigurationCache configuration) {
		cache = TemporaryCacheForChange.create(
				configuration.getTokenLifeTime(),
				configuration.getMaxNumberElementsInMemory(),
				configuration.getMaxNumberElementsInDisk());
	}

	public TemporaryCacheForChange<String, Token> getCache() {
		return cache;
	}

}

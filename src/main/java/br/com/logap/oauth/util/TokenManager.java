/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logap.oauth.util;

import br.com.logap.oauth.Token;
import br.com.logap.oauth.AuthenticationUser;
import br.com.logap.oauth.cache.CacheTokenFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author LogAp
 */
@Singleton
public class TokenManager {

	private final CacheTokenFactory cacheTokenFactory;

    /**
     * @deprecated CDI eyes only.
     */
    public TokenManager() {
        this(null);
    }

    @Inject
    public TokenManager(CacheTokenFactory cacheTokenFactory) {
        this.cacheTokenFactory = cacheTokenFactory;
    }

    public Token addToken(String token, AuthenticationUser authenticationUser) {
    	Token tokenSalvar = new Token(token, authenticationUser);
		cacheTokenFactory.getCache().insert(token, tokenSalvar);
    	return tokenSalvar;
    }
    
    public boolean isValidToken(String token) {
        return cacheTokenFactory.getCache().containsToken(token);
    }
    
    public Token getToken(String token) {
        return cacheTokenFactory.getCache().getToken(token);
    }
    
}

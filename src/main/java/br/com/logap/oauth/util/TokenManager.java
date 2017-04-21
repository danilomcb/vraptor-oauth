/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.logap.oauth.util;

import br.com.logap.oauth.cache.ConfigurationCache;
import br.com.logap.oauth.exception.IncorrectParametersAuthenticationException;
import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 *
 * @author LogAp
 */
@Singleton
public class TokenManager implements Serializable {

	private final TokenManagerService tokenManagerService;
	private final ConfigurationCache configurationCache;

    /**
     * @deprecated CDI eyes only.
     */
    public TokenManager() {
        this(null, null);
    }

    @Inject
    public TokenManager(TokenManagerService tokenManagerService, ConfigurationCache configurationCache) {
        this.tokenManagerService = tokenManagerService;
        this.configurationCache = configurationCache;
    }

    public String generateAuthenticationToken() {
        try {
            OAuthIssuer oauthIssuer = new OAuthIssuerImpl(new MD5Generator());
            final String accessToken = oauthIssuer.accessToken();
            tokenManagerService.addToken(accessToken);

            OAuthResponse response = OAuthASResponse.tokenResponse(HttpServletResponse.SC_OK)
                    .setAccessToken(accessToken)
                    .setExpiresIn(Long.toString(configurationCache.getTokenLifeTime()))
                    .buildJSONMessage();

            return response.getBody();
        } catch (OAuthSystemException e) {
            throw new IncorrectParametersAuthenticationException();
        }
    }

    public String generateOauthAuthenticationFail() {
        OAuthResponse response;
        try {
            response = OAuthASResponse
                    .errorResponse(HttpServletResponse.SC_BAD_REQUEST)
                    .setError(OAuthError.TokenResponse.INVALID_GRANT)
                    .setErrorDescription("invalid username or password")
                    .buildJSONMessage();
            return response.getBody();
        } catch (OAuthSystemException e) {
            throw new IncorrectParametersAuthenticationException();
        }
    }

    public void removeToken() {
        tokenManagerService.removeToken();
    }
}

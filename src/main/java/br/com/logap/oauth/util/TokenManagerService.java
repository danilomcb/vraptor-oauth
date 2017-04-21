package br.com.logap.oauth.util;

import br.com.logap.oauth.Token;
import br.com.logap.oauth.cache.CacheTokenFactory;
import br.com.logap.oauth.exception.IncorrectParametersAuthenticationException;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by danilo-barros on 21/04/17.
 */
public class TokenManagerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenManagerService.class);

    private final CacheTokenFactory cacheTokenFactory;
    private final HttpServletRequest httpServletRequest;

    /**
     * @deprecated CDI eyes only.
     */
    public TokenManagerService() {
        this(null, null);
    }

    @Inject
    public TokenManagerService(CacheTokenFactory cacheTokenFactory, HttpServletRequest httpServletRequest) {
        this.cacheTokenFactory = cacheTokenFactory;
        this.httpServletRequest = httpServletRequest;
    }

    void addToken(String token) {
        Token tokenSalvar = new Token(token);
        cacheTokenFactory.getCache().insert(token, tokenSalvar);
    }

    boolean isValidToken(String token) {
        return cacheTokenFactory.getCache().containsToken(token);
    }

    void removeToken() {
        LOGGER.debug("Removing token.");
        OAuthAccessResourceRequest oauthRequest;
        try {
            oauthRequest = new OAuthAccessResourceRequest(httpServletRequest, ParameterStyle.HEADER);
            String accessToken = oauthRequest.getAccessToken();

            if (isValidToken(accessToken)) {
                LOGGER.debug("Token removido. ({})", accessToken);
                cacheTokenFactory.getCache().removeToken(accessToken);
            }
        } catch (OAuthSystemException | OAuthProblemException e) {
            LOGGER.debug("Error remove token.", e);
            throw new IncorrectParametersAuthenticationException();
        }
    }
}

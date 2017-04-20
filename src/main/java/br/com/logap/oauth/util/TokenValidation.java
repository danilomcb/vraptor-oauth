package br.com.logap.oauth.util;

import br.com.logap.oauth.Token;
import br.com.logap.oauth.exception.InvalidAuthenticationException;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author LogAp
 *
 */
public class TokenValidation {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TokenValidation.class);
	
    private final TokenManager tokenManager;

	/**
	 * @deprecated CDI eyes only.
	 */
	public TokenValidation() {
		this(null);
	}

	@Inject
	public TokenValidation(TokenManager tokenManager) {
		this.tokenManager = tokenManager;
	}

	public Token validToken(HttpServletRequest request) throws InvalidAuthenticationException {
		OAuthAccessResourceRequest oauthRequest;
		try {
			oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.HEADER);
			String accessToken = oauthRequest.getAccessToken();
			
			if (!tokenManager.isValidToken(accessToken)) {
				throw new InvalidAuthenticationException();
			}
			
			return tokenManager.getToken(accessToken);
		} catch (OAuthSystemException | OAuthProblemException e) {
			throw new InvalidAuthenticationException();
		}
	}
}

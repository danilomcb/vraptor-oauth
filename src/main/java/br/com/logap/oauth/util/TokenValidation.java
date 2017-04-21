package br.com.logap.oauth.util;

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
	
    private final TokenManagerService tokenManagerService;

	/**
	 * @deprecated CDI eyes only.
	 */
	public TokenValidation() {
		this(null);
	}

	@Inject
	public TokenValidation(TokenManagerService tokenManagerService) {
		this.tokenManagerService = tokenManagerService;
	}

	public void validate(HttpServletRequest request) throws InvalidAuthenticationException {
		OAuthAccessResourceRequest oauthRequest;
		try {
			oauthRequest = new OAuthAccessResourceRequest(request, ParameterStyle.HEADER);
			String accessToken = oauthRequest.getAccessToken();

			if (!tokenManagerService.isValidToken(accessToken)) {
				LOGGER.debug("Invalid token {}", accessToken);
				throw new InvalidAuthenticationException();
			}

		} catch (OAuthSystemException | OAuthProblemException e) {
			LOGGER.error("Error validating token.", e);
			throw new InvalidAuthenticationException();
		}
	}


}

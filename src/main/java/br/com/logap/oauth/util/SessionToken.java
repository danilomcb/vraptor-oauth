package br.com.logap.oauth.util;

import br.com.logap.oauth.Token;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
public class SessionToken {

	private final TokenManager gerenciaTokens;
	private final HttpServletRequest httpRequest;

	/**
	 * @deprecated CDI eyes only.
	 */
	public SessionToken() {
		this(null, null);
	}

	@Inject
	public SessionToken(TokenManager gerenciaTokens, HttpServletRequest httpRequest) {
		this.gerenciaTokens = gerenciaTokens;
		this.httpRequest = httpRequest;
	}

	private Token getToken(Token token) {
		OAuthAccessResourceRequest oauthRequest;
		try {
			oauthRequest = new OAuthAccessResourceRequest(httpRequest, ParameterStyle.HEADER);
			String accessToken = oauthRequest.getAccessToken();
			token = gerenciaTokens.getToken(accessToken);
		} catch (OAuthSystemException | OAuthProblemException e) {
			e.printStackTrace();
		}
		return token;
	}
	
}

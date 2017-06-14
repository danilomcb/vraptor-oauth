package br.com.logap.oauth.util;

import br.com.logap.oauth.exception.InvalidAuthenticationException;
import org.apache.oltu.jose.jws.JWS;
import org.apache.oltu.jose.jws.JWSConstants;
import org.apache.oltu.jose.jws.io.JWSWriter;
import org.apache.oltu.jose.jws.signature.impl.PrivateKey;
import org.apache.oltu.jose.jws.signature.impl.SignatureMethodRSAImpl;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.jwt.JWT;
import org.apache.oltu.oauth2.jwt.io.JWTClaimsSetWriter;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

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

	public void autenticarViaJWT() throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, IOException, CertificateException {
		String P12_FILE = "";

		KeyStore keyStore = KeyStore.getInstance("PKCS12");
//		keyStore.load(new FileInputStream(P12_FILE), "notasecret".toCharArray());
		keyStore.load(null, "notasecret".toCharArray());
		java.security.PrivateKey privateKey = (java.security.PrivateKey) keyStore.getKey("privatekey", "notasecret".toCharArray());
		PrivateKey pk = new PrivateKey(privateKey);
		JWT jwt = new JWT.Builder()
				.setClaimsSetIssuer("788732372078-pas6c4tqtudpoco2f4au18e00suedjtb@developer.gserviceaccount.com")
				.setClaimsSetCustomField("scope", " https://www.googleapis.com/auth/plus.login")
				.setClaimsSetAudience("https://accounts.google.com/o/oauth2/token")
				.setClaimsSetIssuedAt(System.currentTimeMillis() / 1000)
				.setClaimsSetExpirationTime(System.currentTimeMillis() / 1000 +3600)
				.build();
		String payload = new JWTClaimsSetWriter().write(jwt.getClaimsSet());
		JWS jws = new JWS.Builder()
				.setType("JWT")
				.setAlgorithm(JWSConstants.RS256)
				.setPayload(payload).sign(new SignatureMethodRSAImpl(JWSConstants.RS256), pk).build();
		System.out.println("your assertion is "+new JWSWriter().write(jws));
	}
}

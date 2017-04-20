package br.com.logap.oauth;

public class Token {
	
	private final String token;
	
	private final AuthenticationUser authenticationUser;
	
	public Token(String token, AuthenticationUser authenticationUser) {
		this.token = token;
		this.authenticationUser = authenticationUser;
	}

	public String getToken() {
		return token;
	}

	public AuthenticationUser getAuthenticationUser() {
		return authenticationUser;
	}

}

package br.com.logap.oauth;

public class AuthenticationUserToken {
	
	private final String token;
	private final AuthenticationUser user;
	
	public AuthenticationUserToken(String token, AuthenticationUser user) {
		this.token = token;
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public AuthenticationUser getUser() {
		return user;
	}
}

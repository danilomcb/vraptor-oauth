package br.com.logap.oauth;

public class AuthenticationUser {

	private final String login;
	private final String password;

	public AuthenticationUser(String login, String password) {
		this.login = login;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getLogin() {
		return login;
	}
}
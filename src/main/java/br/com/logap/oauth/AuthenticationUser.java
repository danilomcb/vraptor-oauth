package br.com.logap.oauth;

public interface AuthenticationUser {

	String getPassword();

	Long getId();

	String getLogin();
}
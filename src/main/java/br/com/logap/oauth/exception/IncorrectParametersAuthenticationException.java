package br.com.logap.oauth.exception;

/**
 * Created by danilo-barros on 21/04/17.
 */
public class IncorrectParametersAuthenticationException extends RuntimeException {

    public IncorrectParametersAuthenticationException() {
        super();
    }

    public IncorrectParametersAuthenticationException(String message) {
        super(message);
    }

    public IncorrectParametersAuthenticationException(Throwable cause) {
        super(cause);
    }
}

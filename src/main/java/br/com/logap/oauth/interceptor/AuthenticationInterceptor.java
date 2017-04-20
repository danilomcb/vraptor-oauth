package br.com.logap.oauth.interceptor;

import br.com.logap.oauth.exception.InvalidAuthenticationException;
import br.com.logap.oauth.util.TokenValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by danilo-barros on 20/04/17.
 */
@Interceptor
@CheckToken
public class AuthenticationInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    private final TokenValidation tokenValidation;
    private final HttpServletRequest httpRequest;

    @Inject
    public AuthenticationInterceptor(TokenValidation tokenValidation, HttpServletRequest httpRequest) {
        this.tokenValidation = tokenValidation;
        this.httpRequest = httpRequest;
    }

    @AroundInvoke
    public Object check(InvocationContext ctx) throws Exception {
        this.validarToken();
        return ctx.proceed();
    }

    private void validarToken() throws InvalidAuthenticationException {
        LOGGER.debug("Validando token.");
        tokenValidation.validToken(httpRequest);
        LOGGER.debug("Token validado com sucesso.");
    }
}

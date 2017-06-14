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
@PrivateAccess
public class AuthenticationInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    private final TokenValidation tokenValidation;
    private final HttpServletRequest httpRequest;

    /**
     * @deprecated CDI eyes only.
     */
    public AuthenticationInterceptor() {
        this(null, null);
    }

    @Inject
    public AuthenticationInterceptor(TokenValidation tokenValidation, HttpServletRequest httpRequest) {
        this.tokenValidation = tokenValidation;
        this.httpRequest = httpRequest;
    }

    @AroundInvoke
    public Object check(InvocationContext ctx) throws Exception {
        LOGGER.trace("Validating token.");
        this.validateToken();
        return ctx.proceed();
    }

    private void validateToken() throws InvalidAuthenticationException {
        tokenValidation.validate(httpRequest);
        LOGGER.trace("Token validated successfully.");
    }
}

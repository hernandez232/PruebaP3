package com.ldar01.demoemployees.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuth implements AuthenticationEntryPoint {

    /*
    Esta clase se encarga de manejar los errores de autenticación. Por ejemplo, si un usuario intenta acceder a un recurso
    sin tener un token JWT válido, esta clase interceptará la solicitud y enviará una respuesta de error 401 Unauthorized.
    */

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}

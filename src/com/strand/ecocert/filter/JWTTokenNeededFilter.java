package com.strand.ecocert.filter;

import java.io.IOException;
import java.security.Key;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import com.strand.ecocert.util.DefaultKeyGenerator;
import com.strand.ecocert.util.KeyGenerator;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

/**
 * 
 * @author vilay
 *
 */

public class JWTTokenNeededFilter implements Filter{
	
	@Inject
	private KeyGenerator keyGenerator;
	
	public JWTTokenNeededFilter() {
		keyGenerator = new DefaultKeyGenerator();
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// Get the HTTP Authorization header from the request
        String authorizationHeader = ((HttpServletRequest)request).getHeader(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            //throw new NotAuthorizedException("Authorization header must be provided");
        	((HttpServletResponse) response).setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
        	return;
        }

        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
        try {

            // Validate the token
            Key key = keyGenerator.generateKey();
            Jws<Claims> claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            claims.getBody();
        } catch (Exception e) {
        	((HttpServletResponse) response).setStatus(Response.Status.UNAUTHORIZED.getStatusCode());
        	return;
        }
        chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}

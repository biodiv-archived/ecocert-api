package com.strand.ecocert.api.login;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.json.JSONObject;

import com.strand.ecocert.data.users.User;
import com.strand.ecocert.util.DefaultKeyGenerator;
import com.strand.ecocert.util.KeyGenerator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("user")
public class TokenService {
	
	@Context
    private UriInfo uriInfo;

    @Inject
    private KeyGenerator keyGenerator;
    
	@PersistenceContext(unitName="ecocert", type=PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;
	
	public TokenService() {
		entityManager = Persistence.createEntityManagerFactory( "ecocert" ).createEntityManager();
		keyGenerator = new DefaultKeyGenerator();
	}
	
	@POST
	@Path("token")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateUser(String jsonString) {
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			String userName = jsonObject.getString("userName");
			String password = jsonObject.getString("password");
			User user = authenticate(userName, password);
			String token = issueToken(user.getUserId());
			Response response = Response.ok()
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
					//.entity(user.getUserType().toString())
					.entity(user)
					.build();
			return response;
		} catch (Exception e) {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	private String issueToken(int userName) {
		Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(userName+"")
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;
	}
	
	private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

	private User authenticate(String userName, String password) {
		User user = entityManager.createNamedQuery(User.FIND_BY_USERNAME_PASSWORD, User.class)
		.setParameter("userName", userName)
		.setParameter("password", password)
		.getSingleResult();
		
        if (user == null)
            throw new SecurityException("Invalid user/password");
        else
        	return user;
	}
}